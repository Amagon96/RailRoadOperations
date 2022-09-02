package com.rroperations.models

data class TrainCar(
    val name: String,
    val destination: String,
    val receiver: String) {

    override fun toString(): String {
        return "Train(nameOfCar='$name', destination='$destination', receiver='$receiver')"
    }
}