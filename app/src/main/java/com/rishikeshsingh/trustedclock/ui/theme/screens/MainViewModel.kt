package com.rishikeshsingh.trustedclock.ui.theme.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishikeshsingh.trustedclock.data.repository.TimeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainViewModel(private val repository: TimeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(TimeUiState())
    val uiState: StateFlow<TimeUiState> = _uiState.asStateFlow()

    init {
        startUpdateTime()
    }

    private fun startUpdateTime(){
        viewModelScope.launch {
            while (isActive){
                updateTime()
                delay(1000)
            }
        }
    }

    private fun updateTime(){
        val info = repository.getTimeInfo()
        _uiState.value = TimeUiState(
            trustedTime = info.trustedTime,
            deviceTime = info.deviceTime,
            difference = info.difference,
            isTrustedAvailable = info.trustedTime != null
        )
    }
}

data class TimeUiState(
    val trustedTime: Long? = null,
    val deviceTime: Long = 0L,
    val difference:Long? = null,
    val isTrustedAvailable: Boolean? = false
)