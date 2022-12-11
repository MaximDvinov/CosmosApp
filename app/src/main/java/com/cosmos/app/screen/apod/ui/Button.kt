package com.cosmos.app.screen.apod.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.cosmos.app.ui.theme.card_bg
import com.cosmos.app.ui.theme.primary
import com.cosmos.app.ui.theme.title
import com.cosmos.app.ui.theme.white

@Composable
fun PrimaryBtn(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .shadow(
                10.dp,
                shape = RoundedCornerShape(15.dp),
                ambientColor = primary,
                spotColor = primary
            ),
    ) {
        Text(
            text = text,
            style = title,
            color = white,
            modifier = Modifier
                .padding(vertical = 8.dp),
        )
    }
}

@Composable
fun SecondaryBtn(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .shadow(
                10.dp,
                shape = RoundedCornerShape(15.dp),
                ambientColor = card_bg,
                spotColor = card_bg
            ),
        colors = ButtonDefaults.buttonColors(backgroundColor = card_bg)
    ) {
        Text(
            text = text,
            style = title,
            color = white,
            modifier = Modifier
                .padding(vertical = 8.dp),
        )
    }
}