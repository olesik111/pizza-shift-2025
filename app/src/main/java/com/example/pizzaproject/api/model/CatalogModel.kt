package com.example.pizza_shift_intensive.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CatalogModel(
    val success: Boolean,
    val reason: String? = null,
    val catalog: List<PizzaApiModel>
)