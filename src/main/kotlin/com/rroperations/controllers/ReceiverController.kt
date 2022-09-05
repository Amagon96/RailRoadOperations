package com.rroperations.controllers


import com.rroperations.models.ReceiverModel
import com.rroperations.services.ReceiverService
import io.micronaut.http.annotation.*
import java.util.UUID
import javax.validation.Valid

@Controller("receiver")
 open class ReceiverController {

    private val service: ReceiverService
    constructor(service: ReceiverService) {
        this.service = service
    }

    @Post()
    open fun save(@Valid @Body receiver: ReceiverModel) {
        receiver.id = UUID.randomUUID().toString()
        return service.save(receiver)
    }

    @Get()
    open fun findAll(): ArrayList<ReceiverModel> {
        return service.getAll()
    }

    @Get("/{id}")
    open fun findById(id: String): ReceiverModel? {
        return service.findById(id)
    }

    @Delete("/{id}")
    open fun delete(id: String): ReceiverModel {
        return service.delete(id)
    }

    @Put("/{id}")
    open fun update(id: String, @Body receiver: ReceiverModel): ReceiverModel {
        receiver.id = id
        return service.update(receiver)

    }
}
