package com.example.sms_sync

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sms_sync.data.model.ApiRequest
import com.example.sms_sync.data.remote.RetrofitInstance
import com.example.sms_sync.data.repository.SmsRepository
import com.example.sms_sync.screen.sms.PermissionScreen
import com.example.sms_sync.screen.sms.SmsScreen
import com.example.sms_sync.ui.theme.SMS_SyncTheme
import com.example.sms_sync.screen.sms.SmsViewModel
import com.example.sms_sync.worker.SmsSyncWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SMS_SyncTheme {
                val context = LocalContext.current
                var hasPermission by remember {
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_SMS
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) {
                    granted ->
                    hasPermission = granted
                    if(granted) {
                        scheduleSmsSyncWorker(context)
                    }
                }
                LaunchedEffect(Unit) {
                    if(!hasPermission) {
                        launcher.launch(Manifest.permission.READ_SMS)
                    } else {
                        scheduleSmsSyncWorker(context)
                    }
                }
                if(hasPermission){
                    val viewModel: SmsViewModel = viewModel()
                    SmsScreen(viewModel)
                } else {
                    PermissionScreen {
                        launcher.launch(Manifest.permission.READ_SMS)
                    }
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

private fun scheduleSmsSyncWorker (context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val workRequest = PeriodicWorkRequestBuilder<SmsSyncWorker>(
        15, TimeUnit.MINUTES
    )
        .setConstraints(constraints)
        .build()
    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "SmsSyncJob",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}