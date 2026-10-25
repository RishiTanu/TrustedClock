package com.rishikeshsingh.trustedclock.ui.theme.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rishikeshsingh.trustedclock.TrustedTimeApplication
import com.rishikeshsingh.trustedclock.data.datasource.TimeDataSource
import com.rishikeshsingh.trustedclock.data.repository.TimeRepository
import com.rishikeshsingh.trustedclock.ui.theme.TrustedClockTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource = TimeDataSource(application as TrustedTimeApplication)
        val repository = TimeRepository(dataSource)
        val viewModel = MainViewModel(repository)

        setContent {
            TrustedClockTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel){
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Trusted Time", style = MaterialTheme.typography.titleMedium)

        Text(
            text = uiState.trustedTime?.let { formatTime(it) } ?: "Not Available",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Device Time", style = MaterialTheme.typography.titleMedium)

        Text(
            text = formatTime(uiState.deviceTime),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Difference ${uiState.difference ?: "N/A"} ms",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private fun formatTime(millis: Long): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(Date(millis))
}