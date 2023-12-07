package com.example.mobile_integration_ca3.ui
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_integration_ca3.model.Response
import com.example.mobile_integration_ca3.network.MarsApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.json.JSONStringer
import java.io.IOException


class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    var isDark = false
    var doseList = emptyList<String>()

    init {
        getMarsPhotos()
    }

    fun updateIsDark(){
        _uiState.value = AppUiState(isDark = isDark)
        isDark = !isDark
    }

    private fun getMarsPhotos() {
        viewModelScope.launch {
            val bla = MarsApi.retrofitService.getPhotos()
            Log.d("result",bla.data[0].toString())
        }
    }
}
