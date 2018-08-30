package com.igordanilchik.coroutinestest.common.di

import com.igordanilchik.coroutinestest.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.*
import timber.log.Timber
import java.io.IOException
import javax.inject.Singleton

@Module
class HttpClientModule {

    private val progressListener = object : ProgressListener {

        override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
            update(bytesRead, done)
            Timber.d("Content length: %s", contentLength)
            Timber.d("%d%% done\n", 100 * bytesRead / contentLength)
        }

        override fun update(bytesRead: Long, done: Boolean) {
            Timber.d("Bytes read: %s", bytesRead)
            Timber.d("Done: %s", done)
        }
    }

    @Provides
    @Singleton
    internal fun provideHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            httpClient.addInterceptor(logging)

            httpClient.addNetworkInterceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                originalResponse.newBuilder()
                        .body(originalResponse.body()?.let { ProgressResponseBody(it, progressListener) })
                        .build()
            }
        }
        return httpClient.build()
    }

    private class ProgressResponseBody internal constructor(
            private val responseBody: ResponseBody,
            private val progressListener: ProgressListener
    ) : ResponseBody() {

        private val bufferedSource: BufferedSource by lazy { Okio.buffer(source(responseBody.source())) }

        override fun contentType(): MediaType? = responseBody.contentType()

        override fun contentLength(): Long = responseBody.contentLength()

        override fun source(): BufferedSource = bufferedSource

        private fun source(source: Source): Source {
            return object : ForwardingSource(source) {
                var totalBytesRead = 0L

                @Throws(IOException::class)
                override fun read(sink: Buffer, byteCount: Long): Long {
                    val bytesRead = super.read(sink, byteCount)
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += if (bytesRead != -1L) bytesRead else 0L
                    if (responseBody.contentLength() > 0) {
                        progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1L)
                    } else {
                        progressListener.update(totalBytesRead, bytesRead == -1L)
                    }
                    return bytesRead
                }
            }
        }
    }

    internal interface ProgressListener {
        //Known content length
        fun update(bytesRead: Long, contentLength: Long, done: Boolean)

        //Unknown content length
        fun update(bytesRead: Long, done: Boolean)
    }
}
