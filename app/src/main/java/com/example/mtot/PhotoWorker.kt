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
import com.example.mtot.retrofit2.ResponseAddPin
import com.example.mtot.retrofit2.getJourneyId
import com.example.mtot.retrofit2.getRetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

        cursor?.use {
            val retrofitInterface = getRetrofitInterface()
            var pinId = -1
            retrofitInterface.addPinToJourney(
                RequestAddPin(
                    getJourneyId(applicationContext),
                    LocationData(36.5, 127.5)
                )
            ).enqueue(object: Callback<ResponseAddPin> {
                override fun onResponse(
                    call: Call<ResponseAddPin>,
                    response: Response<ResponseAddPin>
                ) {
                    Log.d("workmanager", response.body().toString())
                    if(response.isSuccessful)
                        pinId = response.body()!!.pinId
                }

                override fun onFailure(call: Call<ResponseAddPin>, t: Throwable) {
                    t.message.toString()
                }
            })
            Log.d("workmanager", "pinId : " + pinId)

            while (it.moveToNext()) {
                val imagePath = it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
                Log.d("workmanager", "imagePath : " + imagePath)
                val imageFile = File(imagePath)
            }
        }

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
