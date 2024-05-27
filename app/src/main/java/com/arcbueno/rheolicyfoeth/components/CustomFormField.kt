package com.arcbueno.rheolicyfoeth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomFormField(
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable() (() -> Unit)?,
    label: @Composable() (() -> Unit)?,
    readOnly: Boolean = false,
    trailingIcon: @Composable() (() -> Unit)?,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(horizontal = 12.dp, vertical = 4.dp),

        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
        ),
        readOnly = readOnly,
        value = text,
        onValueChange = onValueChange,
        placeholder = placeholder,
        label = label,
        trailingIcon = trailingIcon
    )
}