package com.example.railRoadOperations.models

data class ClassificationTrack(
        val classificationTrack: String,
        val nameOfCar: String,
        val destination: String,
        val receiver: String) {

    override fun toString(): String {
        return "ClassificationTrack(classificationTrack=$classificationTrack, nameOfCar='$nameOfCar', destination='$destination', receiver='$receiver')"
    }
}