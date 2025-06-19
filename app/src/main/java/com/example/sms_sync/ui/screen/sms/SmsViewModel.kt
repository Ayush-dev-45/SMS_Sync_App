package com.example.sms_sync.ui.screen.sms

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sms_sync.data.model.SmsData
import com.example.sms_sync.data.repository.SmsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SmsViewModel : ViewModel() {
    private val _smsList = MutableStateFlow<List<SmsData>>(emptyList())
    val smsList: StateFlow<List<SmsData>> = _smsList

    fun loadSms(context: Context) {
        viewModelScope.launch {
            val list = SmsRepository.getSmsFromLastSixMonths(context)
            _smsList.value = list
        }
    }
}
