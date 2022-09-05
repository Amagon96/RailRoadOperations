package com.rroperations.repositories

import io.micronaut.core.annotation.Introspected
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.util.UUID

@Introspected
@DynamoDbBean
data class TrainCarRepository( @get: DynamoDbPartitionKey
    var name: String? = null,
    var destination: String? = null,
    var receiver: String? = null,
    var classificationTrack: String? = null,
    var trainId: UUID? = null)