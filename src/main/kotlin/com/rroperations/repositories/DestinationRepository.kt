package com.rroperations.repositories

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

// type can only be "DESTINATION" or "RECEIVER"
@DynamoDbBean
data class DestinationRepository (
    @get: DynamoDbSortKey var id: String? = null,
    var name: String? = null,
    var classification: Int? = -1,
    @get: DynamoDbPartitionKey var type: String? = null
)