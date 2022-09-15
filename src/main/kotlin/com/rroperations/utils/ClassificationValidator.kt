package com.rroperations.utils

import com.rroperations.models.Classification
import com.rroperations.models.DestinationModel
import com.rroperations.models.FindDestination
import com.rroperations.models.FindReceiver

import com.rroperations.services.ClassificationService
import jakarta.inject.Singleton

@Singleton
open class ClassificationValidator(private val service: ClassificationService) {

    open fun validateSave(classification: Classification): Classification? {
        return classificationExists(classification)
    }

    open fun classificationExists(classification: Classification): Classification? {
        var exists: Classification? = null
        val classifications = service.getAll(if (classification.type == "DESTINATION") FindDestination() else FindReceiver())
        println("classifications: $classifications")

        classifications.stream().forEach { c ->
            if (c.classification == classification.classification || c.name == classification.name )
                exists = c
        }

        return exists
    }

    open fun validateUpdate(destination: DestinationModel): Boolean {
        val classifications = service.getAll(if (destination.type == "DESTINATION") FindDestination(destination.id) else FindReceiver(destination.id))
        val nameCount = classifications.stream().filter { c -> c.name == destination.name && c.id != destination.id }.count() == 0L
        val orderCount = classifications.stream().filter { c -> c.classification == destination.classification && c.id != destination.id }.count() == 0L

        return nameCount && orderCount
    }
}