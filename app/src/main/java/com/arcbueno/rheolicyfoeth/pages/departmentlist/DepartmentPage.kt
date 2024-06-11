package com.arcbueno.rheolicyfoeth.pages.departmentlist

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.Routes
import com.arcbueno.rheolicyfoeth.components.CustomAppBar
import com.arcbueno.rheolicyfoeth.components.DepartmentListItem
import com.arcbueno.rheolicyfoeth.pages.createitem.CreateItemViewModel
import org.koin.compose.koinInject


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DepartmentPage(
    navHostController: NavHostController,
    departmentListViewModel: DepartmentListViewModel = koinInject()
) {
    val state by departmentListViewModel.state.collectAsState()
    departmentListViewModel.getAll()

    Scaffold(modifier = Modifier.padding(bottom = 64.dp), floatingActionButton = {
        AnimatedVisibility(
            visible = true,
            enter = slideInHorizontally(initialOffsetX = { it * 2 }),
            exit = slideOutHorizontally(targetOffsetX = { it * 2 }),
        ) {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Routes.createDepartment)

                },
            ) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.add_new_item))
            }
        }
    }, content = {
        Column {
            Text(
                stringResource(id = R.string.all_departments),
                modifier = Modifier.padding(12.dp),
                fontSize = 24.sp
            )
            LazyColumn() {
                items(state.departmentList) {
                    DepartmentListItem(department = it, onTap = {
                        navHostController.navigate(
                            "${Routes.departmentDetails}/${it.id}",
                        )
                    })
                }
            }
        }
    })


}