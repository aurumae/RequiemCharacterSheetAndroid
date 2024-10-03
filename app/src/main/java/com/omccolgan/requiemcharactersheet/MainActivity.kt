package com.omccolgan.requiemcharactersheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.omccolgan.requiemcharactersheet.ui.theme.RequiemCharacterSheetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RequiemCharacterSheetTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Background Image
                    Image(
                        painter = painterResource(id = R.drawable.vtr_border),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Content
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(50.dp))

                        // Logo Image
                        Image(
                            painter = painterResource(id = R.drawable.vtr_logo), // Replace with your logo image name
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .align(Alignment.CenterHorizontally),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Attributes List
                        val attributes = listOf(
                            Attribute(name = "Intelligence", rating = 3),
                            Attribute(name = "Wits", rating = 2),
                            Attribute(name = "Resolve", rating = 4),
                            Attribute(name = "Strength", rating = 3),
                            Attribute(name = "Dexterity", rating = 2),
                            Attribute(name = "Stamina", rating = 4),
                            Attribute(name = "Presence", rating = 3),
                            Attribute(name = "Manipulation", rating = 2),
                            Attribute(name = "Composure", rating = 4)
                            // Add more attributes as needed
                        )

                        // Use weight to make the list fill the remaining space
                        AttributesList(
                            attributes = attributes,
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 60.dp) // Adjust bottom padding as needed
                        )
                    }
                }
            }
        }
    }
}
