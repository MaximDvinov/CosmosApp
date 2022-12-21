package com.cosmos.app.screen.apod.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cosmos.app.R
import com.cosmos.app.screen.apod.LoadState
import com.cosmos.app.screen.apod.model.ApodModel
import com.cosmos.app.ui.theme.description
import com.cosmos.app.ui.theme.title

@Composable
fun ErrorMessage(uiState: LoadState.Error<ApodModel>, onClickReload: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.error),
                style = title,
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )

            if (uiState.message != null) {
                Text(
                    text = uiState.message,
                    style = description,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                )
            }
        }

        PrimaryBtn(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.try_again)) {
            onClickReload()
        }
    }
}