package com.rroperations.controllers

import com.rroperations.models.ReceiverEntity
import com.rroperations.repositories.ClassificationRepository
import com.rroperations.services.ClassificationService
import io.micronaut.http.annotation.*
import java.util.UUID
import javax.validation.Valid

@Controller
open class ReceiverController() {

    val service = ClassificationService("classifications")

    @Post("/receiver")
    open fun save(@Valid @Body receiver: ReceiverEntity): ReceiverEntity {
        var clsEntity = ClassificationRepository(UUID.randomUUID().toString(),receiver.name, receiver.classification, receiver.type)
        service.save(clsEntity)
        return receiver
    }

    @Get("/receivers")
    open fun findAll(): ArrayList<ClassificationRepository> {
        return service.getAll("RECEIVER")
    }
//
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
