package com.example.mobile_integration_ca3.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ConvertedDose(val date:String) {
    var scheduledMedications = mutableListOf<String>()
}

@Serializable
data class Response(
    @SerialName(value = "status")
    val status: Int,
    @SerialName(value = "message")
    val password: String,
    @SerialName(value = "data")
    val data : List<User>
)

@Serializable
data class User(
    @SerialName(value = "id")
    val id: String,
    @SerialName(value = "email")
    val email: String? = null,
    @SerialName(value = "password")
    val password: String? = null,
    @SerialName(value = "pillbox")
    val pillbox : Pillbox? = null,
    @SerialName(value = "medications")
    val medications : List<Medication>? = null,
    @SerialName(value = "schedule")
    val schedule : List<Schedule>? = null,
    @SerialName(value = "events")
    val events : List<Events>? = null,
    @SerialName(value = "history")
    val history : List<History>? = null
)

@Serializable
data class Pillbox(
    @SerialName(value = "id")
    val id: String,
    @SerialName(value = "numbercompartments")
    val numbercompartments : Int
)

@Serializable
data class Medication(
    @SerialName(value = "pilldose")
    val pilldose: Int,
    @SerialName(value = "numberofpills")
    val numberofpills: Int,
    @SerialName(value = "id")
    val id: String,
    @SerialName(value = "name")
    val name: String,
)

@Serializable
data class Schedule(
    @SerialName(value = "datetime")
    val datetime: String,
    @SerialName(value = "id")
    val id: String,
    @SerialName(value = "compartment")
    val compartment: Int,
    @SerialName(value = "medications")
    val medications : List<String>
)

@Serializable
data class Events(
    @SerialName(value = "isempty")
    val isempty: Boolean? = null,
    @SerialName(value = "datetime")
    val datetime: String,
    @SerialName(value = "compartment")
    val compartment: Int? = null,
    @SerialName(value = "sensor")
    val sensor: String,
    @SerialName(value = "isopen")
    val isopen: Boolean
)

@Serializable
data class History(
    @SerialName(value = "email")
    val email: String?
)