package com.example.mobile_integration_ca3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_integration_ca3.model.ConvertedDose
import com.example.mobile_integration_ca3.network.AppApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private var isDark = false
    private var list = mutableListOf<ConvertedDose>()

    init {
        getUserDoses()
    }

    fun updateIsDark(){
        _uiState.value = AppUiState(isDark = isDark)
        isDark = !isDark
    }

    private fun getUserDoses() {
        viewModelScope.launch {
            val response = AppApi.retrofitService.getUsers()
            var schedules = response.data[0].schedule
            var medications = response.data[0].medications
            if (schedules != null && medications != null) {
                for(schedule in schedules) {
                    var newDose = ConvertedDose(
                           "Date: " + schedule.datetime.subSequence(5,10).toString() +
                                " Time: " + schedule.datetime.subSequence(11, schedule.datetime.length-4).toString())
                    for(scheduledMedication in schedule.medications) {
                        for(medication in medications) {
                            if (scheduledMedication == medication.id) newDose.scheduledMedications.add(medication.name)
                        }
                    }
                    list.add(newDose)
                }
            }
            _uiState.value = AppUiState(doseList = list)
        }
    }
}
