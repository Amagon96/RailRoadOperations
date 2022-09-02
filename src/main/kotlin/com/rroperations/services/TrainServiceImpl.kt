package com.rroperations.services

import com.rroperations.models.ClassificationTrack
import com.rroperations.models.TrainCar
import com.rroperations.utils.TrainComparator
import com.rroperations.models.DestinationsOrder
import com.rroperations.models.ReceiversOrder
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class TrainServiceImpl(
    private val destinationsOrder: DestinationsOrder,
    private val receiversOrder: ReceiversOrder): TrainServiceInterface {

    @Inject
    lateinit var comparator: TrainComparator

    override fun orderTrains(train: ArrayList<TrainCar>): List<ClassificationTrack> {

        val destinations = destinationsOrder.destinations
        val receivers = receiversOrder.receivers

        val response: ArrayList<ClassificationTrack> = ArrayList()

        val trainsSorted = comparator.sortTrains(train)
        trainsSorted.map{ trainCar ->
            val classificationTrack = ClassificationTrack(
                if (destinations.containsKey(trainCar.destination) && receivers.containsKey(trainCar.receiver))
                destinations[trainCar.destination].toString() else "DLQ",
                trainCar.name,
                trainCar.destination,
                trainCar.receiver)
            response.add(classificationTrack)
        }
        println(response)
        println(trainsSorted)

        return response
    }

}