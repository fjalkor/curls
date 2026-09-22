package com.example.curls.features.exercises.domain

data class Video(
    val videoUrl: String,
    val duration: String?,
    val width: Long,
    val height: Long,
)