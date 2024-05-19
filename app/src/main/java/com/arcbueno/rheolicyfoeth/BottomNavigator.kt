package com.arcbueno.rheolicyfoeth

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Items : BottomNavItem("items", Icons.Default.Menu, "Items")
    object Departments : BottomNavItem("departments", Icons.Default.Home, "Departments")
//    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}