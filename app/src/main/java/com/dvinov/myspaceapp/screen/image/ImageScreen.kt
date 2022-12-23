package com.dvinov.myspaceapp.screen.image

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.dvinov.myspaceapp.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.roundToInt

@Composable
fun ImageScreen(url: String, onBackClick: () -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var zoom by remember { mutableStateOf(1f) }

    Box(contentAlignment = Alignment.TopStart) {
        IconButton(onClick = { onBackClick() }, modifier = Modifier.zIndex(10f)) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(
                    id = R.string.back
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, gestureZoom, _ ->
                        zoom *= if (1 > zoom * gestureZoom) 1f else gestureZoom
                        val x = pan.x * zoom * 0.8f
                        val y = pan.y * zoom * 0.8f
                        offsetX += x
                        offsetY += y
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .heightIn()
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .graphicsLayer(
                    scaleX = zoom,
                    scaleY = zoom,
                )
                .pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = {
                        if (zoom == 1f && offsetX == 0f && offsetY == 0f) {
                            zoom = 2f
                            offsetX += it.x * 0.5f
                        } else {
                            zoom = 1f
                            offsetX = 0f
                            offsetY = 0f
                        }
                    })
                }
                .defaultMinSize(minHeight = 100.dp)
                .padding(bottom = 20.dp)) {

                GlideImage(
                    imageModel = { url },
                    modifier = Modifier
                        .fillMaxWidth(),
                    imageOptions = ImageOptions(
                        contentDescription = url,
                        contentScale = ContentScale.FillWidth
                    )
                )
            }

        }
    }

}