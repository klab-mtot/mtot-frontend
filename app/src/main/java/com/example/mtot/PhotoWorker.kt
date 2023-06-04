package com.example.mtot

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mtot.retrofit2.LocationData
import com.example.mtot.retrofit2.RequestAddPin
import com.example.mtot.retrofit2.ResponseAddPhotoToPin
import com.example.mtot.retrofit2.ResponseAddPin
import com.example.mtot.retrofit2.getJourneyId
import com.example.mtot.retrofit2.getRetrofitExceptJsonInterface
import com.example.mtot.retrofit2.getRetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.Part
import java.io.File

class PhotoWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val ALBUM_DIRECTORY =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()

    @SuppressLint("Range")
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
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

        val files = ArrayList<RequestBody>()
        cursor?.use {
            while (it.moveToNext()) {
                val imagePath = it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
                Log.d("workmanager", "imagePath : " + imagePath)
                val imageFile = File(imagePath)
                files.add(
                        imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                )
            }
        }

        var pinId = -1

        val retrofitExceptJsonInterface = getRetrofitExceptJsonInterface()
        retrofitExceptJsonInterface.addPinToJourney(
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
                    val requestBuilder = MultipartBody.Builder()
                    requestBuilder.setType(MultipartBody.FORM)
                    for (file in files){
                           requestBuilder.addFormDataPart("my_images[]", "", file)
                    }

                    retrofitInterface.addPhotoToPin(requestBuilder.build())
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

        Result.success()
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
