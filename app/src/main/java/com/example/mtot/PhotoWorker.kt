package com.example.mtot

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mtot.retrofit2.LocationData
import com.example.mtot.retrofit2.RequestAddPin
import com.example.mtot.retrofit2.ResponseAddPhotoToPin
import com.example.mtot.retrofit2.ResponseAddPin
import com.example.mtot.retrofit2.getJourneyId
import com.example.mtot.retrofit2.getRetrofitExceptJsonInterface
import com.example.mtot.retrofit2.getRetrofitInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class PhotoWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private val ALBUM_DIRECTORY =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()

    @SuppressLint("Range")
    override fun doWork() : Result {
        Log.d("workmanager", "ALBUM_DIRECTORY : " + ALBUM_DIRECTORY)
        checkPermissions()
        Log.d("workmanager", "pass permission checking")

        val currentTime = System.currentTimeMillis()
        val tenMinutesAgo = currentTime - 10 * 60 * 1000

        val selection = "${MediaStore.Images.ImageColumns.DATE_TAKEN} >= $tenMinutesAgo"
        Log.d("workmanager", "selection : " + selection.toString())


        val sortOrder = "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC"
        Log.d("workmanager", "sortOrder : " + sortOrder.toString())


        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA)
        Log.d("workmanager", "projection : " + projection.toString())

        val cursor = applicationContext.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder
        )

        val retrofitInterface = getRetrofitInterface()

        val files = ArrayList<MultipartBody.Part>()
        cursor?.use {
            while (it.moveToNext()) {
                val imagePath = it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
                Log.d("workmanager", "imagePath : " + imagePath)
                val imageFile = File(imagePath)
                files.add(MultipartBody.Part.createFormData(
                    "photos", imageFile.name,
                    imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                ))
            }
        }

        var pinId = -1

        val retrofitInterfaceExceptJson = getRetrofitExceptJsonInterface()

        retrofitInterface.addPinToJourney(
            RequestAddPin(
                getJourneyId(applicationContext),
                LocationData(36.5, 127.5)
            )
        ).enqueue(object : Callback<ResponseAddPin> {
            override fun onResponse(
                call: Call<ResponseAddPin>,
                response: Response<ResponseAddPin>
            ) {
                Log.d("workmanager", response.body().toString())
                if (response.isSuccessful) {
                    pinId = response.body()!!.pinId
                    val pinIdRequestBody = pinId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val requestList = files.toList()
                    Log.d("workmanager", requestList.toString())
                    retrofitInterfaceExceptJson.addPhotoToPin(pinIdRequestBody, requestList)
                        .enqueue(object : Callback<ResponseAddPhotoToPin> {
                            override fun onResponse(
                                call: Call<ResponseAddPhotoToPin>,
                                response: Response<ResponseAddPhotoToPin>
                            ) {
                                Log.d("workmanager", response.body().toString())
                            }

                            override fun onFailure(
                                call: Call<ResponseAddPhotoToPin>,
                                t: Throwable
                            ) {
                                Log.d("workmanager", t.message.toString())
                            }

                        })
                }
            }

            override fun onFailure(call: Call<ResponseAddPin>, t: Throwable) {
                t.message.toString()
            }
        })

        return Result.success()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw RuntimeException("Read storage permission not granted")
        }
    }
}
