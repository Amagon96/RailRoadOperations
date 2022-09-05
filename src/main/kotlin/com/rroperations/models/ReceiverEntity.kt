package com.rroperations.models

import io.micronaut.core.annotation.Introspected
import java.util.UUID

@Introspected
class ReceiverEntity
    (
     var name: String,
     var classification: Int,
     var id: UUID?

){
     var type: String = "RECEIVER"

    override fun toString(): String {
        return "Receiver(name='$name', classification='$classification')"
    }
}