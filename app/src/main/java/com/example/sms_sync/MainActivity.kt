package com.example.sms_sync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sms_sync.ui.component.SmsPermissionGate
import com.example.sms_sync.ui.theme.SMS_SyncTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SMS_SyncTheme {
                SmsPermissionGate()
            }
        }
    }
}

//@Composable
//fun NotificationPermissionHandler() {
//    val context = LocalContext.current
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        Log.d("Notif_permission","Granted")
//    }
//}