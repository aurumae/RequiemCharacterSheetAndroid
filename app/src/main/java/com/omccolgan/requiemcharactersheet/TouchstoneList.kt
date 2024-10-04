// TouchstoneList.kt
package com.omccolgan.requiemcharactersheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun TouchstoneList(touchstones: List<Touchstone>, onTextChange: (String, String) -> Unit) {
    Column {
        touchstones.forEach { touchstone ->
            var textState by remember { mutableStateOf(touchstone.text) }

            TextField(
                value = textState, // Changed ':' to '='
                onValueChange = { newText: String ->
                    textState = newText
                    onTextChange(touchstone.id, newText)
                },
                label = { Text("Touchstone Level ${touchstone.level}") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/*
package com.omccolgan.requiemcharactersheet

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun TouchstoneList(touchstones: List<Touchstone>, onTextChange: (String, String) -> Unit) {
    Column {
        touchstones.forEach { touchstone ->
            TextField(
                value = touchstone.text,
            onValueChange = { newText -> onTextChange(touchstone.id, newText) },
            label = { Text("Touchstone Level ${touchstone.level}") }
            )
        }
    }
}
*/