package com.cosmos.app.screen.apod.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.cosmos.app.ui.theme.card_bg
import com.cosmos.app.ui.theme.primary
import com.cosmos.app.ui.theme.subTitle
import com.cosmos.app.R
import java.time.LocalDate

@Composable
fun DatePicker(
    showDialog: Boolean, onChangeState: (Boolean) -> Unit, onDateSelected: (date: LocalDate) -> Unit
) {
    var dateState by remember {
        mutableStateOf(LocalDate.now())
    }

    if (showDialog) {
        Dialog(onDismissRequest = { onChangeState(false) }) {
            Column(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(card_bg)
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WheelDatePicker(
                    modifier = Modifier.padding(16.dp),
                    startDate = LocalDate.now(),
                    yearsRange = IntRange(1996, LocalDate.now().year),
                    size = DpSize(220.dp, 150.dp),
                    textStyle = subTitle,
                    textColor = Color.White,
                    selectorProperties = WheelPickerDefaults.selectorProperties(
                        enabled = true,
                        shape = RoundedCornerShape(16.dp),
                        color = primary.copy(alpha = 0.1f),
                        border = BorderStroke(2.dp, primary)
                    )
                ) { snappedDate ->
                        dateState = snappedDate
                }

                PrimaryBtn(
                    modifier = Modifier
                        .fillMaxWidth(), text = stringResource(R.string.select)
                ) {
                    onDateSelected(dateState)
                    Log.e("time", dateState.toString())
                    onChangeState(false)
                }
            }
        }
    }
}