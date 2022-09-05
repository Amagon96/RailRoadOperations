package com.rroperations.models

import io.micronaut.core.annotation.Introspected
import java.util.UUID

@Introspected
class ReceiverEntity
    (
    override var name: String,
    override var classification: Int,
    override var id: UUID?

): Classification{
    override var type: String = "RECEIVER"

    override fun toString(): String {
        return "Receiver(name='$name', classification='$classification')"
    }
}