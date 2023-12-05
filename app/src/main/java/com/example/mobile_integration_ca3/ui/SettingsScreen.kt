package com.example.mobile_integration_ca3.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_integration_ca3.data.allDoses
import com.example.mobile_integration_ca3.ui.theme.Mobile_Integration_CA3Theme

@Composable
fun SettingsScreen(
    appViewModel: AppViewModel = viewModel(),
    onNextButtonClicked: () -> Unit,
    modifier: Modifier,
    darkTheme: Boolean
) {
    // A surface container using the 'background' color from the theme

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Button(onClick = { appViewModel.updateIsDark() }) {
                    Text(text = if (darkTheme) "Change to Light Mode" else "Change to Dark Mode")
                }

            }

        }


    Log.d("DosesApp","DosesApp successfully loaded")
}