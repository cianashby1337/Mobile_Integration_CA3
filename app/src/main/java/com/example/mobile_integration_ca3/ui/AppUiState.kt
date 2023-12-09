package com.example.mobile_integration_ca3.ui

import com.example.mobile_integration_ca3.model.ConvertedDose


data class AppUiState(
    var isDark: Boolean = true,
    var doseList: List<ConvertedDose> = emptyList()
)