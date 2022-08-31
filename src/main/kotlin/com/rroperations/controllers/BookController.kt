package com.rroperations.controllers

import com.example.DynamoRepository
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("book")
class BookController(private val dynamoRepository: DynamoRepository) {

    @Get
    fun getBooks(): String {
        if (this.dynamoRepository.existsTable()) {
            return "Existe"
        } else {
            return "no existe"
        }
    }

}