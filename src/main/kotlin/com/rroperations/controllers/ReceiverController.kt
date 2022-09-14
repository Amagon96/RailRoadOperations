package com.rroperations.controllers

import com.rroperations.models.Classification
import com.rroperations.models.FindReceiver
import com.rroperations.models.ReceiverModel
import com.rroperations.services.ClassificationService
import io.micronaut.http.annotation.*
import java.util.UUID
import javax.validation.Valid

@Controller("receiver")
open class ReceiverController(private val service: ClassificationService) {

    @Post()
    open fun save(@Valid @Body receiver: ReceiverModel): Classification {
        receiver.id = UUID.randomUUID().toString()
        service.save(receiver)
        return receiver
    }

    @Get()
    open fun findAll(): MutableList<Classification> {
        return service.getAll(FindReceiver())
    }

    @Get("/{id}")
    open fun findById(id: String): Classification? {
        return service.findById(FindReceiver(id))
    }

    @Delete("/{id}")
    open fun delete(id: String): Classification {
        return service.delete(FindReceiver(id))
    }

    @Delete("/")
    open fun deleteAll(): MutableList<Classification> {
        return service.deleteAllByType(FindReceiver())
    }

    @Put("/{id}")
    open fun update(id: String, @Body receiver: ReceiverModel): Classification {
        receiver.id = id
        return service.update(receiver)
    }
}
