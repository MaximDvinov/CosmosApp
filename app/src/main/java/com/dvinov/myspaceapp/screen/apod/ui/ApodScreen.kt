package com.dvinov.myspaceapp.screen.apod.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import com.skydoves.landscapist.glide.GlideImageState

@Composable
fun ApodScreen(
    scrollState: ScrollState,
    viewModel: ApodViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val showDialog = uiState.isShowDialog

    LaunchedEffect(Unit) {
        if (uiState.apodData == null) viewModel.getApodToday()
    }

    Crossfade(targetState = uiState.apodData) { state ->
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
        selectedDate = uiState.selectedDate,
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
                color = dominantColorAnimated.value.copy(0.9f)
            ) { state: GlideImageState ->
                dominantColor = getDominantColor(state)
            }
            DescriptionAndCopyright(apodData = apodState.data)
        }

        Row(modifier = Modifier.padding(top = 0.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)) {
            PrimaryBtn(
                modifier = Modifier.weight(2f), text = stringResource(id = R.string.get_a_random),
                onClick = onRandomClick
            )
            Divider(color = Color.Transparent, modifier = Modifier.width(16.dp))
            SecondaryBtn(
                modifier = Modifier.weight(1f), text = stringResource(id = R.string.get_date),
                onClick = remember {
                    { onShowDialog(true) }
                }
            )
        }
    }
}




