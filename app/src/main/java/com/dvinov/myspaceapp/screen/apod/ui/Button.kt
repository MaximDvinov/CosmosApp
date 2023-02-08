package com.dvinov.myspaceapp.screen.apod.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dvinov.myspaceapp.ui.theme.card_bg
import com.dvinov.myspaceapp.ui.theme.primary
import com.dvinov.myspaceapp.ui.theme.title
import com.dvinov.myspaceapp.ui.theme.white


@Composable
fun ColoredBtn(modifier: Modifier = Modifier, color: Color, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        elevation = ButtonDefaults.elevation(0.dp, pressedElevation = 0.dp, hoveredElevation = 0.dp, focusedElevation = 0.dp)
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