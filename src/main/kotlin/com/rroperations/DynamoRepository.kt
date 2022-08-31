package com.example

import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException


@Requirements(
    Requires(beans = [DynamoDbClient::class, DynamoConfiguration::class]),
    Requires(condition = CIAwsCredentialsProviderChainCondition::class),
    Requires(condition = CIAwsRegionProviderChainCondition::class),
)
@Singleton
class DynamoRepository(
    private val dynamoDbClient: DynamoDbClient,
    private val dynamoConfiguration: DynamoConfiguration
) {

    fun existsTable(): Boolean {
        return try {
            dynamoDbClient.describeTable(
                DescribeTableRequest.builder()
                    .tableName(dynamoConfiguration.tableName)
                    .build()
            )
            true
        } catch (e: ResourceNotFoundException) {
            false
        }
    }
}
