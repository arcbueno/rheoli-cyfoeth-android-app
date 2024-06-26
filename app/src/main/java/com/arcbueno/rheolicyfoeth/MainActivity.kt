package com.arcbueno.rheolicyfoeth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.arcbueno.rheolicyfoeth.components.CustomAppBar
import com.arcbueno.rheolicyfoeth.pages.createdepartment.CreateDepartmentPage
import com.arcbueno.rheolicyfoeth.pages.createitem.CreateItemPage
import com.arcbueno.rheolicyfoeth.pages.departmentlist.DepartmentPage
import com.arcbueno.rheolicyfoeth.pages.itemlist.ItemPage
import com.arcbueno.rheolicyfoeth.pages.departmentdetail.DepartmentDetailPage
import com.arcbueno.rheolicyfoeth.pages.home.HomePage
import com.arcbueno.rheolicyfoeth.pages.itemdetail.ItemDetailPage
import com.arcbueno.rheolicyfoeth.pages.settings.SettingsPage


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Config.startConfig(this@MainActivity.baseContext)
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
    navController.let {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val route = navBackStackEntry?.destination?.route
    }

    Scaffold(
        bottomBar = { AppBottomNavigation(navController = navController) },
        modifier = Modifier.padding(horizontal = 12.dp), topBar = {
            CustomAppBar(title = stringResource(id = R.string.inventory))
        }
    ) {
        NavigationGraph(navController = navController)
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomePage(navController)
        }
        composable(BottomNavItem.Items.route) {
            ItemPage(navController)
        }
        composable(BottomNavItem.Departments.route) {
            DepartmentPage(navController)
        }
        composable(BottomNavItem.Settings.route) {
            SettingsPage(navController)
        }
        composable(Routes.createItem) {
            CreateItemPage(navController)
        }
        composable(Routes.createDepartment) {
            CreateDepartmentPage(navController)
        }
        composable(
            "${Routes.itemDetails}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: 0
            ItemDetailPage(navController, id)
        }
        composable(
            "${Routes.departmentDetails}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: 0
            DepartmentDetailPage(navController, id)
        }
        composable(
            "${Routes.updateItem}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: 0
            CreateItemPage(navController, itemId = id)
        }
        composable(
            "${Routes.updateDepartment}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: 0
            CreateDepartmentPage(navController, departmentId = id)
        }
    }
}

@Composable
fun AppBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Items,
        BottomNavItem.Departments,
        BottomNavItem.Settings,
    )
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        elevation = 20.dp
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