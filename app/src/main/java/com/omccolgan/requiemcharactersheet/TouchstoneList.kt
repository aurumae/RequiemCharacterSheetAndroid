// TouchstoneList.kt
package com.omccolgan.requiemcharactersheet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TouchstoneList(
    touchstones: List<Touchstone>,
    humanity: Int, // Add humanity parameter
    onHumanityChange: (Int) -> Unit, // Callback for humanity change
    onTextChange: (String, String) -> Unit
) {
    Column {
        touchstones.sortedByDescending { it.level }.forEach { touchstone ->
            TouchstoneRow(
                touchstone = touchstone,
                humanity = humanity,
                onHumanityChange = onHumanityChange,
                onTextChange = onTextChange
            )
        }
    }
}

@Composable
fun TouchstoneRow(
    touchstone: Touchstone,
    humanity: Int,
    onHumanityChange: (Int) -> Unit,
    onTextChange: (String, String) -> Unit
) {
    var textState by remember { mutableStateOf(touchstone.text) }
    var isEditing by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        // Level Number
        Text(
            text = "${touchstone.level}",
            fontFamily = CaslonAntique,
            fontSize = 22.sp,
            color = VampireRed,
            modifier = Modifier.width(30.dp)
        )

        // Touchstone Text or TextField
        if (isEditing) {
            TextField(
                value = textState,
                onValueChange = { newText ->
                    textState = newText
                    onTextChange(touchstone.id, newText)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    disabledPlaceholderColor = Color.Gray,
                    errorPlaceholderColor = Color.Gray
                ),
                placeholder = {
                    Text("Add Touchstone", color = Color.Gray.copy(alpha = 0.5f))
                },
                singleLine = true
            )
        } else {
            val textColor = Color.Gray

            Text(
                text = if (textState.isEmpty()) "Add Touchstone" else textState,
                color = textColor.copy(alpha = if (textState.isEmpty()) 0.5f else 1f),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clickable { isEditing = true },
                fontFamily = CaslonAntique,
                fontSize = 18.sp
            )
        }

        // Dot Rating
        Canvas(
            modifier = Modifier
                .size(20.dp)
                .padding(end = 8.dp)
            .clickable {
                onHumanityChange(touchstone.level)
            }
        ) {
        drawCircle(
            color = if (touchstone.level <= humanity) VampireRed else Color.Transparent,
            radius = size.minDimension / 2
        )
        drawCircle(
            color = Color.Black,
            radius = size.minDimension / 2,
            style = Stroke(width = 1.dp.toPx())
        )
    }
    }
}
