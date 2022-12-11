package com.cosmos.app.screen.apod.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cosmos.app.screen.apod.model.ApodModel
import com.cosmos.app.ui.theme.description
import com.cosmos.app.ui.theme.subTitle

@Composable
fun DescriptionAndCopyright(apodData: ApodModel?) {
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