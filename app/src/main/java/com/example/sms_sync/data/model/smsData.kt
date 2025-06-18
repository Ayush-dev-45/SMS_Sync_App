package com.example.sms_sync.data.model

data class SmsData(
    val from: String,
    val body: String,
    val receivedAt: String,
    val type: String
)