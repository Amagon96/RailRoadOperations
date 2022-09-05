package com.rroperations.services


import com.rroperations.models.ReceiverModel
import jakarta.inject.Singleton
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException

import java.net.URI

@Singleton
class ReceiverService {
    private val connection = dynamoDbTable()
    private val type = "RECEIVER"


    fun save(data: ReceiverModel) {
        return connection.putItem(data)
    }

    fun getAll(): ArrayList<ReceiverModel> {
        val classificationRepository = ArrayList<ReceiverModel>()
        val results = connection.scan().items().iterator()
        while (results.hasNext()) {
            val cResult = results.next()
            if (cResult.type == type) {
                classificationRepository.add(cResult)
            }
        }
        return classificationRepository
    }

    //
    fun findById(id: String): ReceiverModel? {
        return connection.getItem(
            Key.builder()
                .partitionValue(type)
                .sortValue(id)
                .build()
        )
    }

    fun delete(id: String): ReceiverModel {
        return connection.deleteItem(
            Key.builder()
                .partitionValue(type)
                .sortValue(id)
                .build()
        )
    }

    fun update(receiver: ReceiverModel): ReceiverModel {
        return connection.updateItem(receiver)
    }

    private fun dynamoDbTable(): DynamoDbTable<ReceiverModel> {
        val envRegion = System.getenv("AWS_REGION")
        val region = Region.of(envRegion)

        val dynamoDbClient = DynamoDbClient.builder()
            .endpointOverride(URI(System.getenv("DYNAMO_ENDPOINT")))
            .region(region)
            .build()

        val dynamoDbClientEnhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build()

        val table = dynamoDbClientEnhancedClient
            .table("Trains", TableSchema.fromBean(ReceiverModel::class.java))


        try {
            table.describeTable()
        } catch (e: ResourceNotFoundException) {
            table.createTable()
        }

        return table
    }
}

