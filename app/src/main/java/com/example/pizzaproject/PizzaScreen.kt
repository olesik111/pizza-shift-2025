package com.example.pizzaproject

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pizzaproject.api.NetworkModule
import com.example.pizzaproject.api.NetworkModule.pizzaApi
import com.example.pizzaproject.api.NetworkModule.pizzaConverter

@Composable
fun PizzaListScreen(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onThemeChange: () -> Unit,
    onPizzaClick: (Pizza) -> Unit
) {
    var state: PizzaListState by remember { mutableStateOf(PizzaListState.Loading) }

    LaunchedEffect(key1 = Unit) {
        state = PizzaListState.Loading
        try {
            val pizzaList = getPizzas()
            state = PizzaListState.Content(pizzaList)
        } catch (e: Exception) {
            Log.e("PizzaListScreen", "Error loading pizzas", e)
            state = PizzaListState.Error
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Главная",
                style = MaterialTheme.typography.headlineMedium
            )

            IconButton(onClick = onThemeChange) {
                Icon(
                    painter = painterResource(
                        if (isDarkTheme) R.drawable.ic_light_mode
                        else R.drawable.ic_dark_mode
                    ),
                    contentDescription = "Сменить тему"
                )
            }
        }

        when (val currentState = state) {
            PizzaListState.Loading -> FullScreenProgressIndicator()
            PizzaListState.Error -> ErrorIndicator()
            is PizzaListState.Content -> PizzaListContent(
                modifier,
                onPizzaClick,
                pizzaList = currentState.pizzas
            )
        }


    }
}

@Composable
private fun PizzaListContent(
    modifier: Modifier,
    onPizzaClick: (Pizza) -> Unit,
    pizzaList: List<Pizza>
) {
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

private suspend fun getPizzas(): List<Pizza> {
    val pizzaItems = pizzaApi.getPizzas()
    return pizzaConverter.convert(pizzaItems)
}