package com.rroperations.services

import com.rroperations.repositories.TrainCarRepository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

class DynamoClientService(private val tableName: String)
{
    private fun dynamoDbTable(): DynamoDbTable<TrainCarRepository> {
        val envRegion = System.getenv("AWS_REGION")
        val region = Region.of(envRegion)

        val dynamoDbClient = DynamoDbClient.builder()
            .endpointOverride(URI(System.getenv("DYNAMO_ENDPOINT")))
            .region(region)
            .build()

        val dynamoDbClientEnhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build()

        return dynamoDbClientEnhancedClient
            .table(tableName, TableSchema.fromBean(TrainCarRepository::class.java))
    }
}
