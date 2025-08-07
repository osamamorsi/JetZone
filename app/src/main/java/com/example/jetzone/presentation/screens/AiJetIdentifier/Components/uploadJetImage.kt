package com.example.jetzone.presentation.screens.AiJetIdentifier.Components

import android.content.Context
import android.net.Uri
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.create
import okhttp3.Response
import java.io.IOException
import kotlin.text.trim

fun uploadJetImage(
    context: Context,
    uri: Uri,
    onResult: (String?, String?) -> Unit,
) {
    val contentResolver = context.contentResolver
    val inputStream = try {
        contentResolver.openInputStream(uri)
    } catch (e: Exception) {
        null
    }

    if (inputStream == null) {
        onResult(null, "Unable to read the selected image.")
        return
    }

    val fileBytes = try {
        inputStream.readBytes()
    } catch (e: Exception) {
        onResult(null, "Failed to read the image.")
        return
    } finally {
        inputStream.close()
    }

    val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart(
            "image", "jet.jpg",
            create("image/jpeg".toMediaTypeOrNull(), fileBytes)
        )
        .build()

    val request = Request.Builder()
        .url("https://app2-umber.vercel.app/api/server")
        .post(requestBody)
        .build()

    OkHttpClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // Categorize errors
            val message = when {
                e.message?.contains("Unable to resolve host", ignoreCase = true) == true -> {
                    "No internet connection. Please check your network."
                }

                e.message?.contains("timeout", ignoreCase = true) == true -> {
                    "Connection timed out. Please try again."
                }

                else -> {
                    "Failed to connect to the server. Please try again later."
                }
            }
            onResult(null, message)
        }

        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                onResult(null, "Server error. Please try again later.")
                return
            }

            val bodyString = response.body?.string()
            if (!bodyString.isNullOrBlank()) {
                onResult(bodyString.trim(), null)
            } else {
                onResult(null, "Empty server response. Please try again.")
            }
        }
    })
}
