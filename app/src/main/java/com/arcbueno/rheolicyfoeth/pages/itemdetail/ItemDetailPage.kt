package com.arcbueno.rheolicyfoeth.pages.itemdetail

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.Routes
import com.arcbueno.rheolicyfoeth.components.CustomFormField
import com.arcbueno.rheolicyfoeth.components.DateListItem
import com.arcbueno.rheolicyfoeth.components.ItemMovingListItem
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.ItemMoving
import org.koin.compose.koinInject
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun ItemDetailPage(
    navHostController: NavHostController, itemId: Int,
    viewModel: ItemDetailViewModel = ItemDetailViewModel(koinInject(), koinInject(), koinInject())
) {
    val state by viewModel.state.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }


    viewModel.setInitialData(itemId)
    Scaffold(
        modifier = Modifier.padding(bottom = 64.dp),
        floatingActionButton = {
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally(initialOffsetX = { it * 2 }),
                exit = slideOutHorizontally(targetOffsetX = { it * 2 }),
            ) {
                ExtendedFloatingActionButton(
                    contentColor = Color.DarkGray,
                    onClick = {
                        showBottomSheet = true
                    },
                    icon = { Icon(Icons.Default.ArrowOutward, "") },
                    text = {
                        Text(
                            text = stringResource(id = R.string.transfer),
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    },
                )
            }
        },

        ) {
        if (showBottomSheet) {
            BottomSheet(
                onSave = {
                    showBottomSheet = false
                    viewModel.onNewMoving(it)
                },
                onDismiss = { showBottomSheet = false }, itemId = itemId,
                departmentList = state.departmentList,
                initialDepartmentId = state.item?.departmentId ?: 0
            )
        }
        Column(modifier = Modifier.padding(it)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { navHostController.popBackStack() },
                    content = { Icon(Icons.Default.ArrowBackIosNew, "") })
                Text(
                    state.item?.name ?: "",
                    modifier = Modifier.padding(12.dp),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    navHostController.navigate(
                        "${Routes.updateItem}/${state.item!!.id}",
                    )
                }) {
                    Icon(Icons.Default.Settings, "")
                }
            }
            if (state.item != null)
                Text(
                    viewModel.getDepartmentById(state.item!!.departmentId).name,
                    modifier = Modifier.padding(12.dp),
                    fontSize = 24.sp
                )

            Column(modifier = Modifier.padding(12.dp)) {
                Box(
                    modifier = Modifier
                        .background(shape = RoundedCornerShape(12.dp), color = Color.LightGray)
                        .heightIn(min = 96.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        state.item?.description ?: stringResource(id = R.string.no_description),
                        modifier = Modifier.padding(12.dp)
                    )
                }
                Text(
                    text = stringResource(id = R.string.item_movings),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 24.dp, bottom = 12.dp),
                )
                if (state.movings.isEmpty())
                    Text(stringResource(id = R.string.no_movings_found))
                else
                    LazyColumn() {
                        items(state.movings) {
                            ItemMovingListItem(
                                itemMoving = it,
                                destinationDepartment = viewModel.getDepartmentById(it.destinationDepartmentId),
                                initialDepartment = viewModel.getDepartmentById(it.initialDepartmentId)
                            )
                        }
                    }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    onDismiss: () -> Unit,
    onSave: (ItemMoving) -> Unit,
    itemId: Int,
    initialDepartmentId: Int,
    departmentList: List<Department>
) {

    var initialDate by remember { mutableStateOf<LocalDateTime?>(value = null) }
    var finishDate by remember { mutableStateOf<LocalDateTime>(value = LocalDateTime.now()) }
    var finishDepartment by remember { mutableStateOf<Department?>(value = null) }

    var dropdownExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
    ) {

        Column(modifier = Modifier.padding(bottom = 64.dp, start = 8.dp, end = 8.dp)) {

            DateListItem(onSelectDate = {
                initialDate = it
            }, title = "${stringResource(id = R.string.start_date)}:", initialValue = initialDate)
            DateListItem(onSelectDate = {
                finishDate = it
            }, title = "${stringResource(id = R.string.finish_date)}*:", initialValue = finishDate)

            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = {
                    dropdownExpanded = !dropdownExpanded
                },
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
            ) {
                CustomFormField(
                    text = finishDepartment?.name ?: "",
                    onValueChange = {},
                    placeholder = { Text(text = "${stringResource(id = R.string.department)}*") },
                    readOnly = true,
                    label = { Text(text = "${stringResource(id = R.string.department)}*") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                    },
                )

                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }) {
                    departmentList.forEach { option: Department ->
                        DropdownMenuItem(
                            content = { Text(option.name) },
                            onClick = {
                                dropdownExpanded = false
                                finishDepartment = option
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (finishDepartment == null) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.fields_not_filled),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    val movingItem = ItemMoving(
                        startDate = initialDate,
                        finishDate = finishDate,
                        initialDepartmentId = initialDepartmentId,
                        destinationDepartmentId = finishDepartment!!.id,
                        itemId = itemId,
                    )
                    onSave(movingItem)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp)
                    .background(shape = RoundedCornerShape(12.dp), color = Color.DarkGray),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)

            ) {
                Text(stringResource(id = R.string.save), color = Color.White)
            }
        }

    }
}