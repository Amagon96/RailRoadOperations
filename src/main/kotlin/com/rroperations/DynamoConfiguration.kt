package com.example

import io.micronaut.context.annotation.Requires
import javax.validation.constraints.NotBlank
import io.micronaut.context.annotation.ConfigurationProperties

@Requires(property = "dynamodb.table-name")
@ConfigurationProperties("dynamodb")
interface DynamoConfiguration {
    @get:NotBlank
    val tableName: String
}
