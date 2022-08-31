package com.example.railRoadOperations.models

data class Train(
        val nameOfCar: String,
        val destination: String,
        val receiver: String) {

    override fun toString(): String {
        return "Train(nameOfCar='$nameOfCar', destination='$destination', receiver='$receiver')"
    }
}