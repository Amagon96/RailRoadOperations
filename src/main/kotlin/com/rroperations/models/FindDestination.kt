package com.rroperations.models

data class FindDestination(override val id: String? = null) : FindClassification() {
    override val type = "DESTINATION"
}

