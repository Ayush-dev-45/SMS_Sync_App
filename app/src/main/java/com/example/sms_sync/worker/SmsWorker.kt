package com.example.sms_sync.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.sms_sync.data.model.ApiRequest
import com.example.sms_sync.data.model.SmsPayLoad
import com.example.sms_sync.data.remote.RetrofitInstance
import com.example.sms_sync.data.repository.SmsRepository
import com.example.sms_sync.utils.NotificationUtils


class SmsSyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
        override suspend fun doWork(): Result {
            return try {
                val smsList = SmsRepository.getNewSmsSinceLastSync(applicationContext)
                Log.d("SmsSyncWorker", "Found ${smsList.size} messages to sync")
                //we don't need "type" in req.body hence using payload here
                val smsPayLoad = smsList.map {
                    SmsPayLoad(
                        title = it.title,
                        body = it.body,
                        from = it.from,
                        receivedAt = it.receivedAt
                    )
                }
//                val test = smsList[0]
//                Log.d("msg", test.toString());
                if(smsPayLoad.isNotEmpty()){
                    val response = RetrofitInstance.api.sendSms(
                        request = ApiRequest(message_data = smsPayLoad)
                    )
                    Log.d("Response", "Synced ${response.count}")
                } else {
                    Log.d("Msg_Status", "No new messages found!!!")
                }
//                if (response) {
                Log.d("Sms_Sync_Worker", "Worker triggered at: ${System.currentTimeMillis()}")
                //Notif_function -- to add here
                Result.success()
            } catch (e: Exception) {
                Log.e("Sms_Sync_Worker", "Error: ${e.message}")
                Result.retry()
            }
        }
}