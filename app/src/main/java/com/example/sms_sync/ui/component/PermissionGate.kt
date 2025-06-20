package com.example.sms_sync.ui.component

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sms_sync.ui.screen.sms.PermissionScreen
import com.example.sms_sync.ui.screen.sms.SmsScreen
import com.example.sms_sync.ui.screen.sms.SmsViewModel
import com.example.sms_sync.worker.SmsSyncScheduler.scheduleSmsSyncWorker

@Composable
fun SmsPermissionGate() {
    val context = LocalContext.current
    var hasSmsPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_SMS
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasSmsPermission = granted
        if (granted) scheduleSmsSyncWorker(context)
    }

    LaunchedEffect(Unit) {
        if (!hasSmsPermission) {
            permissionLauncher.launch(Manifest.permission.READ_SMS)
        } else {
            scheduleSmsSyncWorker(context)
        }
    }

    if (hasSmsPermission) {
        val viewModel: SmsViewModel = viewModel()
        SmsScreen(viewModel)
    } else {
        PermissionScreen(onRequestPermission = {
            permissionLauncher.launch(Manifest.permission.READ_SMS)
        })
    }
}