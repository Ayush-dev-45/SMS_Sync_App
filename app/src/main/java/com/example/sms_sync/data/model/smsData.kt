package com.example.sms_sync.data.model

data class SmsData(
    val title: String,
    val body: String,
    val from: String,
    val receivedAt: String,
    val type: String
)