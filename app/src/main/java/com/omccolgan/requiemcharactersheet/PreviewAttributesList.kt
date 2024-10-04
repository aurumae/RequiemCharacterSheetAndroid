// PreviewAttributesList.kt
package com.omccolgan.requiemcharactersheet

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

// PreviewAttributesList.kt
@Preview(showBackground = true)
@Composable
fun PreviewAttributesList() {
    val sampleAttributes = listOf(
        Attribute(name = "Intelligence", rating = 3),
        Attribute(name = "Wits", rating = 2),
        Attribute(name = "Resolve", rating = 4),
        Attribute(name = "Strength", rating = 5),
        Attribute(name = "Dexterity", rating = 3),
        Attribute(name = "Stamina", rating = 2),
        Attribute(name = "Presence", rating = 4),
        Attribute(name = "Manipulation", rating = 2),
        Attribute(name = "Composure", rating = 5)
    )
    AttributesList(
        attributes = sampleAttributes,
        onAttributeChange = { _, _ -> } // Added this line
    )
}

