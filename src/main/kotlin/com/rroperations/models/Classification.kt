package com.rroperations.models

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
open class Classification {
    @get: DynamoDbSortKey
    open var id: String? = null
    @get: DynamoDbPartitionKey
    open var type: String? = null
    open var name: String? = null
    open var classification: Int? = -1
}