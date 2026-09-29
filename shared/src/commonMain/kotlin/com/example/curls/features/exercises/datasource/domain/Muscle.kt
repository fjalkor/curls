package com.example.curls.features.exercises.datasource.domain

data class Muscle(
    val name: String,
    val nameEn: String?,
    val imageUrlMain: String? = null,
    val imageUrlSecondary: String? = null,
)