package com.dvinov.myspaceapp.screen.apod.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dvinov.myspaceapp.R
import com.dvinov.myspaceapp.screen.image.navigateToImageScreen
import com.dvinov.myspaceapp.ui.theme.card_bg
import com.dvinov.myspaceapp.ui.theme.primary
import com.dvinov.myspaceapp.ui.theme.title
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.glide.GlideImageState

@Composable
fun ImageView(
    url: String,
    contentDescription: String?,
    navController: NavController,
    onChangeColor: (image: GlideImageState) -> Unit
) {
    GlideImage(imageModel = { url },
        onImageStateChanged = onChangeColor,
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
            ImageError(text = stringResource(R.string.error_loading_image))
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
            .placeholder(
                visible = true,
                color = card_bg,
                shape = RoundedCornerShape(16.dp),
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = primary.copy(alpha = 0.3f),
                ),
            )
    )
}