package com.rroperations.services

import com.rroperations.repositories.DestinationRepository
import jakarta.inject.Singleton
import software.amazon.awssdk.enhanced.dynamodb.*
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException

import java.net.URI

@Singleton
class DestinationService(private val tableName: String)
{
    private val connection = dynamoDbTable()

    fun save(data: DestinationRepository) {
        println(data)
        connection.putItem(data)
    }

    fun getAllScan(type: String): ArrayList<DestinationRepository> {
        val destinationRepository = ArrayList<DestinationRepository>()
        val results = connection.scan().items().iterator()
        while (results.hasNext()) {
            val cResult = results.next()
            if (cResult.type==type) {
                destinationRepository.add(cResult)
            }
        }
        return destinationRepository
    }

    fun getAll(type: String): ArrayList<DestinationRepository> {
        val query = QueryConditional.keyEqualTo(Key.builder().partitionValue(type).build())
        val classificationRepository = ArrayList<DestinationRepository>()
        val results = connection.query(query).items().iterator()
        while (results.hasNext()) {
                classificationRepository.add(results.next())
        }
        return classificationRepository
    }

    fun existsTable(connection: DynamoDbTable<DestinationRepository>): Boolean {
        return try {
            connection.describeTable(
            )
            true
        } catch (e: ResourceNotFoundException) {
            false
        }
    }

    private fun dynamoDbTable(): DynamoDbTable<DestinationRepository> {
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
            .table(tableName, TableSchema.fromBean(DestinationRepository::class.java))

        if (!existsTable(table)) {
            table.createTable()
        }

        return table
    }
}