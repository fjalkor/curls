package com.example.curls.features.exercises.network.mappers

import com.example.curls.cache.Category
import com.example.curls.features.exercises.network.model.CategoryRemote

fun CategoryRemote.toCategoryLocal(): Category? = Category(
    id = id?.toLong() ?: return null,
    name = name?.takeIf { it.isNotBlank() } ?: return null,
)