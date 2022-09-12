package com.rroperations.utils

import com.rroperations.models.DestinationsOrder
import com.rroperations.models.ReceiversOrder
import com.rroperations.models.TrainCar
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TrainComparatorTest {

    private val destinationOrder = DestinationsOrder()
    private val receiversOrder = ReceiversOrder()

    private val trainComparator = TrainComparator()
    private val destinationsMap = destinationOrder.destinations
    private val receiversMap = receiversOrder.receivers

    private val dummyTrainOneCar = arrayListOf(TrainCar("Train","Destination","Receiver"))
    private val dummyTrainValid = arrayListOf(
        TrainCar("Box Car 1","LA","Old Dominion"),
        TrainCar("Box Car 2","Houston","UPS"),
        TrainCar("Box Car 3","Chicago","Old Dominion"),
        TrainCar("Box Car 4","Houston","FedEx"),
        TrainCar("Box Car 5","LA","FedEx"),
        TrainCar("Box Car 6","LA","UPS"),
    )

    @Test
    fun testTrainComparator_OneCar() {

        assertEquals(dummyTrainOneCar,trainComparator.sortTrains(dummyTrainOneCar))

    }

    @Test
    fun testTrainComparator_ValidTrain() {
        val sortedValidTrains = trainComparator.sortTrains(dummyTrainValid)

        assertEquals(dummyTrainValid.size, sortedValidTrains.size)

        assertEquals(1, destinationsMap[sortedValidTrains[0].destination])
        assertEquals(destinationsMap.size, destinationsMap[sortedValidTrains[sortedValidTrains.size-1].destination])

        assertEquals(1,receiversMap[sortedValidTrains[0].receiver])
        assertEquals(receiversMap.size, receiversMap[sortedValidTrains[sortedValidTrains.size - 1].receiver])
    }
}