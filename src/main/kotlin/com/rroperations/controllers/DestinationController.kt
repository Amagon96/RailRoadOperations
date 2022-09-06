package com.rroperations.controllers


import com.rroperations.models.DestinationModel
import com.rroperations.services.DestinationService
import io.micronaut.http.annotation.*
import java.util.UUID
import javax.validation.Valid

@Controller("destination")
 open class DestinationController(private val service: DestinationService) {

    @Post()
    open fun save(@Valid @Body destination: DestinationModel) {
        destination.id = UUID.randomUUID().toString()
        return service.save(destination)
    }

    @Get()
    open fun findAll(): MutableList<DestinationModel> {
        return service.getAll()
    }

    @Get("/{id}")
    open fun findById(id: String): DestinationModel? {
        return service.findById(id)
    }

    @Delete("/{id}")
    open fun delete(id: String): DestinationModel {
        return service.delete(id)
    }

    @Put("/{id}")
    open fun update(id: String, @Body destination: DestinationModel): DestinationModel {
        destination.id = id
        return service.update(destination)

    }
}
