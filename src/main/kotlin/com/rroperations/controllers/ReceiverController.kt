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
    open fun save(@Valid @Body receiver: ReceiverModel): HttpResponse<Classification>?  {
        receiver.id = UUID.randomUUID().toString()

        return if (validator.validateSave(receiver)) {
            service.save(receiver)
            HttpResponse.created(receiver)
        } else {
            HttpResponse.badRequest(receiver)
        }
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
    open fun update(id: String, @Body receiver: ReceiverModel): HttpResponse<Classification>? {
        receiver.id = id
        return if (validator.validateUpdate(receiver)) {
            service.update(receiver)
            HttpResponse.ok(receiver)
        } else {
            HttpResponse.badRequest(receiver)
        }
    }
}
