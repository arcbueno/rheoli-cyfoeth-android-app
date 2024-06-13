package com.arcbueno.rheolicyfoeth.pages.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arcbueno.rheolicyfoeth.Config
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.components.DepartmentListItem
import com.arcbueno.rheolicyfoeth.ui.theme.RheoliCyfoethTheme
import org.koin.compose.koinInject


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PagePreview() {
    Config.startConfig(LocalContext.current)
    RheoliCyfoethTheme {
        HomePage(NavHostController(LocalContext.current))
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomePage(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = HomeViewModel(koinInject(), koinInject())
) {
    val state by homeViewModel.state.collectAsState()
    Column {
        Row {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
                    .height(100.dp)
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Column {
                    Text(
                        stringResource(id = R.string.total_items),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        state.itemList.size.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                }
            }
            Spacer(Modifier.weight(0.1f))
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
                    .height(100.dp)
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Column {
                    Text(
                        stringResource(id = R.string.total_departments),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        state.departmentList.size.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        LazyColumn() {
            items(state.departmentList) {
                DepartmentListItem(department = it, onTap = {})
            }
        }

    }

}
