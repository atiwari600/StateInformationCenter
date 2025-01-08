package com.atiwari.stateinfocenter.utils

import android.widget.Toast
import com.atiwari.stateinfocenter.SICApplication
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

object AppUtils {

    inline fun <reified T> getJsonFromAssets(fileName: String): T? {
        var inputStream: InputStream? = null
        try {
            inputStream = SICApplication.instance.assets.open(fileName)
            val bufferedReader =
                BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8")))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            val jsonString = stringBuilder.toString()
            return Gson().fromJson(jsonString, T::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }
        return null
    }

    fun showToast(message: String?) {
        Toast.makeText(SICApplication.instance, message ?: "Error!", Toast.LENGTH_SHORT).show()
    }
}