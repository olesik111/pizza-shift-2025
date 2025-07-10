package com.example.pizzaproject

sealed interface PizzaListState {
    data object Loading: PizzaListState
    data object Error: PizzaListState
    data class Content(val pizzas: List<Pizza>): PizzaListState
}