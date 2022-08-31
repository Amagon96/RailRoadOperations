package com.example.railRoadOperations.services

import com.example.railRoadOperations.models.ClassificationTrack
import com.example.railRoadOperations.models.Train
import com.example.railRoadOperations.utils.TrainComparator
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

    override fun orderTrains(trains: ArrayList<Train>): List<ClassificationTrack> {

        val destinations = destinationsOrder.destinations
        val receivers = receiversOrder.receivers

        val response: ArrayList<ClassificationTrack> = ArrayList()

        val trainsSorted = comparator.sortTrains(trains)
        trainsSorted.map{ train ->
            val classificationTrack = ClassificationTrack(
                if (destinations.containsKey(train.destination) && receivers.containsKey(train.receiver))
                destinations[train.destination].toString() else "DLQ",
                train.nameOfCar,
                train.destination,
                train.receiver)
            response.add(classificationTrack)
        }
        println(response)
        println(trainsSorted)

        return response
    }

}