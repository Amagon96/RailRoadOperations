package com.rroperations.models

data class FindReceiver(override val id: String? = null) : FindClassification() {
    override val type = "RECEIVER"
}

