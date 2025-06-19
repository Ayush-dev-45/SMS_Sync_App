package com.example.sms_sync.ui.screen.sms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.sms_sync.data.model.SmsData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsScreen(
    viewModel: SmsViewModel
) {
    val context = LocalContext.current
    val smsList by viewModel.smsList.collectAsState()
    var selectedSms by remember { mutableStateOf<SmsData?>(null) }

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
                SmsItem(sms) {
                    selectedSms = sms
                }
            }
        }
        selectedSms?.let {
            SmsPopupDialog(sms = it) {
                selectedSms = null
            }
        }
    }
}

@Composable
//initial function to test functionality ---> To be removed later
fun SmsItemInitial(sms: SmsData, onClick: ()-> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "From: ${sms.from}", style = MaterialTheme.typography.bodyLarge)
        Text(text = sms.body, style = MaterialTheme.typography.bodyMedium)
        Text(text = sms.receivedAt, style = MaterialTheme.typography.bodySmall)
        Text(text = sms.type, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun SmsItem(sms: SmsData, onClick: () -> Unit) {
    Card(
        onClick = onClick,
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

@Composable
fun SmsPopupDialog(sms: SmsData, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.1f))
                .clickable(onClick = onDismiss)
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("From: ${sms.from}", style = MaterialTheme.typography.titleMedium)
                    Text(sms.body, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 8.dp))
                    Text("Received: ${sms.receivedAt}", style = MaterialTheme.typography.bodySmall)
                    Text("Type: ${sms.type}", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
    }
}