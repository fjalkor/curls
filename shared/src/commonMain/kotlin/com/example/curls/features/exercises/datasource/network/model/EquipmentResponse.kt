package com.example.curls.features.exercises.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EquipmentResponse(
    @SerialName("results")
    val results: List<EquipmentRemote>? = null,
)