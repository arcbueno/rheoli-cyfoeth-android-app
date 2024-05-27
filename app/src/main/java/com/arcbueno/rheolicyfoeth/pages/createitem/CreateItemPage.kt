package com.arcbueno.rheolicyfoeth.pages.createitem

import android.widget.StackView
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.components.CustomFormField
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.Item
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemRepository
import com.arcbueno.rheolicyfoeth.ui.theme.RheoliCyfoethTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PagePreview() {
    RheoliCyfoethTheme {
        CreateItemPage(NavHostController(LocalContext.current))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateItemPage(
    navHostController: NavHostController,
    createItemViewModel: CreateItemViewModel = viewModel()
) {
    val state by createItemViewModel.state.collectAsState()

    var itemName by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }
    var department by remember {
        mutableStateOf<Department?>(value = null)
    }
    var dropdownExpanded by remember { mutableStateOf(false) }


    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        val context = LocalContext.current
        Text(
            stringResource(id = R.string.add_new_item),
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 18.dp)
        )
        CustomFormField(
            text = itemName,
            onValueChange = { itemName = it },
            placeholder = { Text(text = stringResource(id = R.string.item_name_textfield_placeholder)) },
            label = { Text(text = stringResource(id = R.string.item)) },
            trailingIcon = null,
            readOnly = state.isLoading
        )
        Spacer(modifier = Modifier.padding(8.dp))
        CustomFormField(
            text = itemDescription,
            onValueChange = { itemDescription = it },
            placeholder = { Text(text = stringResource(id = R.string.item_description_textfield_placeholder)) },
            label = { Text(text = stringResource(id = R.string.description)) },
            trailingIcon = null,
            readOnly = state.isLoading
        )
        Spacer(modifier = Modifier.padding(8.dp))
        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = {
                dropdownExpanded = !dropdownExpanded
            }
        ) {
            CustomFormField(
                readOnly = true,
                text = department?.name ?: "",
                onValueChange = {},
                placeholder = { Text(text = stringResource(id = R.string.department)) },
                label = { Text(text = stringResource(id = R.string.department)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                }
            )

            ExposedDropdownMenu(
                expanded = !state.isLoading && dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }) {
                state.departmentList.forEach { option: Department ->
                    DropdownMenuItem(
                        content = { Text(option.name) },
                        onClick = {
                            dropdownExpanded = false
                            department = option
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(24.dp))
        Button(onClick = {
            val validationText = createItemViewModel.validate(itemName, department);
            if (validationText != null) {

                Toast.makeText(
                    context,
                    context.getString(validationText),
                    Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            val success = createItemViewModel.createItem(itemName, itemDescription, department!!)

            if (!success) {
                Toast.makeText(
                    context,
                    R.string.create_item_general_error,
                    Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            navHostController.popBackStack()

        }) {
            Text(stringResource(id = R.string.save))
        }

        if (state.isLoading)
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colors.primary,
            )
    }
}
