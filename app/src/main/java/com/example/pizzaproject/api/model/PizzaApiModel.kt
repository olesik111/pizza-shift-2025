package com.example.pizza_shift_intensive.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class PizzaApiModel(
    val id: String,
    val name: String,
    val ingredients: List<PartApi>,
    val toppings: List<PartApi>,
    val description: String,
    val sizes: List<KindApi>,
    val doughs: List<KindApi>,
    val calories: Int,
    val protein: String,
    val totalFat: String,
    val carbohydrates: String,
    val sodium: String,
    val allergens: List<String>,
    val isVegetarian: Boolean,
    val isGlutenFree: Boolean,
    val isNew: Boolean,
    val isHit: Boolean,
    val img: String
)