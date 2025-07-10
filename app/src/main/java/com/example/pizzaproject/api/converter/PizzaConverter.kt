package com.example.pizzaproject.api.converter

import com.example.pizza_shift_intensive.data.api.model.CatalogModel
import com.example.pizzaproject.Pizza
import com.example.pizzaproject.api.mappers.formatImageUrl
import com.example.pizzaproject.api.mappers.toTopping

class PizzaCatalogConverter {
    fun convert(model: CatalogModel): List<Pizza> {
        return model.catalog.map { pizzaApiModel ->
            Pizza(
                id = pizzaApiModel.id,
                name = pizzaApiModel.name,
                description = pizzaApiModel.description,
                toppings = pizzaApiModel.toppings.map { it.toTopping() },
                priceSmall = pizzaApiModel.sizes.find{ it.type == "SMALL" }!!.price,
                priceMedium = pizzaApiModel.sizes.find{ it.type == "MEDIUM" }!!.price,
                priceLarge = pizzaApiModel.sizes.find{ it.type == "LARGE" }!!.price,
                image = formatImageUrl(pizzaApiModel.img)
            )
        }
    }
}