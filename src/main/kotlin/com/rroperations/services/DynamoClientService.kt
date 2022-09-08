package com.rroperations.services

import com.rroperations.models.Classification
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException
import java.net.URI

class DynamoClientService() {
    private fun dynamoDbTable(): DynamoDbTable<Classification> {
        val envRegion = System.getenv("AWS_REGION")
        val region = Region.of(envRegion)

        val dynamoDbClient =
            DynamoDbClient.builder()
                .endpointOverride(URI(System.getenv("DYNAMO_ENDPOINT")))
                .region(region)
                .build()

        val dynamoDbClientEnhancedClient =
            DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build()

        val table =
            dynamoDbClientEnhancedClient.table(
                System.getenv("DYNAMO_DB_TABLE_NAME"),
                TableSchema.fromBean(Classification::class.java)
            )

        try {
            table.describeTable()
        } catch (e: ResourceNotFoundException) {
            table.createTable()
        }

        return table
    }
}
