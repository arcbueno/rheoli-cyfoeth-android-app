package com.arcbueno.rheolicyfoeth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arcbueno.rheolicyfoeth.MainPage
import com.arcbueno.rheolicyfoeth.ui.theme.RheoliCyfoethTheme


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    RheoliCyfoethTheme {
        CustomFormField(
            text = "",
            onValueChange = {

            },
            placeholder = { Text("Teste") },
            readOnly = false,
            trailingIcon = null,
            label = null,
            error = "String",
        )
    }
}

@Composable
fun CustomFormField(
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable() (() -> Unit)? = null,
    readOnly: Boolean = false,
    trailingIcon: @Composable() (() -> Unit)? = null,
    label: @Composable() (() -> Unit)? = null,
    minHeight: Dp? = null,
    error: String? = null,
) {
    Column {
        TextField(

            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.LightGray.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .heightIn(min = minHeight ?: Dp.Unspecified),

            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedLabelColor = Color.DarkGray,
                disabledLabelColor = Color.DarkGray,
                errorLabelColor = Color.DarkGray,
            ),
            readOnly = readOnly,
            value = text,
            onValueChange = onValueChange,
            placeholder = placeholder,
            label = label,
            trailingIcon = trailingIcon
        )
        if (error != null) {
            Text(
                modifier = Modifier.padding(start= 12.dp).fillMaxWidth(),
                text = error,
                color = MaterialTheme.colors.error
            )
        }
    }
}