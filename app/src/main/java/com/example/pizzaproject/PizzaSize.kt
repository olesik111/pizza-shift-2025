package com.example.pizzaproject

import androidx.annotation.StringRes
import com.example.pizzaproject.R

enum class PizzaSize(@StringRes val displayNameRes: Int) {
    SMALL(R.string.pizza_size_small),
    MEDIUM(R.string.pizza_size_medium),
    LARGE(R.string.pizza_size_large)
}