// MainActivity.kt
package com.example.pizzaproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pizzaproject.ui.theme.PizzaProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzaProjectTheme {
                PizzaListScreen(onPizzaClick = {})
            }
        }
    }
}

@Composable
fun PizzaListScreen(onPizzaClick: (Pizza) -> Unit) {
    val pizzaList = PizzaVariants.getPizza()

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pizzaList) { pizza ->
            PizzaCard(
                pizza = pizza,
                onClick = { onPizzaClick(pizza) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PizzaAppPreview() {
    PizzaProjectTheme {
        PizzaListScreen(onPizzaClick = {})
    }
}