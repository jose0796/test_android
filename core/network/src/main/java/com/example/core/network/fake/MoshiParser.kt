package com.example.core.network.fake

import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.*
import java.io.InputStream
import java.io.OutputStream

object MoshiParser {
    @Throws(IOException::class)
    fun <T> fromJson(clazz: Class<T>?, inputStream: InputStream?): T? {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(clazz)
        return inputStream?.source()?.buffer()?.let { adapter.fromJson(it) }
    }

    @Throws(IOException::class)
    fun <T> toJson(obj: T, outputStream: OutputStream?) {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(obj as Class<T>?)
        val out: BufferedSink? = outputStream?.sink()?.buffer()
        if (out != null) {
            adapter.toJson(out, obj)
        }
        out?.flush()
    }
}

inline fun <reified T> getListType() : Class<out Array<T>> =
    arrayOf<T>()::class.java