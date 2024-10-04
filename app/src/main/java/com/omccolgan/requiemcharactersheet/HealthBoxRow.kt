// HealthBoxRow.kt
package com.omccolgan.requiemcharactersheet

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable

@Composable
fun HealthBoxRow(healthBoxes: List<HealthBox>, onHealthBoxClick: (String) -> Unit) {
    Row {
        healthBoxes.forEach { healthBox ->
            HealthBoxItem(healthBox = healthBox, onClick = { onHealthBoxClick(healthBox.id) })
        }
    }
}

@Composable
fun HealthBoxItem(healthBox: HealthBox, onClick: () -> Unit) {
    // Display the health box based on healthBox.damageType
    // Use onClick to handle user interaction
}
