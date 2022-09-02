package com.rroperations.utils

import com.rroperations.models.TrainCar
import com.rroperations.models.DestinationsOrder
import com.rroperations.models.ReceiversOrder
import jakarta.inject.Singleton

@Singleton
class TrainComparator(

    private val destinationsOrder : DestinationsOrder,
    private val receiversOrder: ReceiversOrder) {

    fun sortTrains(train: ArrayList<TrainCar>): List<TrainCar> {

        val destinations = destinationsOrder.destinations
        val receivers = receiversOrder.receivers

        var (trainExpectedValues, trainUnexpectedValues) = train.partition {
            destinations.containsKey(it.destination) && receivers.containsKey(it.receiver)
        }

        val destinationComparator = Comparator { o1: TrainCar, o2: TrainCar ->

            if (!destinations.containsKey(o1.destination) || !receivers.containsKey(o1.receiver)){
                return@Comparator 1
            } else if (!destinations.containsKey(o2.destination) || !receivers.containsKey(o2.receiver)){
                return@Comparator -1
            }

            return@Comparator if (o1.destination == o2.destination) receivers[o1.receiver]!! - receivers[o2.receiver]!!
            else destinations[o1.destination]!! - destinations[o2.destination]!!
        }

        trainExpectedValues = trainExpectedValues.sortedWith(destinationComparator)

        return trainExpectedValues + trainUnexpectedValues
    }
}