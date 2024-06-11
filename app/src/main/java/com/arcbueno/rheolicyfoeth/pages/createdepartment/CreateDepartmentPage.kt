package com.arcbueno.rheolicyfoeth.pages.createdepartment

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenuItem
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.components.CustomFormField
import org.koin.compose.koinInject

@Composable
fun CreateDepartmentPage(
    navHostController: NavHostController,
    viewModel: CreateDepartmentViewModel = CreateDepartmentViewModel(koinInject()),
    departmentId: Int? = null,
) {
    val state by viewModel.state.collectAsState()

    var departmentName by remember { mutableStateOf("") }
    var departmentDescription by remember { mutableStateOf("") }

    if (departmentId != null && !state.loadedInitialData) {
        val department = viewModel.setInitialData(departmentId)

        departmentName = department.name
        departmentDescription = department.description ?: ""
    }



    Box {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            Text(
                stringResource(id = R.string.add_new_department),
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 18.dp)
            )
            CustomFormField(
                text = departmentName,
                onValueChange = { departmentName = it },
                placeholder = { Text(text = stringResource(id = R.string.department_name_textfield_placeholder)) },
                readOnly = state.isLoading,
                trailingIcon = null,
                label = { Text(text = stringResource(id = R.string.department)) },
            )
            Spacer(modifier = Modifier.padding(8.dp))
            CustomFormField(
                text = departmentDescription,
                onValueChange = { departmentDescription = it },
                placeholder = { Text(text = stringResource(id = R.string.department_description_textfield_placeholder)) },
                readOnly = state.isLoading,
                trailingIcon = null,
                label = { Text(text = stringResource(id = R.string.description)) },
                minHeight = 128.dp
            )

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    val validationText = viewModel.validate(departmentName);
                    if (validationText != null) {
                        Toast.makeText(
                            context,
                            context.getString(validationText),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    val success =
                        viewModel.createDepartment(
                            departmentName,
                            departmentDescription,
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
        }
        if (state.isLoading)
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colors.primary,
            )
        if (state.error != null)
            Box(
                Modifier
                    .align(Alignment.BottomEnd)
                    .background(Color.Red)
            ) {
                Text(state.error!!)
            }
    }
}