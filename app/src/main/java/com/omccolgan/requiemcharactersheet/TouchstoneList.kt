// TouchstoneList.kt
package com.omccolgan.requiemcharactersheet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures

@Composable
fun TouchstoneList(
    touchstones: List<Touchstone>,
    humanity: Int,
    onHumanityChange: (Int) -> Unit,
    onTextChange: (String, String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Column {
            // Header Text
            Text(
                text = "HUMANITY",
                fontFamily = CaslonAntique,
                fontSize = 24.sp,
                color = VampireRed,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )

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
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

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
            modifier = Modifier
                .width(30.dp)
                .clickable{ isEditing = false }
        )

        // Touchstone Text or TextField
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            if (isEditing) {
                TextField(
                    value = textState,
                    onValueChange = { newText ->
                        textState = newText
                        onTextChange(touchstone.id, newText)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        disabledTextColor = Color.Black,
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

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            } else {
                val textColor = Color.Gray

                Text(
                    text = if (textState.isEmpty()) "Add Touchstone" else textState,
                    color = textColor.copy(alpha = if (textState.isEmpty()) 0.5f else 1f),
                    textDecoration = if ((touchstone.level > humanity) && !textState.isEmpty()) {
                        TextDecoration.LineThrough
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isEditing = false
                            focusManager.clearFocus()
                            isEditing = true
                                   },
                    fontFamily = CaslonAntique,
                    fontSize = 18.sp
                )
            }
        }

        // Dot Rating
        Canvas(
            modifier = Modifier
                .size(20.dp)
                .padding(end = 8.dp)
                .clickable {
                    onHumanityChange(touchstone.level)
                    //focusManager.clearFocus()
                    isEditing = false
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