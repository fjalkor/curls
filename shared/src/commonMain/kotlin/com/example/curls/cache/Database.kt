package com.example.curls.cache

import com.example.curls.entity.MuscleGroup

internal class Database(driverFactory: DatabaseDriverFactory) {
    private val db = AppDatabase(driverFactory.createDriver())
    private val queries = db.appDatabaseQueries

    fun getMuscleGroups(): List<MuscleGroup> {
        return queries.selectAllMuscleGroups(
            mapper = { id, name -> MuscleGroup(id.toInt(), name) }
        ).executeAsList()
    }

    fun clearAndCreateMuscleGroups(muscleGroups: List<MuscleGroup>) {
        queries.transaction {
            queries.removeAllMuscleGroups()
            muscleGroups.forEach { group ->
                queries.insertMuscleGroup(
                    id = group.id.toLong(),
                    name = group.name,
                )
            }
        }
    }
}