package com.rroperations.models

data class TrainCar(
        val nameOfCar: String,
        val destination: String,
        val receiver: String,
        var classificationTrack: String? = null)
