package com.cosmos.app.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cosmos.app.R

val FiraCode = FontFamily(
    Font(R.font.firacode_bold, FontWeight.W700),
    Font(R.font.firacode_semibold, FontWeight.W600),
    Font(R.font.firacode_medium, FontWeight.W500),
    Font(R.font.firacode_regular, FontWeight.W400),
    Font(R.font.firacode_light, FontWeight.W300)
)

val Montserrat = FontFamily(
    Font(R.font.montserrat_semibold, FontWeight.W600),
    Font(R.font.montserrat_medium, FontWeight.W500),
    Font(R.font.montserrat_regular, FontWeight.W400),
)

val header = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.W600,
    fontSize = 22.sp
)

val title = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.W600,
    fontSize = 18.sp
)

val description = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp
)

val subTitle = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.W600,
    fontSize = 18.sp
)

val bottomBar = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.W600,
    fontSize = 12.sp
)

val boldSmall = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.W500,
    fontSize = 16.sp
)