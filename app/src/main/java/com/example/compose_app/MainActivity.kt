package com.example.compose_app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)

            var dateText by remember {
                mutableStateOf("$day / $month / $year")
            }
            var timeText by remember {
                mutableStateOf("$hour:$minute")
            }

            val datePickerDialog = DatePickerDialog(
                this, { _: DatePicker, year: Int, month: Int, day: Int ->
                    dateText = "$day / $month / $year"
                }, year, month, day
            )

            val timePickerDialog = TimePickerDialog(
                this, { _: TimePicker, hour: Int, minute: Int ->
                    timeText = "$hour:$minute"
                }, hour, minute , false
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),

                ) {
                Text(
                    text = dateText + "  ,  " + timeText, style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ), modifier = Modifier
                        .align(Alignment.Center)
                        .padding(0.dp, 0.dp, 0.dp, 90.dp)
                )
                Button(
                    onClick = {
                        timePickerDialog.show()
                        datePickerDialog.show()
                    }, modifier = Modifier
                        .align(Alignment.Center)
                        .padding(0.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Text(
                        text = "change time ", style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    )
                }

                Button(
                    onClick = {
                        createNotification(this@MainActivity)
                    }, modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(0.dp, 0.dp, 0.dp, 250.dp)
                ) {
                    Text(
                        text = "create notification", style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    )
                }

            }
        }
    }

    private fun createNotification(context: Context) {
        // TODO("Not yet implemented")
    }

}