// MainActivity.kt
package com.omccolgan.requiemcharactersheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(50.dp))

                        // Logo Image
                        Image(
                            painter = painterResource(id = R.drawable.vtr_logo),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .align(Alignment.CenterHorizontally),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(20.dp))

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


                        // Health Boxes
                        HealthBoxRow(
                            healthBoxes = character.healthBoxes,
                            onHealthBoxClick = { healthBoxId ->
                                val currentDamageType = character.healthBoxes.find { it.id == healthBoxId }?.damageType ?: HealthBoxFillType.NONE
                                val newDamageType = getNextDamageType(currentDamageType)
                                viewModel.updateHealthBoxDamageType(healthBoxId, newDamageType)
                            }
                        )

                        // Touchstones
                        TouchstoneList(
                            touchstones = character.touchstones,
                            onTextChange = { touchstoneId, newText ->
                                viewModel.updateTouchstoneText(touchstoneId, newText)
                            }
                        )
                    }
                }
            }
        }
    }
}

// Move this function outside of MainActivity
fun getNextDamageType(current: HealthBoxFillType): HealthBoxFillType {
    return when (current) {
        HealthBoxFillType.NONE -> HealthBoxFillType.BASHING
        HealthBoxFillType.BASHING -> HealthBoxFillType.LETHAL
        HealthBoxFillType.LETHAL -> HealthBoxFillType.AGGRAVATED
        HealthBoxFillType.AGGRAVATED -> HealthBoxFillType.NONE
    }
}

