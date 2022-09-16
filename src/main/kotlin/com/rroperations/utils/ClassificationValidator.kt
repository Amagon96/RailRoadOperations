package com.rroperations.utils

import com.rroperations.models.Classification
import com.rroperations.models.FindDestination
import com.rroperations.models.FindReceiver

import com.rroperations.services.ClassificationService
import jakarta.inject.Singleton

@Singleton
open class ClassificationValidator(private val service: ClassificationService) {

    open fun validateSave(classification: Classification): Boolean {
        return classificationIsNew(classification)
    }

    open fun classificationIsNew(classification: Classification): Boolean {
        var isNew = true
        val classifications = service.getAll(if (classification.type == "DESTINATION") FindDestination() else FindReceiver())

        run breaking@ {
            classifications.forEach { c ->
                if (c.classification == classification.classification || c.name == classification.name ) {
                    isNew = false
                    return@breaking
                }
            }
        }

        return isNew
    }

    open fun validUpdate(classification: Classification): Boolean {
        var validUpdate = true
        val classifications = service.getAll(if (classification.type == "DESTINATION") FindDestination(classification.id) else FindReceiver(classification.id))

        if (classifications.stream().filter { c -> c.name == classification.name && c.id != classification.id }.count() != 0L) {
            validUpdate = false
        } else if (classifications.stream().filter { c -> c.classification == classification.classification && c.id != classification.id }.count() != 0L) {
            validUpdate = false
        }

        return validUpdate
    }
}