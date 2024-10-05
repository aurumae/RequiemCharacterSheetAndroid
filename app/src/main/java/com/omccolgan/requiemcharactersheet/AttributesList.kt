// AttributesList.kt
package com.omccolgan.requiemcharactersheet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AttributesList(
    attributes: List<Attribute>,
    onAttributeChange: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        attributes.forEach { attribute ->
            AttributeRow(
                attribute = attribute,
                onRatingChange = { newRating ->
                    onAttributeChange(attribute.name, newRating)
                }
            )
        }
    }
}


// AttributeRow.kt
// Should really pull this out into a separate file...
@Composable
fun AttributeRow(attribute: Attribute, onRatingChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = attribute.name,
            fontFamily = CaslonAntique, // Custom font defined elsewhere
            fontSize = 22.sp,
            color = VampireRed,
            modifier = Modifier.weight(1f)
        )
        RatingDots(
            rating = attribute.rating,
            onRatingChange = { newRating ->
                onRatingChange(newRating)
            }
        )
    }
}

@Composable
fun RatingDots(rating: Int, onRatingChange: (Int) -> Unit, maxRating: Int = 5) {
    Row {
        for (i in 1..maxRating) {
            val filled = i <= rating
            Canvas(
                modifier = Modifier
                    .size(20.dp)
                    .padding(2.dp)
                    .clickable { onRatingChange(i) }
            ) {
                // Draw the filled circle (or transparent if not filled)
                drawCircle(
                    color = if (filled) VampireRed else Color.Transparent,
                    radius = size.minDimension / 2
                )
                // Draw the circle outline
                drawCircle(
                    color = Color.Black,
                    radius = size.minDimension / 2,
                    style = Stroke(width = 1.dp.toPx())
                )
            }
        }
    }
}