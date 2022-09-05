package com.rroperations.models

import io.micronaut.core.annotation.Introspected

@Introspected
class DestinationEntity(
    var name: String,
    var classification: Int,
){
    val type: String = "DESTINATION"

    override fun toString(): String {
        return "Destination(name='$name', classification='$classification')"
    }
}