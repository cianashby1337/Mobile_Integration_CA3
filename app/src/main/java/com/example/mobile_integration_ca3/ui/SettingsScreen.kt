package com.example.mobile_integration_ca3.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsScreen(
    appViewModel: AppViewModel = viewModel(),
    darkTheme: Boolean
) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Button(onClick = { appViewModel.updateIsDark() }) {
                    Log.d("SettingsScreen","Dark/Light mode button clicked")
                    Text(text = if (darkTheme) "Change to Light Mode" else "Change to Dark Mode")
                }
            }
        }
    Log.d("SettingsScreen","SettingsScreen successfully loaded")
}