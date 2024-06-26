package com.arcbueno.rheolicyfoeth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arcbueno.rheolicyfoeth.models.Department


@Composable
fun DepartmentListItem(modifier: Modifier = Modifier, department: Department, onTap: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(width = 2.dp, color = Color.LightGray, RoundedCornerShape(12.dp))
            .heightIn(min = 64.dp)
            .clickable(onClick = onTap),
    ) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(50))
                .background(Color.Transparent)
                .padding(8.dp)
                .align(Alignment.Center)
        ) {
            Column {
                Text(department.name, fontSize = 24.sp)
                if (!department.description.isNullOrEmpty())
                    Text(department.description, color = Color.DarkGray)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .align(
                        Alignment.CenterVertically
                    )
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    "open",

                    )
            }
        }
    }
}