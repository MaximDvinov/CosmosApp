package com.dvinov.myspaceapp

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dvinov.myspaceapp.screen.apod.LoadState
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


fun downloadImage(
    imageURL: String,
    context: Context,
    onChangeState: (loadState: LoadState<Unit>) -> Unit
) {
    Glide.with(context)
        .load(imageURL)
        .into(object : CustomTarget<Drawable?>() {
            override fun onLoadStarted(placeholder: Drawable?) {
                onChangeState(LoadState.Loading())
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                onChangeState(LoadState.Error(message = context.getString(R.string.error_uploading_image)))
            }

            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable?>?
            ) {
                try {
                    val bitmap = (resource as BitmapDrawable).bitmap
                    saveImage(bitmap, context)
                    onChangeState(LoadState.Success())
                } catch (e: java.lang.Exception) {
                    onChangeState(LoadState.Error(message = context.getString(R.string.error_saving_image)))
                }

            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}

private fun saveImage(
    bitmap: Bitmap,
    context: Context,
) {
    val filename = "${System.currentTimeMillis()}.jpg"

    var outputStream: OutputStream? = null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver?.also { resolver ->

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            outputStream = imageUri?.let { resolver.openOutputStream(it) }
        }
    } else {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        outputStream = FileOutputStream(image)
    }

    outputStream?.use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
}