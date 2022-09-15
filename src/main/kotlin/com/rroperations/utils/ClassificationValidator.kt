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
        var exists = true
        val classifications = service.getAll(if (classification.type == "DESTINATION") FindDestination() else FindReceiver())
        println("classifications: $classifications")

        classifications.stream().forEach { c ->
            if (c.classification == classification.classification || c.name == classification.name )
                exists = false
        }

        println(exists)

        return exists
    }

    open fun validateUpdate(classification: Classification): Boolean {
        val classifications = service.getAll(if (classification.type == "DESTINATION") FindDestination(classification.id) else FindReceiver(classification.id))
        val nameCount = classifications.stream().filter { c -> c.name == classification.name && c.id != classification.id }.count() == 0L
        val orderCount = classifications.stream().filter { c -> c.classification == classification.classification && c.id != classification.id }.count() == 0L

        return nameCount && orderCount
    }
}