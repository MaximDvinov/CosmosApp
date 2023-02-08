package com.dvinov.myspaceapp.screen.apod.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.dvinov.myspaceapp.R
import com.dvinov.myspaceapp.getDominantColor
import com.dvinov.myspaceapp.screen.apod.ApodViewModel
import com.dvinov.myspaceapp.screen.apod.LoadResult
import com.dvinov.myspaceapp.screen.apod.model.ApodModel
import com.dvinov.myspaceapp.ui.theme.blue_bg
import com.dvinov.myspaceapp.ui.theme.white
import com.dvinov.myspaceapp.ui.theme.white_alpha
import com.skydoves.landscapist.glide.GlideImageState

@Composable
fun ApodScreen(
    scrollState: ScrollState,
    viewModel: ApodViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val apodData = uiState.apodData
    val showDialog = uiState.isShowDialog
    val selectedDate = uiState.selectedDate

    LaunchedEffect(Unit) {
        if (apodData == null) viewModel.getApodToday()
    }

    Crossfade(targetState = apodData) { state ->
        when (state) {
            is LoadResult.Error -> {
                ErrorMessage(
                    state, onClickReload = viewModel::getApodToday
                )
            }
            is LoadResult.Loading -> {
                ProgressIndicator()
            }
            is LoadResult.Success -> {
                ApodContent(
                    scrollState = scrollState,
                    apodState = state,
                    navController = navController,
                    onRandomClick = viewModel::getApodDataRandom,
                    onShowDialog = viewModel::changeShowDialog
                )
            }
            null -> {}
        }
    }

    DatePicker(
        showDialog = showDialog,
        selectedDate = selectedDate,
        onChangeShowState = viewModel::changeShowDialog,
        onChangeDateState = viewModel::selectDate,
        onDateSelected = viewModel::getApodDate
    )
}

@Composable
private fun ApodContent(
    scrollState: ScrollState,
    apodState: LoadResult.Success<ApodModel>,
    navController: NavController,
    onRandomClick: () -> Unit,
    onShowDialog: (Boolean) -> Unit,
) {
    var dominantColor by remember { mutableStateOf(blue_bg) }
    val dominantColorAnimated = animateColorAsState(targetValue = dominantColor)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        dominantColorAnimated.value.copy(0.5f), blue_bg
                    ),
                )
            )
            .systemBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)) {
            MediaHeader(
                apodState = apodState,
                navController = navController,
                color = dominantColorAnimated.value.copy(0.5f)
            ) { color: Color ->
                dominantColor = color
            }
            DescriptionAndCopyright(apodData = apodState.data)
        }

        Row(modifier = Modifier.padding(top = 0.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)) {
            ColoredBtn(
                modifier = Modifier
                    .weight(2f)
                    .border(
                        width = (0.5).dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                white.copy(0.3f), white.copy(0.2f)
                            ),
                            start = Offset.Zero,
                            end = Offset.Infinite
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ),
                text = stringResource(id = R.string.get_a_random),
                color = white.copy(alpha = 0.2f),
                onClick = onRandomClick
            )

            Divider(color = Color.Transparent, modifier = Modifier.width(16.dp))

            ColoredBtn(
                modifier = Modifier.weight(1f), text = stringResource(id = R.string.get_date),
                color = white.copy(alpha = 0.1f),
                onClick = remember {
                    { onShowDialog(true) }
                }
            )
        }
    }
}




