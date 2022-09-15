package com.rroperations.controllers

import com.rroperations.models.Classification
import com.rroperations.models.FindReceiver
import com.rroperations.models.ReceiverModel
import com.rroperations.services.ClassificationService
import com.rroperations.utils.ClassificationValidator
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import java.util.UUID
import javax.validation.Valid

@Controller("receiver")
open class ReceiverController(private val service: ClassificationService, private val validator: ClassificationValidator) {

    @Post()
    open fun save(@Valid @Body receiver: ReceiverModel): Classification {
        receiver.id = UUID.randomUUID().toString()

        if (validator.validateSave(receiver) == null) {
            println("Saving")
            service.save(receiver)
        }
        println("Not Saving")
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
        if (validator.validateUpdate(receiver)) {
            service.update(receiver)
            println("Updating")
        }

        println("Not updating")
        return service.update(receiver)
    }
}
