package com.arcbueno.rheolicyfoeth.pages.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.Icon
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.components.CustomFormField
import org.koin.compose.koinInject

@Composable
fun SettingsPage(
    navHostController: NavHostController,
    viewModel: SettingsViewModel = SettingsViewModel(koinInject())
) {
    val state by viewModel.state.collectAsState()
    val showBottomSheet = remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.padding(bottom = 64.dp)) {
        if (showBottomSheet.value) {
            BottomSheet(
                onSave = {
                    val result = viewModel.onChangeKey(it)
                    if (result)
                        showBottomSheet.value = false
                },
                onDismiss = { showBottomSheet.value = false },
                currentKey = state.key
            )
        }
        Column(modifier = Modifier.padding(it)) {
            Box(modifier = Modifier
                .padding(8.dp)
                .clickable { showBottomSheet.value = true }
                .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row {
                        Icon(Icons.Default.Key, "")
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(stringResource(id = R.string.change_key))
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, "")
                }
            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    currentKey: String?,
) {

    var currentKeyField by remember { mutableStateOf<String>(value = "") }
    var newKeyField by remember { mutableStateOf<String>(value = "") }
    var againKeyField by remember { mutableStateOf<String>(value = "") }
    var context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
    ) {

        Column(
            modifier = Modifier
                .padding(bottom = 64.dp, start = 8.dp, end = 8.dp)
                .imePadding()
        ) {

            CustomFormField(
                text = currentKeyField,
                onValueChange = {
                    currentKeyField = it
                },
                placeholder = { Text(stringResource(id = R.string.current_key)) },
                label = { Text(stringResource(id = R.string.current_key)) },
            )

            Spacer(modifier = Modifier.padding(8.dp))
            CustomFormField(
                text = newKeyField,
                onValueChange = {
                    newKeyField = it
                },
                placeholder = { Text(stringResource(id = R.string.new_key)) },
                label = { Text(stringResource(id = R.string.new_key)) },
            )

            Spacer(modifier = Modifier.padding(8.dp))
            CustomFormField(
                text = againKeyField,
                onValueChange = {
                    againKeyField = it
                },
                placeholder = { Text(stringResource(id = R.string.digit_the_new_key_again)) },
                label = { Text(stringResource(id = R.string.digit_the_new_key_again)) },
            )


            Spacer(modifier = Modifier.padding(24.dp))
            Button(
                onClick = {
                    if (currentKeyField != currentKey) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.wrong_key),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    if (newKeyField != againKeyField) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.new_keys_incorrect),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }


                    onSave(newKeyField)
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
