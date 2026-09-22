package com.example.curls.features.exercises.domain.mappers

import com.example.curls.features.exercises.domain.Exercise
import com.example.curls.cache.Category as CategoryLocal
import com.example.curls.cache.Equipment as EquipmentLocal
import com.example.curls.cache.Exercise as ExerciseLocal
import com.example.curls.cache.Image as ImageLocal
import com.example.curls.cache.Muscle as MuscleLocal
import com.example.curls.cache.Translation as TranslationLocal
import com.example.curls.cache.Video as VideoLocal

fun ExerciseLocal.toExerciseDomain(
    categories: List<CategoryLocal>,
    translations: List<TranslationLocal>,
    images: List<ImageLocal>,
    videos: List<VideoLocal>,
    muscles: List<MuscleLocal>,
    secondaryMuscles: List<MuscleLocal>,
    equipment: List<EquipmentLocal>,
): Exercise? {
    return Exercise(
        id = id,
        category = categories
            .firstOrNull { category -> category.id == categoryId }?.toCategoryDomain()
            ?: return null,
        translation = translations
            .firstOrNull { translation -> translation.exerciseId == id && translation.languageId == 2L }
            ?.toTranslationDomain()
            ?: return null,
        images = images.filter { image -> image.exerciseId == id }
            .map { image -> image.toImageDomain() },
        videos = videos.filter { video -> video.exerciseId == id }
            .map { video -> video.toVideoDomain() },
        muscles = muscles.map { muscle -> muscle.toMuscleDomain() },
        secondaryMuscles = secondaryMuscles.map { muscle -> muscle.toMuscleDomain() },
        equipment = equipment.map { equipment -> equipment.toEquipmentDomain() },
    )
}