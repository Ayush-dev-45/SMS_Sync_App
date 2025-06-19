package com.example.sms_sync.data.remote

import com.example.sms_sync.data.model.ApiRequest
import com.example.sms_sync.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("usermessage/add")
    suspend fun sendSms(
        @Header("Authorization") key: String,
        @Body request: ApiRequest
    ): ApiResponse
}