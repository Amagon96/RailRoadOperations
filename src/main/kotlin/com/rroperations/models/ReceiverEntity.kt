package com.rroperations.models

import io.micronaut.core.annotation.Introspected

@Introspected
class ReceiverEntity
    (
    var name: String,
    var classification: Int,

){
    val type: String = "RECEIVER"

    override fun toString(): String {
        return "Receiver(name='$name', classification='$classification')"
    }
}