package com.example.curls.features.exercises.datasource.network.mappers

import com.example.curls.cache.Equipment
import com.example.curls.features.exercises.datasource.network.model.EquipmentRemote

fun EquipmentRemote.toEquipmentLocal(): Equipment? = Equipment(
    id = id?.toLong() ?: return null,
    name = name?.takeIf { it.isNotBlank() } ?: return null,
)