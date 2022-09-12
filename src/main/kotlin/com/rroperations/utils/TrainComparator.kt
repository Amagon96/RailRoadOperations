package com.rroperations.utils

import com.rroperations.models.FindDestination
import com.rroperations.models.FindReceiver
import com.rroperations.models.TrainCar
import com.rroperations.services.ClassificationService
import jakarta.inject.Singleton

@Singleton
class TrainComparator {
    private var destinationsOrder: HashMap<String, Int> = HashMap()
    private var receiversOrder: HashMap<String, Int> = HashMap()
    private val classificationService = ClassificationService()

    private fun prepareClassification() {
        classificationService.getAll(FindReceiver()).forEach { classification ->
            receiversOrder[classification.name!!] = classification.classification!!
        }

        classificationService.getAll(FindDestination()).forEach { destination ->
            destinationsOrder[destination.name!!] = destination.classification!!
        }
    }

    fun sortTrains(train: ArrayList<TrainCar>): List<TrainCar> {
        prepareClassification()

        val destinations = destinationsOrder.keys
        val receivers = receiversOrder.keys

        var (trainExpectedValues, trainUnexpectedValues) =
                train.partition {
                    destinations.contains(it.destination) && receivers.contains(it.receiver)
                }

        val destinationComparator = Comparator { o1: TrainCar, o2: TrainCar ->
            if (!destinations.contains(o1.destination) || !receivers.contains(o1.receiver)) {
                return@Comparator 1
            } else if (!destinations.contains(o2.destination) || !receivers.contains(o2.receiver)) {
                return@Comparator -1
            }

            return@Comparator if (o1.destination == o2.destination)
                    receiversOrder.getValue(o1.receiver) - receiversOrder.getValue(o2.receiver)
            else
                    destinationsOrder.getValue(o1.destination) -
                            destinationsOrder.getValue(o2.destination)
        }

        trainExpectedValues = trainExpectedValues.sortedWith(destinationComparator)

        return trainExpectedValues + trainUnexpectedValues
    }
}
