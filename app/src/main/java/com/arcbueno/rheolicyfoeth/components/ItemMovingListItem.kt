package com.arcbueno.rheolicyfoeth.components

import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.ItemMoving
import java.time.format.DateTimeFormatter


@Composable
fun ItemMovingListItem(
    modifier: Modifier = Modifier,
    itemMoving: ItemMoving,
    initialDepartment: Department,
    destinationDepartment: Department
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(width = 2.dp, color = Color.LightGray, RoundedCornerShape(12.dp))
            .heightIn(min = 64.dp),
    ) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(50))
                .background(Color.Transparent)
                .padding(8.dp)
                .align(Alignment.Center)
        ) {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Text(stringResource(id = R.string.initial_department), fontSize = 18.sp)
                Text(initialDepartment.name, color = Color.DarkGray, fontSize = 24.sp)
                Text(
                    formatter.format((itemMoving.startDate ?: itemMoving.finishDate)),
                    color = Color.DarkGray,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.padding(12.dp))
            Column {
                Text(stringResource(id = R.string.destination), fontSize = 18.sp)
                Text(destinationDepartment.name, color = Color.DarkGray, fontSize = 24.sp)
                Text(
                    formatter.format(itemMoving.finishDate),
                    color = Color.DarkGray,
                    fontSize = 18.sp
                )
            }

        }
    }
}