package com.cosmos.app.screen.apod

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.cosmos.app.screen.apod.model.ApodModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.cosmos.app.R
import com.cosmos.app.getYoutubeId
import com.cosmos.app.screen.image.navigateToImageScreen
import com.cosmos.app.ui.theme.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.time.format.DateTimeFormatter

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

    Crossfade(targetState = uiState.isLoading) { isLoading ->
        if (isLoading) {
            ProgressIndicator()
        } else {
            ApodContent(
                scrollState = scrollState,
                uiState,
                navController,
                onClick = viewModel::getApodDataRandom
            )
        }
    }

}

@Composable
private fun ApodContent(
    scrollState: ScrollState,
    uiState: ApodUiState,
    navController: NavController,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            MediaContentContainer(apodData = uiState.apodData, navController = navController)
            DescriptionAndCopyright(apodData = uiState.apodData)
        }
        GetARandomApodBtn { onClick() }
    }
}

@Composable
private fun GetARandomApodBtn(onClick: () -> Unit) {
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

        if (!apodData?.copyright.isNullOrBlank()) Text(
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
fun MediaContentContainer(
    modifier: Modifier = Modifier,
    apodData: ApodModel?,
    navController: NavController
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (apodData?.date != null) {
            val formatDate = remember {
                DateTimeFormatter.ofPattern("yyyy MMMM dd").format(apodData.date)
            }

            Text(
                text = "Picture of the day: $formatDate",
                style = title,
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
            )
        }

        if (!apodData?.hdurl.isNullOrBlank() || !apodData?.url.isNullOrBlank()) {
            if (apodData?.media_type == "image")
                ImageView(
                    apodData.hdurl ?: apodData.url!!,
                    apodData.title,
                    navController
                )
            else
                YouTubeView(apodData?.url)
        }

        if (!apodData?.title.isNullOrBlank()) Title(
            modifier = Modifier.padding(top = 16.dp),
            text = apodData?.title!!
        )
    }
}

@Composable
fun YouTubeView(url: String?) {
    AndroidView(modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(minHeight = 100.dp)
        .clip(RoundedCornerShape(16.dp)), factory = { context ->
        val youTubePlayerView = YouTubePlayerView(context)

        youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                getYoutubeId(url)?.let { youTubePlayer.loadVideo(it, 0f) }
            }

        })

        return@AndroidView youTubePlayerView
    }, update = { })
}

@Composable
private fun ImageView(
    url: String,
    contentDescription: String?,
    navController: NavController
) {
    GlideImage(imageModel = { url },
        imageOptions = ImageOptions(
            contentDescription = contentDescription, contentScale = ContentScale.FillWidth
        ),
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                navController.navigateToImageScreen(url)
            },
        loading = {
            ImageLoading()
        },
        failure = {
            ImageError(text = "error loading the image")
        })
}

@Composable
fun ImageError(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(card_bg)
            .clip(RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = title,
            maxLines = 2,
            modifier = Modifier.padding(top = 12.dp, start = 15.dp, end = 15.dp, bottom = 12.dp)
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
            .clip(RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center
    ) {
        ProgressIndicator()
    }
}

@Composable
fun Title(modifier: Modifier = Modifier, text: String) {
    Row(
        modifier = modifier
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
