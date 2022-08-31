package com.example

import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import io.micronaut.context.exceptions.ConfigurationException
import jakarta.inject.Singleton
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder
import java.net.URI
import java.net.URISyntaxException


@Requires(property = "dynamodb-local.host")
@Requires(property = "dynamodb-local.port")
@Singleton
internal class DynamoDbClientBuilderListener(
    @Value("\${dynamodb-local.host}") host: String,
    @Value("\${dynamodb-local.port}") port: String
) : BeanCreatedEventListener<DynamoDbClientBuilder> {
    private var endpoint: URI? = null
    private val accessKeyId: String
    private val secretAccessKey: String

    init {
        try {
            endpoint = URI("http://$host:$port")
        } catch (e: URISyntaxException) {
            throw ConfigurationException("dynamodb.endpoint not a valid URI")
        }
        accessKeyId = "fakeMyKeyId"
        secretAccessKey = "fakeSecretAccessKey"
    }

    override fun onCreated(event: BeanCreatedEvent<DynamoDbClientBuilder>): DynamoDbClientBuilder {
        return event.bean.endpointOverride(endpoint)
            .credentialsProvider {
                object : AwsCredentials {
                    override fun accessKeyId(): String {
                        return accessKeyId
                    }

                    override fun secretAccessKey(): String {
                        return secretAccessKey
                    }
                }
            }
    }
}