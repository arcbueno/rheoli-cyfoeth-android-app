package com.arcbueno.rheolicyfoeth.pages.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arcbueno.rheolicyfoeth.Config
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.components.CustomAppBar
import com.arcbueno.rheolicyfoeth.components.DepartmentListItem
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import com.arcbueno.rheolicyfoeth.ui.theme.RheoliCyfoethTheme
import org.koin.compose.koinInject
import org.koin.core.context.GlobalContext


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
