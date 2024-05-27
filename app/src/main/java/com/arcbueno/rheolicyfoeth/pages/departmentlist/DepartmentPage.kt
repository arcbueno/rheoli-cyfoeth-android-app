package com.arcbueno.rheolicyfoeth.pages.departmentlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.pages.createitem.CreateItemViewModel
import org.koin.compose.koinInject


@Composable
fun DepartmentPage(
    navHostController: NavHostController,
    departmentListViewModel: DepartmentListViewModel = koinInject()
) {
    val departmentList = departmentListViewModel.getAll()
    Column {
        Text(
            stringResource(id = R.string.all_departments),
            modifier = Modifier.padding(12.dp),
            fontSize = 24.sp
        )
        LazyColumn() {
            items(departmentList) {
                Column(modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)) {
                    Text(it.name)
                    if (it.description != null)
                        Text(it.description)
                    Divider()
                }
            }
        }
    }
}