package com.rroperations.services

import com.rroperations.repositories.ClassificationRepository
import jakarta.inject.Singleton
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException

import java.net.URI

@Singleton
class ClassificationService(private val tableName: String) {
    private val connection = dynamoDbTable()

    fun save(data: ClassificationRepository) {
        println(data)
        connection.putItem(data)
    }

    fun getAll(type: String): ArrayList<ClassificationRepository> {
        val classificationRepository = ArrayList<ClassificationRepository>()
        val results = connection.scan().items().iterator()
        while (results.hasNext()) {
            val cResult = results.next()
            if (cResult.type == type) {
                classificationRepository.add(cResult)
            }
        }
        return classificationRepository
    }

    fun getSingle(type: String): ClassificationRepository {
        return connection.getItem(Key.builder().partitionValue("fd").build())
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


        try {
            table.describeTable()
        } catch (e: ResourceNotFoundException) {
            table.createTable()
        }

//        val result = table.describeTable()
//        if (!result) {
//            table.createTable()
//        }

        return table
    }
}
