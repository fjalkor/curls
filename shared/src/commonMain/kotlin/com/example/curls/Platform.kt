package com.example.curls

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform