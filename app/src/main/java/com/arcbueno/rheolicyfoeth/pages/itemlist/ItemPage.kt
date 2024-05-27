package com.arcbueno.rheolicyfoeth.pages.itemlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.pages.departmentlist.DepartmentListViewModel
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemRepository
import org.koin.compose.koinInject


@Composable
fun ItemPage(
    navHostController: NavHostController,
    itemListViewModel: ItemListViewModel = koinInject()
) {
    val itemList = itemListViewModel.getAllItems()
    Column {
        Text(
            stringResource(id = R.string.all_items),
            modifier = Modifier.padding(12.dp),
            fontSize = 24.sp
        )
        LazyColumn() {
            items(itemList) {
                val department = itemListViewModel.getDepartmentById(it.departmentId)
                Column(modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)) {
                    Text(it.name, fontSize = 18.sp)
                    if (it.description != null)
                        Text(
                            it.description,
                            Modifier.padding(start = 12.dp),
                            color = Color.LightGray
                        )
                    if (department != null)
                        Row {
                            Text(
                                text = "${stringResource(id = R.string.department)}: ",
                                Modifier.padding(start = 12.dp)
                            )
                            Text(text = department.name)

                        }
                }
                Divider()
            }
        }
    }
}
