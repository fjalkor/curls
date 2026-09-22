package com.example.curls.features.exercises.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseRemote(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("category")
    val category: CategoryRemote? = null,
    @SerialName("muscles")
    val muscles: List<MuscleRemote>? = null,
    @SerialName("muscles_secondary")
    val musclesSecondary: List<MuscleRemote>? = null,
    @SerialName("equipment")
    val equipment: List<EquipmentRemote>? = null,
    @SerialName("images")
    val images: List<ImageRemote>? = null,
    @SerialName("translations")
    val translations: List<TranslationRemote>? = null,
    @SerialName("videos")
    val videos: List<VideoRemote>? = null,
)