package com.arcbueno.rheolicyfoeth.pages.departmentdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.Routes
import com.arcbueno.rheolicyfoeth.components.ItemListItem
import com.arcbueno.rheolicyfoeth.components.ItemMovingListItem
import com.arcbueno.rheolicyfoeth.pages.itemdetail.ItemDetailViewModel
import org.koin.compose.koinInject

@Composable
fun DepartmentDetailPage(
    navHostController: NavHostController, departmentId: Int,
    viewModel: DepartmentDetailViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()
    viewModel.setInitialData(departmentId)

    Scaffold(
        modifier = Modifier.padding(bottom = 64.dp),

        ) {
        Column(modifier = Modifier.padding(it)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { navHostController.popBackStack() },
                    content = { Icon(Icons.Default.ArrowBackIosNew, "") })
                Text(
                    state.department?.name ?: "",
                    modifier = Modifier.padding(12.dp),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    navHostController.navigate(
                        "${Routes.updateDepartment}/${state.department!!.id}",
                    )
                }) {
                    Icon(Icons.Default.Settings, "")
                }
            }


            Column(modifier = Modifier.padding(12.dp)) {
                Box(
                    modifier = Modifier
                        .background(shape = RoundedCornerShape(12.dp), color = Color.LightGray)
                        .heightIn(min = 96.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        state.department?.description
                            ?: stringResource(id = R.string.no_description),
                        modifier = Modifier.padding(12.dp)
                    )
                }
                Text(
                    text = stringResource(id = R.string.all_items),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 24.dp, bottom = 12.dp),
                )
                if (state.itemList.isEmpty())
                    Text(stringResource(id = R.string.empty_department))
                else
                    LazyColumn() {
                        items(state.itemList) {
                            ItemListItem(item = it, department = state.department) {

                            }
                        }
                    }
            }

        }
    }
}