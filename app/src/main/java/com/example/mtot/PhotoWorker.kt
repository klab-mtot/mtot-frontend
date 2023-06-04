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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class PhotoWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val ALBUM_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()

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

//        val cursor = applicationContext.contentResolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            projection,
//            selection,
//            null,
//            sortOrder
//        )
//
//        cursor?.use {
//            while (it.moveToNext()) {
//                val imagePath = it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
//                val imageFile = File(imagePath)
//                // Do something with the image file
//            }
//        }

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
