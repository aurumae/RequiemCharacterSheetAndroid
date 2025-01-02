// EditableCharacterField.kt
package com.omccolgan.requiemcharactersheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditableCharacterField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(value) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Label
        Text(
            text = label,
            fontSize = 16.sp,
            color = VampireRed,
            modifier = Modifier.width(100.dp) // Fixed width for labels
        )
        Spacer(modifier = Modifier.width(16.dp)) // Padding between label and field

        // Editable Field
        if (isEditing) {
            TextField(
                value = text,
                onValueChange = { text = it },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onValueChange(text)
                        isEditing = false
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        } else {
            Text(
                text = if (value.isEmpty()) "Tap to edit" else value,
                fontSize = 16.sp,
                color = if (value.isEmpty()) Color.LightGray else Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isEditing = true }
                    .padding(vertical = 8.dp)
            )
        }
    }
}