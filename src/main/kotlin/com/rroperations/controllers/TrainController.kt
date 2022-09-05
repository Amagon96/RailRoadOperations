package com.rroperations.controllers

import com.rroperations.models.TrainCar
import com.rroperations.services.TrainServiceImpl
import io.micronaut.http.annotation.*
import jakarta.inject.Inject

@Controller("/trains")
class TrainController {

    @Inject
    lateinit var trainServiceImpl: TrainServiceImpl

    @Post("/railroadoperations")
    fun postTrains(@Body train: ArrayList<TrainCar>): List<TrainCar> {
        return trainServiceImpl.orderTrains(train)
    }
}