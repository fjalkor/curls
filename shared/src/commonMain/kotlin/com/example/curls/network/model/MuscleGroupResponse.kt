package com.example.curls.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MuscleGroupResponse(
    @SerialName("results")
    val results: List<MuscleGroupRemote>? = null,
)