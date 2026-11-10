package com.example.retrofit.network

import com.example.retrofit.model.Data
import com.example.retrofit.model.UserDetail
import com.example.retrofit.model.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("employees")
    fun getAllUsers(): Call<List<Data>>
    @GET("employees")
    fun getUserDetail(@Query("id") id: String): Call<List<Data>>

    @POST("employees")
    fun createEmployee(@Body employee: Data): Call<Void>

    @PATCH("employees")
    fun updateEmployee(
        @Query("id") id: String, // eq.1
        @Body employee: Data
    ): Call<Void>

    @DELETE("employees")
    fun deleteEmployee(@Query("id") id: String): Call<Void>
}