package com.example.retrofit.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun getInstance(): ApiService {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val supabaseKey = "sb_publishable_Xlp7NC1nKhQXqABd2K80CA_vOUEMb2-"
        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("apikey", supabaseKey)
                    .addHeader("Authorization", "Bearer $supabaseKey")
                    .build()
                chain.proceed(request)
            }
            .build()

        val builder = Retrofit.Builder()
            .baseUrl("https://pmgegxmvqfasvzchqysf.supabase.co/rest/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()
        return builder.create(ApiService::class.java)
    }
}