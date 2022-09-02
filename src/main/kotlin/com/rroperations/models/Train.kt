package com.rroperations.models

import java.util.UUID

data class TrainCar(
        val nameOfCar: String,
        val destination: String,
        val receiver: String,
        var classificationTrack: String? = null,
        var trainId: UUID? = null)
