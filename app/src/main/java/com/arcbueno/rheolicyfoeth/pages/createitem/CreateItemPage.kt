package com.arcbueno.rheolicyfoeth.pages.createitem

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.components.CustomFormField
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.repositories.ItemRepository
import com.arcbueno.rheolicyfoeth.ui.theme.RheoliCyfoethTheme
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateItemPage(
    navHostController: NavHostController,
    createItemViewModel: CreateItemViewModel = koinInject(), itemId: Int? = null
) {
    val state by createItemViewModel.state.collectAsState()


    var itemName by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }
    var department by remember {
        mutableStateOf<Department?>(value = null)
    }
    var isHidden by remember { mutableStateOf(true) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var quantity by remember { mutableIntStateOf(1) }

    if (itemId != null && !state.loadedInitialData && state.departmentList.isNotEmpty()) {
        val item = createItemViewModel.setInitialData(itemId)
        itemName = item.name
        department = state.departmentList.filter { it.id == item.departmentId }.first()
        itemDescription = item.description ?: ""
        isHidden = item.isHidden
        quantity = item.quantity.toInt()
    }



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
            readOnly = state.isLoading,
            trailingIcon = null,
            label = { Text(text = stringResource(id = R.string.item)) },
        )
        Spacer(modifier = Modifier.padding(8.dp))
        CustomFormField(
            text = itemDescription,
            onValueChange = { itemDescription = it },
            placeholder = { Text(text = stringResource(id = R.string.item_description_textfield_placeholder)) },
            readOnly = state.isLoading,
            trailingIcon = null,
            label = { Text(text = stringResource(id = R.string.description)) },
            minHeight = 128.dp
        )
        Spacer(modifier = Modifier.padding(8.dp))
        CustomFormField(
            text = quantity.toString(),
            onValueChange = {
                val intValue = it.toIntOrNull()
                if (intValue != null) {
                    quantity = intValue
                }
            },
            placeholder = { Text(text = stringResource(id = R.string.item_quantity)) },
            readOnly = state.isLoading,
            trailingIcon = null,
            label = { Text(text = stringResource(id = R.string.item_quantity)) },
        )
        Spacer(modifier = Modifier.padding(8.dp))
        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = {
                if (!state.loadedInitialData) {
                    dropdownExpanded = !dropdownExpanded
                }
            }
        ) {
            CustomFormField(
                text = department?.name ?: "",
                onValueChange = {},
                placeholder = { Text(text = stringResource(id = R.string.department)) },
                readOnly = true,
                label = { Text(text = stringResource(id = R.string.department)) },
                trailingIcon = if (!state.loadedInitialData) ({
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                }) else null,
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
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                stringResource(id = R.string.is_hidden),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = isHidden,
                onCheckedChange = { isHidden = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.DarkGray,
                ),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                val validationText = createItemViewModel.validate(itemName, department, quantity);
                if (validationText != null) {
                    Toast.makeText(
                        context,
                        context.getString(validationText),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                val success =
                    createItemViewModel.createItem(
                        itemName,
                        itemDescription,
                        department!!,
                        isHidden
                    )

                if (!success) {
                    Toast.makeText(
                        context,
                        R.string.create_item_general_error,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                navHostController.popBackStack()

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 64.dp)
                .background(shape = RoundedCornerShape(12.dp), color = Color.DarkGray),
            colors = ButtonDefaults.buttonColors(Color.DarkGray)

        ) {
            Text(stringResource(id = R.string.save), color = Color.White)
        }
        if (state.isLoading)
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colors.primary,
            )
    }
}
