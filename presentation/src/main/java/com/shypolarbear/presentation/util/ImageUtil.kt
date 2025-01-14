package com.shypolarbear.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object ImageUtil{

    private const val MAX_IMAGE_WIDTH = 1440
    private const val MAX_IMAGE_HEIGHT = 1440

//    이미지 최대 사이즈 : 1440 * 1440
//    이미지 최대 용량 : 1440 * 1440 / 1024 / 1024 = 7.91MB로 이미지 최적화한다.

    fun uriToOptimizeImageFile(context: Context, uri: Uri): File? {
        try {
            val storage = context.cacheDir
            val fileName = String.format("%s.%s", UUID.randomUUID(), "jpg")

            val tempFile = File(storage, fileName)
            tempFile.createNewFile()

            val fos = FileOutputStream(tempFile)

            decodeOptimizeBitmapFromUri(context, uri)?.apply {
                compress(Bitmap.CompressFormat.JPEG, 90, fos)
                recycle()
            } ?: throw NullPointerException()

            fos.flush()
            fos.close()

            return tempFile
        } catch (e: Exception) {
            Timber.e("${e.message}")
        }

        return null
    }

//    BitmapFactory를 사용하게 되면 Bitmap.Config는 기본으로 ARGB_8888으로 설정된다.
//    이미지의 최대 가로/세로 길이가 1280이라고 할 때,
//    Bitmap의 최대 크기는 1280 * 1280 * 4 = 6.25MB가 된다.
//    즉 해당 함수는 Bitmap을 6.25MB 이하로 만들어준다.

    private fun decodeOptimizeBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        val input = BufferedInputStream(context.contentResolver.openInputStream(uri))

        input.mark(input.available())

        var bitmap: Bitmap?

        BitmapFactory.Options().run {
            inJustDecodeBounds = true
            bitmap = BitmapFactory.decodeStream(input, null, this)

            input.reset()

            inSampleSize = calculateInSampleSize(this)
            inJustDecodeBounds = false

            bitmap = rotateImageIfRequired(context, BitmapFactory.decodeStream(input, null, this)!!, uri)
        }

        input.close()

        return bitmap
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > MAX_IMAGE_HEIGHT || width > MAX_IMAGE_WIDTH) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= MAX_IMAGE_HEIGHT && halfWidth / inSampleSize >= MAX_IMAGE_WIDTH) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun rotateImageIfRequired(context: Context, bitmap: Bitmap, uri: Uri): Bitmap? {
        val input = context.contentResolver.openInputStream(uri) ?: return null

        val exif =
            ExifInterface(input)

        val orientation =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

        return rotateBitmap(bitmap, orientation)
    }

    private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap? {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> return bitmap
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }

            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }

            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }

            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            else -> return bitmap
        }

        return try {
            val bmRotated =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()
            bmRotated
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            null
        }
    }
}