package com.rroperations.services

import com.rroperations.models.Classification
import com.rroperations.models.FindClassification
import com.rroperations.models.FindDestination
import com.rroperations.models.FindReceiver
import jakarta.inject.Singleton
import java.net.URI
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException

@Singleton
class ClassificationService {
    private val connection = dynamoDbTable()

    fun save(data: Classification) {
        return connection.putItem(data)
    }

    fun getAll(findClassification: FindClassification): MutableList<Classification> {
        val query: QueryConditional =
                QueryConditional.keyEqualTo(
                        Key.builder().partitionValue(findClassification.type).build()
                )
        return connection
                .query { request -> request.queryConditional(query) }
                .items()
                .sortedBy { it.classification }
                .toMutableList()
    }

    fun findById(findClassification: FindClassification): Classification? {
        return connection.getItem(
            Key.builder()
                .partitionValue(findClassification.type)
                .sortValue(findClassification.id)
                .build()
        )
    }

    fun delete(findClassification: FindClassification): Classification {
        return connection.deleteItem(
            Key.builder()
                .partitionValue(findClassification.type)
                .sortValue(findClassification.id)
                .build()
        )
    }

    fun deleteAllByType(findClassification: FindClassification): MutableList<Classification> {
        val classifications = getAll(findClassification)

        if (findClassification.type == "DESTINATION") {
            classifications.stream().forEach { classification ->  delete(FindDestination(classification.id)) }
        } else {
            classifications.stream().forEach { classification ->  delete(FindReceiver(classification.id)) }
        }

        return classifications
    }

    fun update(receiver: Classification): Classification {
        return connection.updateItem(receiver)
    }

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
