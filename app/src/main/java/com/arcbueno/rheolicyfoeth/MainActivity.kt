package com.arcbueno.rheolicyfoeth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.arcbueno.rheolicyfoeth.ui.theme.RheoliCyfoethTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import com.arcbueno.rheolicyfoeth.pages.createitem.CreateItemPage
import com.arcbueno.rheolicyfoeth.pages.DepartmentPage
import com.arcbueno.rheolicyfoeth.pages.ItemPage


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RheoliCyfoethTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MainPage()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    RheoliCyfoethTheme {
        MainPage()
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainPage(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val fabIsVisible = rememberSaveable { mutableStateOf(true) }
    navController.let {
        val navBackStackEntry by it.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        fabIsVisible.value = currentRoute == BottomNavItem.Items.route
    }

    Scaffold(
        bottomBar = { AppBottomNavigation(navController = navController, fabIsVisible) },
        floatingActionButton = {
            AnimatedVisibility(
                visible = fabIsVisible.value,
                enter = slideInHorizontally(initialOffsetX = { it * 2 }),
                exit = slideOutHorizontally(targetOffsetX = { it * 2 }),
            ) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Routes.createItem)
                    },
                ) {
                    Icon(Icons.Filled.Add, stringResource(id = R.string.add_new_item))
                }
            }
        }
    ) {
        NavigationGraph(navController = navController)
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(navController, startDestination = BottomNavItem.Items.route) {

        composable(BottomNavItem.Items.route) {
            ItemPage()
        }
        composable(BottomNavItem.Departments.route) {
            DepartmentPage()
        }
        composable(Routes.createItem) {
            CreateItemPage(navController)
        }
    }
}

@Composable
fun AppBottomNavigation(navController: NavController, fabIsVisible: MutableState<Boolean>) {
    val items = listOf(
        BottomNavItem.Items,
        BottomNavItem.Departments,
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.teal_200),
        contentColor = Color.Black
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}