package com.example.pizzaproject

object PizzaVariants {
    private val toppings = listOf(
        Topping(1, "Cheese", 50, R.drawable.cheesy),
        Topping(2, "Mozarella", 70, R.drawable.mocarella),
        Topping(3, "Shrimp", 40, R.drawable.shrimp),
    )
    fun getPizza() = listOf(
        Pizza(
            id = 1,
            name = "Pepperoni",
            description = "Peperroni pizza with spicy sausages",
            price = 300,
            image = R.drawable.pepperoni,
            toppings = listOf(toppings[0], toppings[1], toppings[2])
        ),
        Pizza(
            id = 2,
            name = "Cheese pizza",
            description = "Cheese pizza with tomato sauce and cheese",
            price = 300,
            image = R.drawable.margherita,
            toppings = listOf(toppings[0], toppings[1])
        ),
        Pizza(
            id = 3,
            name = "Cheese pizza",
            description = "Cheese pizza with tomato sauce and cheese",
            price = 300,
            image = R.drawable.margherita,
            toppings = listOf(toppings[0], toppings[1])
        ),
        Pizza(
            id = 4,
            name = "Cheese pizza",
            description = "Cheese pizza with tomato sauce and cheese",
            price = 300,
            image = R.drawable.margherita,
            toppings = listOf(toppings[0], toppings[1])
        ),
        Pizza(
            id = 5,
            name = "Cheese pizza",
            description = "Cheese pizza with tomato sauce and cheese",
            price = 300,
            image = R.drawable.margherita,
            toppings = listOf(toppings[0], toppings[1])
        )

    )
    fun getAllToppings() = toppings
}