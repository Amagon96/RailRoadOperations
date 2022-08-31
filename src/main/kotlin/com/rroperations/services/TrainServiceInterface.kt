package com.rroperations.services

import com.rroperations.models.ClassificationTrack
import com.rroperations.models.Train

interface TrainServiceInterface {
    fun orderTrains(trains: ArrayList<Train>): List<ClassificationTrack>
}