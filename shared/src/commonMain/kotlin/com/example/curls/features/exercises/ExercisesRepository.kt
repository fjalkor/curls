package com.example.curls.features.exercises

import app.cash.sqldelight.coroutines.mapToList
import com.example.curls.cache.Database
import com.example.curls.cache.EquipmentExerciseCrossRef
import com.example.curls.cache.MuscleExerciseCrossRef
import com.example.curls.cache.MuscleSecondaryExerciseCrossRef
import com.example.curls.features.exercises.domain.Exercise
import com.example.curls.features.exercises.domain.mappers.toCategoryDomain
import com.example.curls.features.exercises.domain.mappers.toExerciseDomain
import com.example.curls.features.exercises.network.ExercisesApi
import com.example.curls.features.exercises.network.mappers.ExerciseDetailedLocal
import com.example.curls.features.exercises.network.mappers.toCategoryLocal
import com.example.curls.features.exercises.network.mappers.toEquipmentLocal
import com.example.curls.features.exercises.network.mappers.toExerciseDetailedLocal
import com.example.curls.features.exercises.network.mappers.toMuscleLocal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import com.example.curls.cache.Category as CategoryLocal
import com.example.curls.cache.Equipment as EquipmentLocal
import com.example.curls.cache.Exercise as ExerciseLocal
import com.example.curls.cache.Image as ImageLocal
import com.example.curls.cache.Muscle as MuscleLocal
import com.example.curls.cache.Translation as TranslationLocal
import com.example.curls.cache.Video as VideoLocal

class ExercisesRepository(private val api: ExercisesApi, private val db: Database) {
    private val localExercisesFlow = db.getExercisesFlow().mapToList(Dispatchers.IO)
    private val localTranslationsFlow = db.getTranslationsFlow().mapToList(Dispatchers.IO)
    private val localImagesFlow = db.getImagesFlow().mapToList(Dispatchers.IO)
    private val localVideosFlow = db.getVideosFlow().mapToList(Dispatchers.IO)
    private val musclesCrossRefsFlow = db.getMuscleCrossRefs().mapToList(Dispatchers.IO)
    private val secondaryMusclesCrossRefsFlow =
        db.getSecondaryMuscleCrossRefs().mapToList(Dispatchers.IO)
    private val equipmentCrossRefsFlow = db.getEquipmentCrossRefs().mapToList(Dispatchers.IO)
    private val localCategoriesFlow = db.getCategoriesFlow().mapToList(Dispatchers.IO)
    private val localMuscles = db.getMusclesFlow().mapToList(Dispatchers.IO)
    private val localEquipmentFlow = db.getEquipmentFlow().mapToList(Dispatchers.IO)

    @Suppress("UNCHECKED_CAST")
    val exercisesFlow: Flow<List<Exercise>> = combine(
        localExercisesFlow,
        localTranslationsFlow,
        localImagesFlow,
        localVideosFlow,
        musclesCrossRefsFlow,
        secondaryMusclesCrossRefsFlow,
        equipmentCrossRefsFlow,
        localCategoriesFlow,
        localMuscles,
        localEquipmentFlow,
    ) {
        val exercisesLocal = (it[0] as? List<ExerciseLocal>) ?: emptyList()
        val translations = (it[1] as? List<TranslationLocal>) ?: emptyList()
        val images = (it[2] as? List<ImageLocal>) ?: emptyList()
        val videos = (it[3] as? List<VideoLocal>) ?: emptyList()
        val musclesCrossRefs = (it[4] as? List<MuscleExerciseCrossRef>) ?: emptyList()
        val secondaryMusclesCrossRefs =
            (it[5] as? List<MuscleSecondaryExerciseCrossRef>) ?: emptyList()
        val equipmentCrossRefs = (it[6] as? List<EquipmentExerciseCrossRef>) ?: emptyList()
        val localCategories = (it[7] as? List<CategoryLocal>) ?: emptyList()
        val localMuscles = (it[8] as? List<MuscleLocal>) ?: emptyList()
        val localEquipment = (it[9] as? List<EquipmentLocal>) ?: emptyList()

        return@combine exercisesLocal.mapNotNull { exercise ->
            val muscleIds = musclesCrossRefs
                .filter { crossRef -> crossRef.exerciseId == exercise.id }
                .map { crossRef -> crossRef.muscleId }

            val secondaryMuscleIds = secondaryMusclesCrossRefs
                .filter { crossRef -> crossRef.exerciseId == exercise.id }
                .map { crossRef -> crossRef.muscleId }

            val equipmentIds = equipmentCrossRefs
                .filter { crossRef -> crossRef.exerciseId == exercise.id }
                .map { crossRef -> crossRef.equipmentId }

            exercise.toExerciseDomain(
                categories = localCategories,
                translations = translations,
                images = images,
                videos = videos,
                muscles = localMuscles.filter { muscle -> muscle.id in muscleIds },
                secondaryMuscles = localMuscles.filter { muscle -> muscle.id in secondaryMuscleIds },
                equipment = localEquipment.filter { equipment -> equipment.id in equipmentIds },
            )
        }
    }.flowOn(Dispatchers.IO)

    suspend fun initialize() = withContext(Dispatchers.IO) {
        coroutineScope {
            val categories = populateCategoriesTask()
            val muscles = populateMusclesTask()
            val equipment = populateEquipmentTask()

            categories.await()
            muscles.await()
            equipment.await()

            // fetch all at once for now
            val mapped: List<ExerciseDetailedLocal> = api.getExercises(limit = 1000).results
                ?.mapNotNull { exerciseRemote ->
                    runCatching { exerciseRemote.toExerciseDetailedLocal() }
                        .onFailure { println(it.message) }
                        .getOrNull()
                }.orEmpty()

            db.insertExercises(mapped)
        }
    }

    fun getCategoriesFlow() = db.getCategoriesFlow().mapToList(Dispatchers.IO)
        .map { listOfCategories ->
            listOfCategories.map { it.toCategoryDomain() }
        }

    private fun CoroutineScope.populateCategoriesTask() = async {
        if (db.getCategories().isEmpty())
            api.getCategories().results
                ?.mapNotNull { it.toCategoryLocal() }
                ?.forEach { db.insertCategory(it) }
    }

    private fun CoroutineScope.populateMusclesTask() = async {
        if (db.getMuscles().isEmpty())
            api.getMuscles().results
                ?.mapNotNull { it.toMuscleLocal() }
                ?.forEach { db.insertMuscle(it) }
    }

    private fun CoroutineScope.populateEquipmentTask() = async {
        if (db.getEquipment().isEmpty())
            api.getEquipment().results
                ?.mapNotNull { it.toEquipmentLocal() }
                ?.forEach { db.insertEquipment(it) }
    }
}
