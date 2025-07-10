package com.example.pizzaproject

import androidx.compose.foundation.background
import androidx.compose.ui.res.stringResource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.pizzaproject.api.NetworkModule.pizzaApi
import com.example.pizzaproject.api.NetworkModule.pizzaConverter

@Composable
fun PizzaDetailScreen(
    navController: NavController,
    isDarkTheme: Boolean,
    pizzaId: String?
) {
    var state: PizzaDetailsState by remember { mutableStateOf(PizzaDetailsState.Loading) }

    LaunchedEffect(key1 = Unit) {
        state = PizzaDetailsState.Loading
        try {
            if (pizzaId != null) {
                val pizza = getPizza(pizzaId)
                state = PizzaDetailsState.Content(pizza)
            } else {
                state = PizzaDetailsState.Error
            }
        } catch (e: Exception) {
            state = PizzaDetailsState.Error
        }

    }
    when (val currentState = state) {
        is PizzaDetailsState.Loading -> FullScreenProgressIndicator()
        is PizzaDetailsState.Error -> ErrorIndicator()
        is PizzaDetailsState.Content -> PizzaDetailsContent(
            currentState.pizza,
            navController,
            isDarkTheme
        )
    }

}

@Composable
private fun PizzaDetailsContent(pizza: Pizza, navController: NavController, isDarkTheme: Boolean) {
    var selectedToppings by remember { mutableStateOf<List<Topping>>(emptyList()) }
    val availableToppings = remember { pizza.toppings }
    var selectedSize by remember { mutableStateOf(PizzaSize.MEDIUM) }

    val totalPrice = calculateTotalPrice(pizza, selectedSize, selectedToppings)

    Scaffold { paddingValues ->
        PizzaDetail(
            paddingValues = paddingValues,
            pizza = pizza,
            isDarkTheme = isDarkTheme,
            selectedSize = selectedSize,
            onSizeSelected = { selectedSize = it },
            availableToppings = availableToppings,
            selectedToppings = selectedToppings,
            onToppingSelected = { topping ->
                selectedToppings = if (selectedToppings.contains(topping)) {
                    selectedToppings - topping
                } else {
                    selectedToppings + topping
                }
            },
            totalPrice = totalPrice,
            onBackClick = { navController.popBackStack() }
        )
    }
}


@Composable
private fun PizzaDetail(
    paddingValues: PaddingValues,
    pizza: Pizza,
    isDarkTheme: Boolean,
    selectedSize: PizzaSize,
    onSizeSelected: (PizzaSize) -> Unit,
    availableToppings: List<Topping>,
    selectedToppings: List<Topping>,
    onToppingSelected: (Topping) -> Unit,
    totalPrice: Int,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PizzaHeader(pizza.name, onBackClick)
        ContextImage(pizza.image, 300.dp)
        PizzaTitle(pizza.name, pizza.description)
        SizeSelector(
            pizza = pizza,
            selectedSize = selectedSize,
            onSizeSelected = onSizeSelected,
            isDarkTheme = isDarkTheme
        )
        ToppingsSelector(
            availableToppings = availableToppings,
            selectedToppings = selectedToppings,
            onToppingSelected = onToppingSelected,
            isDarkTheme = isDarkTheme
        )
        TotalPriceAndButton(totalPrice)
    }
}

@Composable
private fun PizzaHeader(name: String, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад"
            )
        }
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
private fun PizzaTitle(name: String, description: String) {
    Text(
        text = name,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineLarge
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = description,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        style = MaterialTheme.typography.bodyLarge
    )

    Spacer(modifier = Modifier.height(22.dp))
}

@Composable
private fun SizeSelector(
    pizza: Pizza,
    selectedSize: PizzaSize,
    onSizeSelected: (PizzaSize) -> Unit,
    isDarkTheme: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                if (isDarkTheme) Color(0xFF1E1E1E) else Color.LightGray,
                RoundedCornerShape(8.dp)
            )
    ) {
        PizzaSize.entries.forEach { size ->
            SizeOption(
                size = size,
                price = when (size) {
                    PizzaSize.SMALL -> pizza.priceSmall
                    PizzaSize.MEDIUM -> pizza.priceMedium
                    PizzaSize.LARGE -> pizza.priceLarge
                },
                isSelected = size == selectedSize,
                isDarkTheme = isDarkTheme,
                onClick = { onSizeSelected(size) }
            )
        }
    }
    Spacer(modifier = Modifier.height(33.dp))
}

@Composable
private fun SizeOption(
    size: PizzaSize,
    price: Int,
    isSelected: Boolean,
    isDarkTheme: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .clickable(onClick = onClick)
            .background(
                if (isSelected) {
                    if (isDarkTheme) Color(0xFF333333) else Color.White
                } else {
                    Color.Transparent
                },
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                stringResource(size.displayNameRes),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = if (isDarkTheme) Color.White else Color.Black
            )
            Text(
                "$price ₽",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (isDarkTheme) Color.LightGray else Color.DarkGray
            )
        }
    }
}

@Composable
private fun ToppingsSelector(
    availableToppings: List<Topping>,
    selectedToppings: List<Topping>,
    onToppingSelected: (Topping) -> Unit,
    isDarkTheme: Boolean
) {
    Text(
        text = "Добавить",
        fontSize = 28.sp,
        fontWeight = FontWeight.Medium
    )

    GridToppings(
        toppings = availableToppings,
        selectedToppings = selectedToppings,
        onToppingSelected = onToppingSelected,
        isDarkTheme = isDarkTheme
    )

    Spacer(modifier = Modifier.height(32.dp))
}

@Composable
private fun TotalPriceAndButton(totalPrice: Int) {
    Text(
        text = "Итого: $totalPrice",
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

private fun calculateTotalPrice(pizza: Pizza, size: PizzaSize, toppings: List<Topping>): Int {
    val basePrice = when (size) {
        PizzaSize.SMALL -> pizza.priceSmall
        PizzaSize.MEDIUM -> pizza.priceMedium
        PizzaSize.LARGE -> pizza.priceLarge
    }
    return basePrice + toppings.sumOf { it.price }
}

@Composable
fun GridToppings(
    toppings: List<Topping>,
    selectedToppings: List<Topping>,
    onToppingSelected: (Topping) -> Unit,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
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
                        isDarkTheme = isDarkTheme,
                        modifier = Modifier
                            .height(180.dp)
                            .weight(1f)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

private suspend fun getPizza(pizzaId: String): Pizza {
    val pizzaItems = pizzaApi.getPizzas()
    return pizzaConverter.convert(pizzaItems).first { it.id == pizzaId }
}