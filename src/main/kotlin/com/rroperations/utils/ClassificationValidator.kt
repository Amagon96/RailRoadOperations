package com.rroperations.utils

import com.rroperations.models.Classification
import com.rroperations.models.FindDestination
import com.rroperations.models.FindReceiver

import com.rroperations.services.ClassificationService
import jakarta.inject.Singleton

@Singleton
open class ClassificationValidator(private val service: ClassificationService) {

    open fun validateSave(classification: Classification): Boolean {
        return classificationExists(classification)
    }

    open fun classificationExists(classification: Classification): Boolean {
        val (classificationById, classificationByName) = Pair(
            service.findById(if (classification.type === "DESTINATION")
                FindDestination(classification.id) else
                FindReceiver(classification.id)
            )?.id,
            service.findByName(classification)?.name
        )

        return classificationById == null && classificationByName == null
    }
}