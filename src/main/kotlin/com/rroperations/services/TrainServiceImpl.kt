package com.rroperations.services

import com.rroperations.models.ClassificationTrack
import com.rroperations.models.Train
import com.rroperations.utils.TrainComparator
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class TrainServiceImpl: TrainServiceInterface {

    @Inject
    lateinit var comparator: TrainComparator

    override fun orderTrains(trains: ArrayList<Train>): List<ClassificationTrack> {
        val response: ArrayList<ClassificationTrack> = ArrayList()
        val trainsSorted = comparator.sortTrains(trains)
        trainsSorted.forEachIndexed{index, train ->
            val classificationTrack = ClassificationTrack(index + 1, train.nameOfCar, train.destination, train.receiver)
            response.add(classificationTrack)
        }
        println(response)
        println(trainsSorted)
        return response
    }

}