package com.arcbueno.rheolicyfoeth.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomAppBar(title: String) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        content = {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .width(IntrinsicSize.Max),
            )
        },
    )
}