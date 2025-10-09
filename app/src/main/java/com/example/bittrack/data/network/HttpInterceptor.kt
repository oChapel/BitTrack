package com.example.bittrack.data.network

import com.example.bittrack.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HttpInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = BuildConfig.COINCAP_API_KEY
        val request = chain.request()
            .newBuilder()
            .header("Authorization", "Bearer $apiKey")
            .build()

        return chain.proceed(request)
    }
}
