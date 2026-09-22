package com.example.curls.features.exercises.domain.mappers

import com.example.curls.features.exercises.domain.Category
import com.example.curls.cache.Category as CategoryLocal

fun CategoryLocal.toCategoryDomain() = Category(id = id, name = name)