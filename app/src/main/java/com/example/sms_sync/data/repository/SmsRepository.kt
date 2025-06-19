package com.example.sms_sync.data.repository

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.provider.Telephony
import com.example.sms_sync.data.model.SmsData
import java.util.Date
import java.util.Locale

object SmsRepository {
    private var lastSyncTimestamp: Long = System.currentTimeMillis() - 6L * 30 * 24 * 60 * 60 * 1000

    fun getSmsFromLastSixMonths(context: Context): List<SmsData> {
        val allSmsList = mutableListOf<SmsData>()
        val sixMonthsAgo = System.currentTimeMillis() - 6L * 30 * 24 * 60 * 60 * 1000
        val sixMonthSmsResult = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(Telephony.Sms.ADDRESS, Telephony.Sms.BODY, Telephony.Sms.DATE, Telephony.Sms.TYPE),
            "${Telephony.Sms.DATE} > ?",
            arrayOf(sixMonthsAgo.toString()),
            Telephony.Sms.DEFAULT_SORT_ORDER
        )
        sixMonthSmsResult?.use {
            while (it.moveToNext()) {
                val from = it.getString(0) ?: "Unknown"
                val body = it.getString(1) ?: ""
                val date = it.getLong(2)
                val type: String = if (it.getInt(3) == Telephony.Sms.MESSAGE_TYPE_INBOX) "inbox" else "sent"
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
                val receivedAt = formatter.format(Date(date))
                allSmsList.add(SmsData(title = "Sms", body = body, from = from, receivedAt = receivedAt, type = type))
            }
        }
        return allSmsList
    }

    fun getNewSmsSinceLastSync(context: Context): List<SmsData> {
        val newSmsList = mutableListOf<SmsData>()
        val latestSmsResult = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(Telephony.Sms.ADDRESS, Telephony.Sms.BODY, Telephony.Sms.DATE, Telephony.Sms.TYPE),
            "${Telephony.Sms.DATE} > ?",
            arrayOf(lastSyncTimestamp.toString()),
            Telephony.Sms.DEFAULT_SORT_ORDER
        )
        latestSmsResult?.use {
            while (it.moveToNext()) {
                val from = it.getString(0) ?: "Unknown"
                val body = it.getString(1) ?: ""
                val date = it.getLong(2)
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
                val receivedAt = formatter.format(Date(date))
                val type: String = if (it.getInt(3) == Telephony.Sms.MESSAGE_TYPE_INBOX) "inbox" else "sent"
                newSmsList.add(SmsData(title = "Sms", body = body, from = from, receivedAt = receivedAt, type = type))
            }
        }
        lastSyncTimestamp = System.currentTimeMillis()
        return newSmsList
    }
}