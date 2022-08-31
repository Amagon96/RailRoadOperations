package com.rroperations.models

import jakarta.inject.Singleton

@Singleton
class DestinationsOrder {
        val destinations = mapOf(
        "Houston" to 1,
        "Chicago" to 2,
        "LA" to 3
    )
}