package com.rroperations.controllers

import com.rroperations.models.DestinationEntity
import com.rroperations.repositories.DestinationRepository
import com.rroperations.services.DestinationService
import io.micronaut.http.annotation.*
import java.util.UUID
import javax.validation.Valid

@Controller
open class DestinationController() {

    private val service = DestinationService("destinations")

    @Post("/destination")
    open fun save(@Valid @Body destination: DestinationEntity): DestinationEntity {
        val clsEntity = DestinationRepository(UUID.randomUUID().toString(),destination.name, destination.classification, destination.type)
        service.save(clsEntity)
        return destination
    }

    @Get("/destinations")
    open fun findAll(): ArrayList<DestinationRepository> {
        return service.getAll("DESTINATION")
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
