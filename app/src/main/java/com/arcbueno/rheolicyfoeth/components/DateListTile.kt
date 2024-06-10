package com.arcbueno.rheolicyfoeth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.arcbueno.rheolicyfoeth.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateListItem(
    onSelectDate: (LocalDateTime) -> Unit,
    title: String,
    initialValue: LocalDateTime?
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    var date by remember { mutableStateOf<LocalDateTime?>(value = null) }
    val datePickerState = rememberDatePickerState(yearRange = 2000..LocalDateTime.now().year)
    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }
    if (initialValue != null) {
        date = initialValue
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showDatePickerDialog) {
            DatePickerDialog(
                onDismissRequest = { showDatePickerDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            datePickerState
                                .selectedDateMillis?.let { millis ->
                                    date = Instant.ofEpochMilli(millis)
                                        .atZone(ZoneId.systemDefault()).toLocalDateTime();
                                    onSelectDate(date!!)
                                }
                            showDatePickerDialog = false
                        }) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "")
                    }
                }) {

                DatePicker(state = datePickerState)
            }
        }
        Row {
            Text(
                title
            )
            if (date == null)
                Text(stringResource(id = R.string.empty), color = Color.LightGray)
            else
                Text(formatter.format(date))

        }
        IconButton(
            onClick = {
                showDatePickerDialog = true
            },
            content = {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = ""
                )
            })
    }
}