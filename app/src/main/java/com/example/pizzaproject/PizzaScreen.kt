package com.example.pizzaproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PizzaListScreen(
    modifier: Modifier = Modifier,
    onPizzaClick: (Pizza) -> Unit
) {
    val pizzaList = PizzaVariants.getPizza()

    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(pizzaList) { pizza ->
            PizzaCard(
                pizza = pizza,
                onClick = { onPizzaClick(pizza) }
            )
        }
    }
}