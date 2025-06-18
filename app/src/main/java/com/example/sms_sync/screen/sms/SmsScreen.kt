package com.example.sms_sync.screen.sms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sms_sync.data.model.SmsData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsScreen(viewModel: SmsViewModel) {
    val context = LocalContext.current
    val smsList by viewModel.smsList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSms(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("SMS Sync") })
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(smsList) { sms ->
                SmsItem(sms)
            }
        }
    }
}

@Composable
fun SmsItem(sms: SmsData) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "From: ${sms.from}", style = MaterialTheme.typography.bodyLarge)
        Text(text = sms.body, style = MaterialTheme.typography.bodyMedium)
        Text(text = sms.receivedAt, style = MaterialTheme.typography.bodySmall)
        Text(text = sms.type, style = MaterialTheme.typography.labelSmall)
    }
}