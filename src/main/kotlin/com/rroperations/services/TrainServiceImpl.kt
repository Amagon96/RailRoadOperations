package com.rroperations.services

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

    override fun orderTrains(train: ArrayList<TrainCar>): List<TrainCar> {

        val destinations = destinationsOrder.destinations
        val receivers = receiversOrder.receivers

        val response: ArrayList<TrainCar> = ArrayList()

        val trainsSorted = comparator.sortTrains(train)
        trainsSorted.map{ trainCar ->
            val classificationTrack = TrainCar(
                trainCar.name,
                trainCar.destination,
                trainCar.receiver,
                if (destinations.containsKey(trainCar.destination) && receivers.containsKey(trainCar.receiver))
                    destinations[trainCar.destination].toString() else "DLQ")
            response.add(classificationTrack)
        }

        return response
    }

}