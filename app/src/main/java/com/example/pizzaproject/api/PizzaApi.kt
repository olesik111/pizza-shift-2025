package com.example.pizzaproject.api

import com.example.pizza_shift_intensive.data.api.model.CatalogModel
import retrofit2.http.GET


interface PizzaApi {
    @GET("catalog")
    suspend fun getPizzas(): CatalogModel
}