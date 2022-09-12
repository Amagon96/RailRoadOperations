package com.rroperations.controllers

import com.rroperations.models.Classification
import com.rroperations.models.DestinationModel
import com.rroperations.models.FindDestination
import com.rroperations.services.ClassificationService
import io.micronaut.http.annotation.*
import java.util.UUID
import javax.validation.Valid

@Controller("destination")
open class DestinationController(private val service: ClassificationService) {

    @Post()
    open fun save(@Valid @Body destination: DestinationModel): Classification {
        destination.id = UUID.randomUUID().toString()
        service.save(destination)
        return destination
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

    @Put("/{id}")
    open fun update(id: String, @Body destination: DestinationModel): Classification {
        destination.id = id
        return service.update(destination)
    }
}
