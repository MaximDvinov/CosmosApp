package com.cosmos.app.screen.random

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.cosmos.app.screen.random.model.ApodModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.koinViewModel
import com.cosmos.app.R
import com.cosmos.app.ui.theme.*

@Composable
fun RandomScreen(viewModel: RandomViewModel = koinViewModel()) {
    val uiState = viewModel.uiState

    LaunchedEffect(false) {
        viewModel.getApodDataRandom()
    }

    Crossfade(targetState = uiState.isLoading) { isLoading ->
        if (isLoading) {
            ProgressIndicator()
        } else {
            ApodContent(uiState) { viewModel.getApodDataRandom() }
        }
    }

}

@Composable
private fun ApodContent(
    uiState: RandomUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            MediaContentContainer(apodData = uiState.apodData)
            DescriptionAndCopyright(apodData = uiState.apodData)
        }
        GetARandomBtn { onClick() }
    }
}

@Composable
private fun GetARandomBtn(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(top = 0.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .shadow(
                10.dp,
                shape = RoundedCornerShape(15.dp),
                ambientColor = primary,
                spotColor = primary
            ),
    ) {
        Text(
            text = stringResource(id = R.string.get_a_random),
            style = title,
            color = white,
            modifier = Modifier
                .background(primary)
                .padding(vertical = 8.dp),
        )
    }
}

@Composable
private fun DescriptionAndCopyright(apodData: ApodModel?) {
    Column(modifier = Modifier.padding(top = 12.dp, bottom = 16.dp)) {
        Text(
            text = apodData?.explanation ?: "",
            style = description,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        if (!apodData?.copyright.isNullOrBlank())
            Text(
                text = apodData?.copyright!!,
                style = subTitle,
                modifier = Modifier.padding(vertical = 4.dp)
            )
    }

}

@Composable
fun ProgressIndicator() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                R.raw.load
            )
        )
        LottieAnimation(
            composition,
            restartOnPlay = true,
            iterations = 10,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MediaContentContainer(modifier: Modifier = Modifier, apodData: ApodModel?) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (!apodData?.hdurl.isNullOrBlank() || !apodData?.url.isNullOrBlank())
            ImageView(apodData?.hdurl ?: apodData?.url!!, apodData?.title)

        if (!apodData?.title.isNullOrBlank())
            Title(modifier = Modifier, text = apodData?.title!!)
    }
}

@Composable
private fun ImageView(url: String, contentDescription: String?) {
    GlideImage(
        imageModel = { url },
        imageOptions = ImageOptions(
            contentDescription = contentDescription,
            contentScale = ContentScale.FillWidth
        ),
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .clip(RoundedCornerShape(16.dp)),
        loading = {
            ImageLoading()
        },
        failure = {
            ImageError(text = "error loading the image")
        }
    )
}

@Composable
fun ImageError(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(card_bg)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = title,
            maxLines = 2,
            modifier = Modifier
                .padding(top = 12.dp, start = 15.dp, end = 15.dp, bottom = 12.dp)
        )
    }
}

@Composable
private fun ImageLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(card_bg)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        ProgressIndicator()
    }
}

@Composable
fun Title(modifier: Modifier = Modifier, text: String) {
    Row(
        modifier = modifier
            .padding(top = 16.dp)
            .shadow(
                10.dp,
                shape = RoundedCornerShape(15.dp),
                ambientColor = card_bg,
                spotColor = card_bg
            )
            .clip(RoundedCornerShape(15.dp))
            .background(card_bg),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = title,
            maxLines = 2,
            modifier = Modifier
                .padding(top = 12.dp, start = 15.dp, end = 15.dp, bottom = 12.dp)
                .weight(1f)
        )
    }
}
