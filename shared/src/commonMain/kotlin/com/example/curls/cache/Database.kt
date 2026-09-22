package com.example.curls.cache

import app.cash.sqldelight.coroutines.asFlow
import com.example.curls.features.exercises.network.mappers.ExerciseDetailedLocal

class Database(driverFactory: DatabaseDriverFactory) {
    private val db = AppDatabase(driverFactory.createDriver())
    private val queries = db.appDatabaseQueries

    fun getCategories() = queries.selectAllCategories().executeAsList()
    fun getMuscles() = queries.selectAllMuscles().executeAsList()
    fun getEquipment() = queries.selectAllEquipment().executeAsList()


    fun getCategoriesFlow() = queries.selectAllCategories().asFlow()
    fun getMusclesFlow() = queries.selectAllMuscles().asFlow()
    fun getEquipmentFlow() = queries.selectAllEquipment().asFlow()
    fun getExercisesFlow() = queries.selectAllExercises().asFlow()
    fun getTranslationsFlow() = queries.selectAllTranslations().asFlow()
    fun getImagesFlow() = queries.selectAllImages().asFlow()
    fun getVideosFlow() = queries.selectAllVideos().asFlow()

    fun getMuscleCrossRefs() = queries.selectAllMuscleCrossRefs().asFlow()
    fun getSecondaryMuscleCrossRefs() = queries.selectAllSecondaryMuscleCrossRefs().asFlow()
    fun getEquipmentCrossRefs() = queries.selectAllEquipmentCrossRefs().asFlow()

    fun insertCategory(category: Category) = queries.insertCategory(
        id = category.id,
        name = category.name,
    )

    fun insertMuscle(muscle: Muscle) = queries.insertMuscle(
        id = muscle.id,
        name = muscle.name,
        nameEn = muscle.nameEn,
        isFront = muscle.isFront,
        imageUrlMain = muscle.imageUrlMain,
        imageUrlSecondary = muscle.imageUrlSecondary,
    )

    fun insertEquipment(equipment: Equipment) = queries.insertEquipment(
        id = equipment.id,
        name = equipment.name,
    )

    fun insertExercises(dtos: List<ExerciseDetailedLocal>) {
        db.transaction {
            dtos.forEach { exercise ->
                queries.insertExercise(exercise.id, exercise.categoryId)
                exercise.translations.forEach {
                    queries.insertTranslation(
                        it.id,
                        it.name,
                        it.exerciseId,
                        it.description,
                        it.languageId
                    )
                }
                exercise.images.forEach {
                    queries.insertImage(
                        it.id,
                        it.exerciseId,
                        it.imageUrl,
                        it.thumbnailSmall,
                        it.thumbnailMedium
                    )
                }
                exercise.videos.forEach {
                    queries.insertVideo(
                        it.id,
                        it.exerciseId,
                        it.videoUrl,
                        it.size,
                        it.duration,
                        it.width,
                        it.height,
                        it.codec,
                        it.codecLong
                    )
                }
                exercise.muscleIds.forEach { queries.insertMuscleCrossRef(exercise.id, it) }
                exercise.secondaryMuscleIds.forEach { queries.insertMuscleSecondaryCrossRef(exercise.id, it) }
                exercise.equipmentIds.forEach { queries.insertEquipmentCrossRef(exercise.id, it) }
            }
        }
    }
}