package com.rroperations.controllers

import com.rroperations.models.ReceiverEntity
import com.rroperations.repositories.ClassificationRepository
import com.rroperations.services.ClassificationService
import io.micronaut.http.annotation.*
import java.util.UUID
import javax.validation.Valid

@Controller("receiver")
open class ReceiverController() {

    val service = ClassificationService("Trains")

    @Post()
    open fun save(@Valid @Body receiver: ReceiverEntity): ReceiverEntity {
        var clsEntity = ClassificationRepository(UUID.randomUUID().toString(),receiver.name, receiver.classification, receiver.type)
        service.save(clsEntity)
        return receiver
    }

    @Get()
    open fun findAll(): ArrayList<ClassificationRepository> {
        return service.getAll("RECEIVER")
    }

    @Get("/{id}")
    open fun findById(id: String): ClassificationRepository? {
        return service.findById("RECEIVER", id)
    }

    @Delete("/{id}")
    open fun delete(id: String): ClassificationRepository {
        return service.delete("RECEIVER", id)
    }

    @Put("/{id}")
    open fun update(id: UUID, @Body receiver: ReceiverEntity): ClassificationRepository{
        receiver.id = id
        return service.update(receiver)
    }

//    @Get("/receiver/{name}")
//    open fun findOne(name: String): ReceiverEntity? {
//        val receiverTable: DynamoDbTable<ReceiverEntity> = dynamoDbTable()
//        val key = Key.builder().partitionValue(AttributeValue.builder().s(name).build()).build()
//        return receiverTable.getItem { r -> r.key(key) }
//    }
//
//    @Delete("/receiver/{name}")
//    open fun deleteOne(name: String): ReceiverEntity? {
//        val receiverTable: DynamoDbTable<ReceiverEntity> = dynamoDbTable()
//        val key = Key.builder().partitionValue(AttributeValue.builder().s(name).build()).build()
//        return receiverTable.deleteItem { r -> r.key(key) }
//    }
}
