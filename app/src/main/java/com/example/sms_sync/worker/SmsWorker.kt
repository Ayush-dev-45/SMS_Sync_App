package com.example.sms_sync.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.sms_sync.data.model.ApiRequest
import com.example.sms_sync.data.remote.RetrofitInstance
import com.example.sms_sync.data.repository.SmsRepository


class SmsSyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
        override suspend fun doWork(): Result {
            return try {
                val smsList = SmsRepository.getSmsFromLastSixMonths(applicationContext)
                val response = RetrofitInstance.api.sendSms(
                    //hardcoded for now
                    token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjE0OSwiaWF0IjoxNzQ5ODA1NzAyfQ.NTZQcaA1rGlDaazMTQqg-RdmbFluhQXB-Nbop0zXUww",
                    request = ApiRequest(message_data = smsList)
                )
                Result.success()
            } catch (e: Exception) {
                e.printStackTrace()
                Result.retry()
            }
        }
    }