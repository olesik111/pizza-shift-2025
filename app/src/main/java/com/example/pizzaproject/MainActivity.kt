// MainActivity.kt
package com.example.pizzaproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pizzaproject.ui.theme.PizzaProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzaProjectTheme {
                PizzaApp()
            }
        }
    }
}

@Composable
fun PizzaApp() {
    val navController = rememberNavController()

    MaterialTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Main.route,
                modifier = Modifier.padding(paddingValues),
            ) {
                composable(Screen.Main.route) {
                    PizzaListScreen(
                        modifier = Modifier.fillMaxSize(),
                        onPizzaClick = { pizza ->
                            navController.navigate(Screen.PizzaDetail.createRoute(pizza.id))
                        }
                    )
                }
                composable(Screen.Profile.route) { ProfileScreen() }
                composable(Screen.Orders.route) { OrdersScreen() }
                composable(Screen.Cart.route) { CartScreen() }
                composable(
                    route = Screen.PizzaDetail.route,
                    arguments = listOf(
                        navArgument("pizzaId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val pizzaId = backStackEntry.arguments?.getInt("pizzaId")
                    PizzaDetailScreen(navController, pizzaId)
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Text("Профиль")
}

@Composable
fun OrdersScreen() {
    Text("Заказы")
}

@Composable
fun CartScreen() {
    Text("Корзина")
}



@Preview(showBackground = true)
@Composable
fun PizzaAppPreview() {
    PizzaProjectTheme {
        PizzaApp()
    }
}