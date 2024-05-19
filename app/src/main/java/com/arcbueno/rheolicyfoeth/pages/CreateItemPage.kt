package com.arcbueno.rheolicyfoeth.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
fun CreateItemPage(navHostController: NavHostController) {
    val allDepartments = DepartmentRepository.getAll()

    var itemName by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }
    var department by remember {
        mutableStateOf<Department?>(value = null)
    }
    var dropdownExpanded by remember { mutableStateOf(false) }


    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        val context = LocalContext.current
        Text("Adicionar Item", fontSize = 24.sp, modifier = Modifier.padding(bottom = 18.dp))
        CustomFormField(
            text = itemName,
            onValueChange = { itemName = it },
            placeholder = { Text(text = "e.g. Mesa") },
            label = { Text(text = "Item") },

            )
        Spacer(modifier = Modifier.padding(8.dp))
        CustomFormField(
            text = itemDescription,
            onValueChange = { itemDescription = it },
            placeholder = { Text(text = "e.g. 4 pernas") },
            label = { Text(text = "Descrição") },
        )


        Spacer(modifier = Modifier.padding(8.dp))
        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = {
                dropdownExpanded = !dropdownExpanded
            }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = department?.name ?: "",
                onValueChange = {},
                label = { Text(text = "Department") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                },

                )

            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }) {
                allDepartments.forEach { option: Department ->
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
            if (department == null) {
                Toast.makeText(
                    context,
                    "Necessário selecionar departamento",
                    Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            ItemRepository.create(
                Item(
                    name = itemName,
                    description = itemDescription,
                    departmentId = department!!.id,
                ),
            )
            navHostController.popBackStack()
        }) {
            Text("Save")
        }
    }
}
