package com.example.sms_sync.data.model

data class SmsPayLoad(
    val title: String,
    val body: String,
    val from: String,
    val receivedAt: String
)

data class ApiRequest(
    val message_data: List<SmsPayLoad>
)
