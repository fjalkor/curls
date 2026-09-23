package com.example.curls.features.exercises.datasource.domain

data class Category(
    val id: Long,
    override val name: String,
): Named