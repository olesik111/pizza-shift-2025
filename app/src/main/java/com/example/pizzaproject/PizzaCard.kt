package com.example.pizzaproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerInputModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PizzaCard (
    pizza: Pizza,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
        Row {
            Image(
                painter = painterResource(id = pizza.image),
                contentDescription = pizza.name,
                modifier = Modifier.size(170.dp)
            )
            Column {
                Spacer(modifier = Modifier.height(13.dp))
                Text(
                    text = pizza.name,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,

                    )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = pizza.description,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = "от ${pizza.price} ₽",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

/*@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier){
        paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            val pizzaList = PizzaVariants.getPizza()
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(pizzaList) { pizza ->
                    PizzaCard(
                        pizza = pizza,
                        onClick = {}
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun MyApp() {
    MainScreen()
}*/