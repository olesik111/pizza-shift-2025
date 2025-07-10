package com.example.pizzaproject

data class Pizza(
    val id: String,
    val name: String,
    val description: String,
    val priceSmall: Int,
    val priceMedium: Int,
    val priceLarge: Int,
    val image: String,
    val toppings: List<Topping> = emptyList()
)