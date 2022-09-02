package com.rroperations

import com.fasterxml.jackson.databind.ObjectMapper
import com.rroperations.models.ClassificationTrack
import com.rroperations.models.TrainCar
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test


@MicronautTest
class TrainController {

    @Inject
    @field:Client("/")
    lateinit var client : HttpClient

    var mapper = ObjectMapper()

    private val train: ArrayList<TrainCar> = arrayListOf(
        TrainCar("Box 1", "Houston", "UPS"),
        TrainCar("Box 2", "LA", "UPS"),
        TrainCar("Box 3", "Houston", "FedEx"),
        TrainCar("Box 4", "Chicago", "Old Dominion"),
        TrainCar("Box 5", "Chicago", "UPS")
    )

    private val classificationTracks: ArrayList<ClassificationTrack> = arrayListOf(
        ClassificationTrack("1","Box 1", "Houston", "UPS"),
        ClassificationTrack("1", "Box 3", "Houston", "FedEx"),
        ClassificationTrack("2", "Box 5", "Chicago", "UPS"),
        ClassificationTrack("2", "Box 4", "Chicago", "Old Dominion"),
        ClassificationTrack("3", "Box 2", "LA", "UPS")
    )

    @Test
    fun testPostTrains_ValidRequest() {
        val request: HttpRequest<Any> = HttpRequest.POST("trains/railroadoperations", train)
        val body = client.toBlocking().retrieve(request)
        val serialized = mapper.writeValueAsString(classificationTracks)
        assertNotNull(body)
        assertEquals(serialized, body)
    }

    @Test
    fun testPostTrains_NoBody() {
        val request: HttpRequest<Any> = HttpRequest.POST("trains/railroadoperations",
            emptyList<ClassificationTrack>())
        val body = client.toBlocking().retrieve(request)
        val serialized = mapper.writeValueAsString(emptyList<ClassificationTrack>())
        assertNotNull(body)
        assertEquals(serialized, body)
    }
}