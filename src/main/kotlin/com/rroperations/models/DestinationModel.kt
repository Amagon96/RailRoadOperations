package com.rroperations.models

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
data class DestinationModel(
    @get: DynamoDbSortKey
    override var id: String? = null,
    override var name: String? = null,
    override var classification: Int? = -1,
    @get: DynamoDbPartitionKey
    override var type: String? = "DESTINATION"
): Classification()
