package com.rroperations.services

import com.rroperations.models.*
import com.rroperations.utils.TrainComparator
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class TrainServiceImpl: TrainServiceInterface {

    private var destinationsOrder: HashMap<String, Int> = HashMap()
    private var receiversOrder: HashMap<String, Int> = HashMap()
    private val classificationService = ClassificationService()

    @Inject
    lateinit var comparator: TrainComparator

    override fun orderTrains(train: ArrayList<TrainCar>): List<TrainCar> {
        prepareClassification()

        val destinations = destinationsOrder.keys
        val receivers = receiversOrder.keys

        val response: ArrayList<TrainCar> = ArrayList()

        val trainsSorted = comparator.sortTrains(train)
        trainsSorted.map{ trainCar ->
            val classificationTrack = TrainCar(
                trainCar.name,
                trainCar.destination,
                trainCar.receiver,
                if (destinations.contains(trainCar.destination) && receivers.contains(trainCar.receiver))
                    destinationsOrder.getValue(trainCar.destination).toString() else "DLQ")
            response.add(classificationTrack)
        }

        return response
    }

    private fun prepareClassification() {
        classificationService.getAll(FindReceiver()).forEach { classification ->
            receiversOrder[classification.name!!] = classification.classification!!
        }

        classificationService.getAll(FindDestination()).forEach { destination ->
            destinationsOrder[destination.name!!] = destination.classification!!
        }
    }

}