package com.example.pizzaproject

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Main : Screen("main", "Главная", Icons.Default.Home)
    object Orders : Screen("orders", "Заказы", Icons.Default.List)
    object Cart : Screen("cart", "Корзина", Icons.Default.ShoppingCart)
    object Profile : Screen("profile", "Профиль", Icons.Default.Person)
    object PizzaDetail : Screen("pizza_detail/{pizzaId}", "Детали пиццы", Icons.Default.Home) {
        fun createRoute(pizzaId: Int) = "pizza_detail/$pizzaId"
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val screens = listOf(
        Screen.Main,
        Screen.Profile,
        Screen.Orders,
        Screen.Cart
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}