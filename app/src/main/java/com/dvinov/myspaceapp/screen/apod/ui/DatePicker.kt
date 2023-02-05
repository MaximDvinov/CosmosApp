package com.dvinov.myspaceapp.screen.apod.ui

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
import com.dvinov.myspaceapp.R
import com.dvinov.myspaceapp.ui.theme.card_bg
import com.dvinov.myspaceapp.ui.theme.primary
import com.dvinov.myspaceapp.ui.theme.subTitle
import java.time.LocalDate

private const val TAG = "DatePicker"

@Composable
fun DatePicker(
    showDialog: Boolean,
    onChangeShowState: (Boolean) -> Unit,
    onDateSelected: () -> Unit,
    selectedDate: LocalDate? = null,
    onChangeDateState: (date: LocalDate) -> Unit
) {
    var dateState by remember {
        mutableStateOf(selectedDate ?: LocalDate.now())
    }

    Log.i(TAG, "DatePicker: $selectedDate")

    if (showDialog) {
        Dialog(onDismissRequest = remember { { onChangeShowState(false) } }) {
            Column(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(card_bg)
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WheelDatePicker(
                    modifier = Modifier.padding(16.dp),
                    startDate = dateState,
                    minDate = LocalDate.of(1995, 7, 1),
                    maxDate = LocalDate.now(),
                    yearsRange = IntRange(1995, LocalDate.now().year),
                    size = DpSize(220.dp, 150.dp),
                    textStyle = subTitle,
                    textColor = Color.White,
                    selectorProperties = WheelPickerDefaults.selectorProperties(
                        enabled = true,
                        shape = RoundedCornerShape(16.dp),
                        color = primary.copy(alpha = 0.1f),
                        border = BorderStroke(2.dp, primary)
                    ),
                    onSnappedDate = remember {
                        { snappedDate ->
                            dateState = snappedDate
                            onChangeDateState(snappedDate)
                            Log.d(TAG, "DatePicker() called with: snappedDate = $snappedDate")
                        }
                    }
                )

                PrimaryBtn(
                    modifier = Modifier
                        .fillMaxWidth(), text = stringResource(R.string.select),
                    onClick = remember {
                        {
                            onDateSelected()
                            onChangeShowState(false)
                        }
                    }
                )
            }
        }
    }
}