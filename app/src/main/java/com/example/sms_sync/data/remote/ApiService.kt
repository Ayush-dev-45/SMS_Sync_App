package com.example.sms_sync.data.remote

import com.example.sms_sync.data.model.ApiRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("usermessage/add")
    fun sendSms(
        @Header("Authorization") token: String,
        @Body request: ApiRequest
    ): Response<Unit>
}