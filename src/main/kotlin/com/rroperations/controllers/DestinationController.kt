package com.rroperations.controllers

import com.rroperations.models.Classification
import com.rroperations.models.DestinationModel
import com.rroperations.models.FindDestination
import com.rroperations.services.ClassificationService
import com.rroperations.utils.ClassificationValidator
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import java.util.UUID
import javax.validation.Valid

@Controller("destination")
open class DestinationController(private val service: ClassificationService, private val validator: ClassificationValidator) {

    @Post()
    open fun save(@Valid @Body destination: DestinationModel): HttpResponse<Classification>? {
        destination.id = UUID.randomUUID().toString()

        return if (validator.validateSave(destination) == null) {
            service.save(destination)
            HttpResponse.created(destination)
        } else {
            HttpResponse.badRequest(destination)
        }
    }

    @Get()
    open fun findAll(): MutableList<Classification> {
        return service.getAll(FindDestination())
    }

    @Get("/{id}")
    open fun findById(id: String): Classification? {
        return service.findById(FindDestination(id))
    }

    @Delete("/{id}")
    open fun delete(id: String): Classification {
        return service.delete(FindDestination(id))
    }

    @Delete("/")
    open fun deleteAll(type: String): MutableList<Classification> {
        return service.deleteAllByType(FindDestination())
    }

    @Put("/{id}")
    open fun update(id: String, @Body destination: DestinationModel): HttpResponse<Classification>? {
        destination.id = id
        return if (validator.validateUpdate(destination)) {
            service.update(destination)
            HttpResponse.ok(destination)
        } else {
            HttpResponse.badRequest(destination)
        }
    }
}
