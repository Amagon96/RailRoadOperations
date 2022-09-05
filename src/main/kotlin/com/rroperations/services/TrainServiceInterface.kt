package com.rroperations.services

import com.rroperations.models.TrainCar

interface TrainServiceInterface {
    fun orderTrains(train: ArrayList<TrainCar>): List<TrainCar>
}