package com.omccolgan.requiemcharactersheet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// AttributesList.kt
@Composable
fun AttributesList(attributes: List<Attribute>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(attributes) { attribute ->
            AttributeRow(attribute)
        }
    }
}


@Composable
fun AttributeRow(attribute: Attribute) {
    var rating by remember { mutableStateOf(attribute.rating) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = attribute.name,
            fontFamily = CaslonAntique, // Custom font we'll define later
            fontSize = 22.sp,
            color = VampireRed, // Custom color we'll define later
            modifier = Modifier.weight(1f)
        )
        //RatingDots(rating = attribute.rating)
        RatingDots(rating = rating, onRatingChange = { newRating ->
            rating = newRating
        })
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



