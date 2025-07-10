package com.example.pizzaproject

sealed interface PizzaDetailsState {
    data object Loading: PizzaDetailsState
    data object Error: PizzaDetailsState
    data class Content(val pizza: Pizza): PizzaDetailsState
}