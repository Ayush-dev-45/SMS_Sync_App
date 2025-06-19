package com.example.sms_sync.screen.sms

import android.content.IntentFilter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.sms_sync.data.model.SmsData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsScreen(
    viewModel: SmsViewModel
) {
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
//initial function to test functionality
fun SmsItemInitial(sms: SmsData) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "From: ${sms.from}", style = MaterialTheme.typography.bodyLarge)
        Text(text = sms.body, style = MaterialTheme.typography.bodyMedium)
        Text(text = sms.receivedAt, style = MaterialTheme.typography.bodySmall)
        Text(text = sms.type, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun SmsItem(sms: SmsData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = sms.from,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(
                modifier = Modifier.height(4.dp)
            )
            Text(
                text = sms.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = sms.receivedAt,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                Text(
                    text = sms.type.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}