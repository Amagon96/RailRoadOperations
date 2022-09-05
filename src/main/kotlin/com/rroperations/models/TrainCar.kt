package com.rroperations.models

data class TrainCar(
        val name: String,
        val destination: String,
        val receiver: String,
        var classificationTrack: String? = null)
