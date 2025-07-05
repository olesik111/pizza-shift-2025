package com.example.pizzaproject

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.graphics.Color

@Composable
fun PizzaDetailScreen(
    navController: NavController,
    pizzaId: Int?
) {
    val pizza = PizzaVariants.getPizza().find { it.id == pizzaId } ?: PizzaVariants.getPizza().first()

    var selectedToppings by remember { mutableStateOf<List<Topping>>(emptyList()) }
    val availableToppings = remember { pizza.toppings }
    var selectedSize by remember { mutableStateOf(PizzaSize.MEDIUM) }

    val totalPrice by remember {
        derivedStateOf {
            val basePrice = when(selectedSize) {
                PizzaSize.SMALL -> pizza.priceSmall
                PizzaSize.MEDIUM -> pizza.priceMedium
                PizzaSize.LARGE -> pizza.priceLarge
            }
            basePrice + selectedToppings.sumOf { it.price }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = pizza.image),
                contentDescription = pizza.name,
                modifier = Modifier.size(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = pizza.name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = pizza.description,
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
            ) {
                PizzaSize.entries.forEach { size ->
                    val price = when(size) {
                        PizzaSize.SMALL -> pizza.priceSmall
                        PizzaSize.MEDIUM -> pizza.priceMedium
                        PizzaSize.LARGE -> pizza.priceLarge
                    }
                    val isSelected = size == selectedSize

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedSize = size }
                            .background(
                                if (isSelected) Color.White else Color.Transparent,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(size.displayName, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                            Text("$price ₽", fontSize = 12.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(33.dp))

            Text(
                text = "Добавить",
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )

            GridToppings(
                toppings = availableToppings,
                selectedToppings = selectedToppings,
                onToppingSelected = { topping ->
                    selectedToppings = if (selectedToppings.contains(topping)) {
                        selectedToppings - topping
                    } else {
                        selectedToppings + topping
                    }
                }
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Итого: ${totalPrice}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )


            Button(
                onClick = { /* Добавить в корзину */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4511E),
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(8.dp)

            ) {
                Text("Добавить в корзину")
            }
        }
    }
}

@Composable
fun GridToppings(
    toppings: List<Topping>,
    selectedToppings: List<Topping>,
    onToppingSelected: (Topping) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        toppings.chunked(2).forEach { row ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { topping ->
                    ToppingCard(
                        topping = topping,
                        isSelected = selectedToppings.contains(topping),
                        onToppingSelected = onToppingSelected,
                        modifier = Modifier
                            .height(150.dp)
                            .weight(1f)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}