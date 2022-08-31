package com.rroperations.models

import jakarta.inject.Singleton

@Singleton
class ReceiversOrder {
     val receivers = mapOf(
        "UPS" to 1,
        "FedEx" to 2,
        "Old Dominion" to 3
    )
}