// MainActivity.kt
package com.omccolgan.requiemcharactersheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.omccolgan.requiemcharactersheet.ui.theme.RequiemCharacterSheetTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RequiemCharacterSheetTheme {
                // Initialize ViewModel and collect state
                val viewModel: CharacterViewModel = viewModel(factory = CharacterViewModelFactory(this))
                val character by viewModel.character.collectAsState()

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
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 55.dp)
                            .padding(top = 55.dp)
                    ) {
                        item {
                            // Spacer(modifier = Modifier.height(50.dp))

                            // Logo Image
                            Image(
                                painter = painterResource(id = R.drawable.vtr_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                contentScale = ContentScale.Fit,
                                alignment = Alignment.Center
                            )

                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        item {
                            // Attributes List
                            AttributesList(
                                attributes = character.attributes,
                                onAttributeChange = { name, rating ->
                                    viewModel.updateAttribute(name, rating)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 60.dp)
                            )
                        }

                        item {
                            // Health Boxes
                            HealthBoxRow(
                                healthBoxes = character.healthBoxes,
                                onHealthBoxClick = { healthBoxId ->
                                    val currentDamageType = character.healthBoxes.find { it.id == healthBoxId }?.damageType ?: HealthBoxFillType.NONE
                                    val newDamageType = getNextDamageType(currentDamageType)
                                    viewModel.updateHealthBoxDamageType(healthBoxId, newDamageType)
                                }
                            )
                        }

                        item {
                            // Touchstones
                            val humanity = character.humanity

                            TouchstoneList(
                                touchstones = character.touchstones,
                                humanity = humanity,
                                onHumanityChange = { newHumanity ->
                                    viewModel.updateHumanity(newHumanity)
                                },
                                onTextChange = { touchstoneId, newText ->
                                    viewModel.updateTouchstoneText(touchstoneId, newText)
                                }
                            )

                        }

                        item {
                            Spacer(modifier = Modifier.height(50.dp)) // Bottom padding
                        }
                    }

                }
            }
        }
    }
}

// Damage type logic
fun getNextDamageType(current: HealthBoxFillType): HealthBoxFillType {
    return when (current) {
        HealthBoxFillType.NONE -> HealthBoxFillType.BASHING
        HealthBoxFillType.BASHING -> HealthBoxFillType.LETHAL
        HealthBoxFillType.LETHAL -> HealthBoxFillType.AGGRAVATED
        HealthBoxFillType.AGGRAVATED -> HealthBoxFillType.NONE
    }
}

