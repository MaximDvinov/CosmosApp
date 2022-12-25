package com.dvinov.myspaceapp.screen.apod.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.dvinov.myspaceapp.R
import com.dvinov.myspaceapp.screen.apod.ApodViewModel
import com.dvinov.myspaceapp.screen.apod.LoadState
import com.dvinov.myspaceapp.screen.apod.model.ApodModel
import java.time.LocalDate

@Composable
fun ApodScreen(
    scrollState: ScrollState,
    viewModel: ApodViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        if (uiState.apodData == null) viewModel.getApodToday()
    }

    Crossfade(targetState = uiState.apodData) { state ->
        when (state) {
            is LoadState.Error -> {
                ErrorMessage(
                    state,
                    onClickReload = viewModel::getApodToday
                )
            }
            is LoadState.Loading -> {
                ProgressIndicator()
            }
            is LoadState.Success -> {
                ApodContent(
                    scrollState = scrollState,
                    apodState = state,
                    navController = navController,
                    onRandomClick = viewModel::getApodDataRandom
                ) { date ->
                    viewModel.getApodDate(date)
                }
            }
            else -> {

            }
        }
    }
}

@Composable
private fun ApodContent(
    scrollState: ScrollState,
    apodState: LoadState.Success<ApodModel>,
    navController: NavController,
    onRandomClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            MediaHeader(
                apodState = apodState,
                navController = navController,
            )
            DescriptionAndCopyright(apodData = apodState.data)
        }

        DatePicker(showDialog = showDialog,
            onChangeState = { showDialog = it },
            onDateSelected = {
                onDateClick(it)
            })

        Row(modifier = Modifier.padding(top = 0.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)) {
            PrimaryBtn(
                modifier = Modifier.weight(2f), text = stringResource(id = R.string.get_a_random)
            ) { onRandomClick() }
            Divider(color = Color.Transparent, modifier = Modifier.width(16.dp))
            SecondaryBtn(
                modifier = Modifier.weight(1f), text = stringResource(id = R.string.get_date)
            ) { showDialog = true }
        }
    }
}


