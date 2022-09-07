package com.rroperations.models

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

interface Classification {
    @get: DynamoDbSortKey
    var id: String?
    @get: DynamoDbPartitionKey
    var type: String
    var name: String?
    var classification: Int?
}