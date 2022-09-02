package com.rroperations.models

import java.util.UUID

data class Train(
        val nameOfCar: String,
        val destination: String,
        val receiver: String,
        var classificationTrack: String? = null,
        var trainId: UUID? = null) {

    override fun toString(): String {
        return "Train(nameOfCar='$nameOfCar', destination='$destination', receiver='$receiver')"
    }
}