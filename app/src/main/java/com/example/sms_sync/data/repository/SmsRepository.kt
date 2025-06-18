package com.example.sms_sync.data.repository

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.provider.Telephony
import com.example.sms_sync.data.model.SmsData
import java.util.Date
import java.util.Locale

object SmsRepository {

    fun getSmsFromLastSixMonths(context: Context): List<SmsData> {
        val list = mutableListOf<SmsData>()
        val sixMonthsAgo = System.currentTimeMillis() - 15778476000L
        val result = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(Telephony.Sms.ADDRESS, Telephony.Sms.BODY, Telephony.Sms.DATE, Telephony.Sms.TYPE),
            "${Telephony.Sms.DATE} > ?",
            arrayOf(sixMonthsAgo.toString()),
            Telephony.Sms.DEFAULT_SORT_ORDER
        )
        result?.use {
            while (it.moveToNext()) {
                val from = it.getString(0) ?: "Unknown"
                val body = it.getString(1) ?: ""
                val date = it.getLong(2)
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
                val receivedAt = formatter.format(Date(date))
                val typeCode = it.getInt(3)
                val type = when (typeCode) {
                    Telephony.Sms.MESSAGE_TYPE_INBOX -> "inbox"
                    Telephony.Sms.MESSAGE_TYPE_SENT -> "sent"
                    else -> "other"
                }
                list.add(SmsData(body = body, from = from, receivedAt = receivedAt, type = type))
            }
        }
        return list
    }
}

