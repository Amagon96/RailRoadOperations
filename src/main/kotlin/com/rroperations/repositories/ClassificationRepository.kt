package com.rroperations.repositories

import io.micronaut.core.annotation.Introspected
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

// type can only be "DESTINATION" or "RECEIVER"
@DynamoDbBean
data class ClassificationRepository (
    @get: DynamoDbSortKey var id: String? = null,
    var name: String? = null,
    var classification: Int? = -1,
    @get: DynamoDbPartitionKey var type: String? = null
)