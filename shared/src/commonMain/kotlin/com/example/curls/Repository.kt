package com.example.curls

import com.example.curls.cache.Database
import com.example.curls.cache.DatabaseDriverFactory
import com.example.curls.entity.MuscleGroup
import com.example.curls.network.Api
import com.example.curls.network.extensions.toMuscleGroups

class Repository(databaseDriverFactory: DatabaseDriverFactory, val api: Api) {
    private val db = Database(databaseDriverFactory)

    @Throws(Exception::class)
    suspend fun getMuscleGroups(forceReload: Boolean): List<MuscleGroup> {
        val cache = db.getMuscleGroups()
        if (cache.isNotEmpty() && !forceReload) return cache

        return api.getAllMuscleGroups().toMuscleGroups().also { db.clearAndCreateMuscleGroups(it) }
    }
}