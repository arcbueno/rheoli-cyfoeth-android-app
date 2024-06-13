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
import com.arcbueno.rheolicyfoeth.models.Item

@Composable
fun ItemListItem(
    modifier: Modifier = Modifier,
    item: Item,
    department: Department?,
    onTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(width = 2.dp, color = Color.LightGray, RoundedCornerShape(12.dp))
            .heightIn(min = 82.dp)
            .clickable(
                onClick = onTap
            ),
    ) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(50))
                .background(Color.Transparent)
                .padding(8.dp)
                .align(Alignment.Center)
        ) {
            Column {
                Text(item.name, fontSize = 24.sp)
                if (!item.description.isNullOrEmpty())
                    Text(item.description, color = Color.DarkGray)
                if (department != null)
                    Text(text = department.name)
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