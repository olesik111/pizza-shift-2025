package com.example.pizzaproject.api.mappers

import com.example.pizza_shift_intensive.data.api.model.PartApi
import com.example.pizzaproject.Topping

fun PartApi.toTopping(): Topping {
    return Topping(
        name = formatToRusString(this.type),
        price = this.price,
        image = formatImageUrl(this.img)
    )
}