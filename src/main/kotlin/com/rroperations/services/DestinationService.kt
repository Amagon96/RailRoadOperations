package com.rroperations.services


import com.rroperations.models.DestinationModel
import jakarta.inject.Singleton
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException

import java.net.URI

@Singleton
class DestinationService {
    private val connection = dynamoDbTable()
    private val type = "DESTINATION"


    fun save(data: DestinationModel) {
        return connection.putItem(data)
    }

    fun getAll(): MutableList<DestinationModel> {
        val query: QueryConditional = QueryConditional
            .keyEqualTo(
                Key.builder().partitionValue(type).build()
            )
        return connection.query { request -> request.queryConditional(query) }
            .items().toMutableList()
    }

    fun findById(id: String): DestinationModel? {
        return connection.getItem(
            Key.builder()
                .partitionValue(type)
                .sortValue(id)
                .build()
        )
    }

    fun delete(id: String): DestinationModel {
        return connection.deleteItem(
            Key.builder()
                .partitionValue(type)
                .sortValue(id)
                .build()
        )
    }

    fun update(receiver: DestinationModel): DestinationModel {
        return connection.updateItem(receiver)
    }

    private fun dynamoDbTable(): DynamoDbTable<DestinationModel> {
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
            .table("Trains", TableSchema.fromBean(DestinationModel::class.java))


        try {
            table.describeTable()
        } catch (e: ResourceNotFoundException) {
            table.createTable()
        }

        return table
    }
}


