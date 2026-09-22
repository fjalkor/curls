package com.example.curls.features.exercises.datasource.domain.mappers

import com.example.curls.features.exercises.datasource.domain.Category
import com.example.curls.cache.Category as CategoryLocal

fun CategoryLocal.toCategoryDomain() = Category(id = id, name = name)