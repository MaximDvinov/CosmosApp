package com.dvinov.myspaceapp

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.palette.graphics.Palette
import com.dvinov.myspaceapp.ui.theme.blue_bg
import com.skydoves.landscapist.glide.GlideImageState
import java.util.regex.Matcher
import java.util.regex.Pattern

fun getYoutubeId(url: String?): String? {
    val pattern =
        "https?://(?:[0-9A-Z-]+\\.)?(?:youtu\\.be/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|</a>))[?=&+%\\w]*"
    val compiledPattern: Pattern = Pattern.compile(
        pattern,
        Pattern.CASE_INSENSITIVE
    )
    val matcher: Matcher = compiledPattern.matcher(url.toString())
    return if (matcher.find()) {
        matcher.group(1)
    } else null /*from w  w  w.  j a  va  2 s .c om*/
}

fun getDominantColor(state: GlideImageState) =
    when (state) {
        is GlideImageState.Failure -> blue_bg
        is GlideImageState.Loading -> blue_bg
        is GlideImageState.None -> blue_bg
        is GlideImageState.Success -> {
            if (state.imageBitmap != null) {
                val tmpColor = Palette.from(state.imageBitmap!!.asAndroidBitmap())
                    .generate().dominantSwatch?.rgb
                if (tmpColor != null) Color(
                    tmpColor
                ) else blue_bg
            } else blue_bg
        }
    }