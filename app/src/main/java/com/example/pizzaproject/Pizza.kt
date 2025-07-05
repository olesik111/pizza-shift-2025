package com.example.pizzaproject

data class Pizza(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val image: Int,
    val toppings: List<Topping> = emptyList()
)