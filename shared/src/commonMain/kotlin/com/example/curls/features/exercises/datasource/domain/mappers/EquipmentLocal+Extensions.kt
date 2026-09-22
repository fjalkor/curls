package com.example.curls.features.exercises.datasource.domain.mappers

import com.example.curls.features.exercises.datasource.domain.Equipment
import com.example.curls.cache.Equipment as EquipmentLocal

fun EquipmentLocal.toEquipmentDomain() = Equipment(name = name)