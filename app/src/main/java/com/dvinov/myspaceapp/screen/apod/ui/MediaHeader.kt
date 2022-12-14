package com.dvinov.myspaceapp.screen.apod.ui

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dvinov.myspaceapp.screen.apod.model.ApodModel
import com.dvinov.myspaceapp.ui.theme.card_bg
import com.dvinov.myspaceapp.ui.theme.title
import java.time.format.DateTimeFormatter
import com.dvinov.myspaceapp.downloadImage
import com.dvinov.myspaceapp.screen.apod.LoadState
import com.dvinov.myspaceapp.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.runtime.setValue

@Composable
fun MediaHeader(
    modifier: Modifier = Modifier,
    navController: NavController,
    apodState: LoadState.Success<ApodModel>,
) {
    var imageLoadState: LoadState<Unit>? by remember {
        mutableStateOf(null)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        if (apodState.data?.date != null) {
            val formatDate = remember(apodState.data) {
                DateTimeFormatter.ofPattern("yyyy MMM dd").format(apodState.data.date)
            }

            Text(
                text = "${stringResource(R.string.picture_of_the_day)} $formatDate",
                style = title,
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
            )
        }

        if (!apodState.data?.hdurl.isNullOrBlank() || !apodState.data?.url.isNullOrBlank()) {
            if (apodState.data?.media_type == "image") ImageView(
                url = apodState.data.url!!,
                hdurl = apodState.data.hdurl,
                apodState.data.title,
                navController
            )
            else YouTubeView(apodState.data?.url)
        }

        if (!apodState.data?.title.isNullOrBlank()) TitleOrDownloadBtn(
            modifier = Modifier.padding(top = 16.dp),
            imageLoadState = imageLoadState,
            text = apodState.data?.title!!,
            type = apodState.data.media_type,
            url = apodState.data.url,
            onChangeState = {
                imageLoadState = it
            }
        )
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TitleOrDownloadBtn(
    modifier: Modifier = Modifier,
    onChangeState: (loadState: LoadState<Unit>) -> Unit,
    text: String,
    url: String?,
    type: String?,
    imageLoadState: LoadState<Unit>?
) {
    val permissionState = rememberPermissionState(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    val context = LocalContext.current

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .padding()
            .background(card_bg),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text, style = title, modifier = Modifier
                .padding(
                    top = 12.dp, start = 15.dp, end = 15.dp, bottom = 12.dp
                )
                .weight(1f)
        )


        if (url != null && type != "video") Box(
            modifier = Modifier
                .size(40.dp)
                .padding(end = 8.dp), contentAlignment = Alignment.Center
        ) {
            when (imageLoadState) {
                is LoadState.Error -> {
                    Toast.makeText(context, imageLoadState.message, Toast.LENGTH_SHORT).show()
                }
                is LoadState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(25.dp), strokeWidth = 3.5.dp
                    )
                }
                else -> {
                    DownloadBtn(permissionState, url, context, onChangeState)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun DownloadBtn(
    permissionState: PermissionState,
    url: String,
    context: Context,
    onChangeState: (loadState: LoadState<Unit>) -> Unit
) {
    IconButton(
        onClick = {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                if (permissionState.status.isGranted) {
                    downloadImage(url, context, onChangeState)
                } else {
                    permissionState.launchPermissionRequest()
                }
            } else {
                downloadImage(url, context, onChangeState)
            }
        }, modifier = Modifier.clip(RoundedCornerShape(50))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.download),
            contentDescription = "Download image",
        )
    }
}
