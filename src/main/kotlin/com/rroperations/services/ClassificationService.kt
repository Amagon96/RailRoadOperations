package com.rroperations.services

import com.rroperations.models.ReceiverEntity
import com.rroperations.repositories.ClassificationRepository
import jakarta.inject.Singleton
import software.amazon.awssdk.enhanced.dynamodb.*
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest
import software.amazon.awssdk.services.dynamodb.model.QueryRequest
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException

import java.net.URI

@Singleton
class ClassificationService(private val tableName: String)
{
    private val connection = dynamoDbTable()

    fun save(data: ClassificationRepository) {
        println(data)
        connection.putItem(data)
    }

    fun getAllScan(type: String): ArrayList<ClassificationRepository> {
        val classificationRepository = ArrayList<ClassificationRepository>()
        val results = connection.scan().items().iterator()
        while (results.hasNext()) {
            val cResult = results.next()
            if (cResult.type==type) {
                classificationRepository.add(cResult)
            }
        }
        return classificationRepository
    }

    fun getAll(type: String): ArrayList<ClassificationRepository> {
        val query = QueryConditional.keyEqualTo(Key.builder().partitionValue(type).build())
        val classificationRepository = ArrayList<ClassificationRepository>()
        val results = connection.query(query).items().iterator()
        while (results.hasNext()) {
                classificationRepository.add(results.next())
        }
        return classificationRepository
    }

    fun existsTable(connection: DynamoDbTable<ClassificationRepository>): Boolean {
        return try {
            connection.describeTable(
            )
            true
        } catch (e: ResourceNotFoundException) {
            false
        }
    }

    private fun dynamoDbTable(): DynamoDbTable<ClassificationRepository> {
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
            .table(tableName, TableSchema.fromBean(ClassificationRepository::class.java))

        if (!existsTable(table)) {
            table.createTable()
        }

        return table
    }
}