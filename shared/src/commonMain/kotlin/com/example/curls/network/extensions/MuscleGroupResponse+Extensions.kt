package com.example.curls.network.extensions

import com.example.curls.entity.MuscleGroup
import com.example.curls.network.model.MuscleGroupResponse

fun MuscleGroupResponse.toMuscleGroups(): List<MuscleGroup> = results?.mapNotNull {
    val id = it.id ?: return@mapNotNull null
    val name = it.name ?: return@mapNotNull null
    if (name.isBlank()) return@mapNotNull null

    MuscleGroup(id = id, name = name)
}.orEmpty()