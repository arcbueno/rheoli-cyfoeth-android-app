package com.arcbueno.rheolicyfoeth.pages.itemlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.Routes
import com.arcbueno.rheolicyfoeth.components.CustomFormField
import com.arcbueno.rheolicyfoeth.components.ItemListItem
import org.koin.compose.koinInject


@Composable
fun ItemPage(
    navHostController: NavHostController,
    itemListViewModel: ItemListViewModel = ItemListViewModel(
        koinInject(),
        koinInject(),
        koinInject()
    )
) {
    val state by itemListViewModel.state.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val passwordString = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf<Int?>(null) }
    itemListViewModel.getAllItems()
    Scaffold(
        modifier = Modifier.padding(bottom = 64.dp),
        floatingActionButton = {
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally(initialOffsetX = { it * 2 }),
                exit = slideOutHorizontally(targetOffsetX = { it * 2 }),
            ) {
                FloatingActionButton(
                    onClick = {
                        navHostController.navigate(Routes.createItem)

                    },
                ) {
                    Icon(Icons.Filled.Add, stringResource(id = R.string.add_new_item))
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                if (showDialog.value)
                    AlertDialog(
                        onDismissRequest = {
                            showDialog.value = false
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                val result = itemListViewModel.showAll(passwordString.value)
                                if (result) {
                                    showDialog.value = false
                                    passwordString.value = ""
                                    return@TextButton
                                }
                                passwordError.value = R.string.wrong_password
                            }) {
                                Text(stringResource(id = R.string.ok))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDialog.value = false
                            }) {
                                Text(stringResource(id = R.string.cancel))
                            }
                        },
                        title = {
                            Text(
                                text = stringResource(id = R.string.hidden_item_password_input_title),
                                fontSize = 20.sp
                            )
                        },
                        text = {
                            CustomFormField(
                                text = passwordString.value,
                                onValueChange = {
                                    passwordString.value = it
                                },
                                placeholder = {
                                    Text(stringResource(id = R.string.password))
                                },
                                label = {
                                    Text(stringResource(id = R.string.password))
                                },
                                error = if (passwordError.value != null) stringResource(id = passwordError.value!!) else null
                            )
                        },
                    )
                Row {
                    Text(
                        stringResource(id = R.string.all_items),
                        modifier = Modifier.padding(12.dp),
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            if (state.showAll) {
                                itemListViewModel.hideItens()
                                return@IconButton
                            }
                            showDialog.value = true
                        }
                    ) {
                        Icon(
                            imageVector = if (state.showAll) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                        )
                    }

                }
                LazyColumn() {
                    items(state.itemList) {
                        itemListViewModel.getDepartmentById(it.departmentId)
                        ItemListItem(item = it, department = state.departmentById[it.id], onTap = {
                            navHostController.navigate(
                                "${Routes.itemDetails}/${it.id}",
                            )
                        })
                    }
                }
            }
        }
    )
}
