// MainActivity.kt
package com.omccolgan.requiemcharactersheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

                // Collect CHARACTER Section States
                val name by viewModel.name.collectAsState()
                val player by viewModel.player.collectAsState()
                val chronicle by viewModel.chronicle.collectAsState()
                val mask by viewModel.mask.collectAsState()
                val dirge by viewModel.dirge.collectAsState()
                val concept by viewModel.concept.collectAsState()
                val clan by viewModel.clan.collectAsState()
                val bloodline by viewModel.bloodline.collectAsState()
                val covenant by viewModel.covenant.collectAsState()

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

                        // CHARACTER Section
                        item {
                            // CHARACTER header
                            Text(
                                text = "CHARACTER",
                                fontFamily = CaslonAntique,
                                fontSize = 24.sp,
                                color = Color(0xFF85858A), // Same color as ATTRIBUTES header
                                lineHeight = 50.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .background(color = Color(0xFFF2F2F7))
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // CHARACTER Fields
                            CharacterSection(
                                name = name,
                                onNameChange = { viewModel.updateName(it) },
                                player = player,
                                onPlayerChange = { viewModel.updatePlayer(it) },
                                chronicle = chronicle,
                                onChronicleChange = { viewModel.updateChronicle(it) },
                                mask = mask,
                                onMaskChange = { viewModel.updateMask(it) },
                                dirge = dirge,
                                onDirgeChange = { viewModel.updateDirge(it) },
                                concept = concept,
                                onConceptChange = { viewModel.updateConcept(it) },
                                clan = clan,
                                onClanChange = { viewModel.updateClan(it) },
                                bloodline = bloodline,
                                onBloodlineChange = { viewModel.updateBloodline(it) },
                                covenant = covenant,
                                onCovenantChange = { viewModel.updateCovenant(it) }
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }


                        // ATTRIBUTES SECTION
                        item {
                            // Attributes header
                            Text(
                                text = "ATTRIBUTES",
                                fontFamily = CaslonAntique,
                                fontSize = 24.sp,
                                color = Color(0xFF85858A),
                                lineHeight = 50.sp,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 8.dp)
                                    .background(color = Color(0xFFF2F2F7))
                            )
                        }



                        // MENTAL Section
                        item {
                            // MENTAL header
                            Text(
                                text = "MENTAL",
                                fontFamily = CaslonAntique,
                                fontSize = 20.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )

                            // MENTAL Attributes List
                            AttributesList(
                                attributes = listOf(
                                    character.attributes.find { it.name == "Intelligence" },
                                    character.attributes.find { it.name == "Wits" },
                                    character.attributes.find { it.name == "Resolve" }
                                ).filterNotNull(),
                                onAttributeChange = { name, rating ->
                                    viewModel.updateAttribute(name, rating)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp)
                            )
                        }

                        // PHYSICAL Section
                        item {
                            // PHYSICAL header
                            Text(
                                text = "PHYSICAL",
                                fontFamily = CaslonAntique,
                                fontSize = 20.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )

                            // PHYSICAL Attributes List
                            AttributesList(
                                attributes = listOf(
                                    character.attributes.find { it.name == "Strength" },
                                    character.attributes.find { it.name == "Dexterity" },
                                    character.attributes.find { it.name == "Stamina" }
                                ).filterNotNull(),
                                onAttributeChange = { name, rating ->
                                    viewModel.updateAttribute(name, rating)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp)
                            )
                        }

                        // SOCIAL Section
                        item {
                            // SOCIAL header
                            Text(
                                text = "SOCIAL",
                                fontFamily = CaslonAntique,
                                fontSize = 20.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )

                            // SOCIAL Attributes List
                            AttributesList(
                                attributes = listOf(
                                    character.attributes.find { it.name == "Presence" },
                                    character.attributes.find { it.name == "Manipulation" },
                                    character.attributes.find { it.name == "Composure" }
                                ).filterNotNull(),
                                onAttributeChange = { name, rating ->
                                    viewModel.updateAttribute(name, rating)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 60.dp)
                            )
                        }


                        // Health Boxes, Touchstones, etc.
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

// In MainActivity.kt or a separate file as per your project structure

@Composable
fun CharacterSection(
    name: String,
    onNameChange: (String) -> Unit,
    player: String,
    onPlayerChange: (String) -> Unit,
    chronicle: String,
    onChronicleChange: (String) -> Unit,
    mask: String,
    onMaskChange: (String) -> Unit,
    dirge: String,
    onDirgeChange: (String) -> Unit,
    concept: String,
    onConceptChange: (String) -> Unit,
    clan: String,
    onClanChange: (String) -> Unit,
    bloodline: String,
    onBloodlineChange: (String) -> Unit,
    covenant: String,
    onCovenantChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        EditableCharacterField(
            label = "Name:",
            value = name,
            onValueChange = onNameChange
        )
        EditableCharacterField(
            label = "Player:",
            value = player,
            onValueChange = onPlayerChange
        )
        EditableCharacterField(
            label = "Chronicle:",
            value = chronicle,
            onValueChange = onChronicleChange
        )
        EditableCharacterField(
            label = "Mask:",
            value = mask,
            onValueChange = onMaskChange
        )
        EditableCharacterField(
            label = "Dirge:",
            value = dirge,
            onValueChange = onDirgeChange
        )
        EditableCharacterField(
            label = "Concept:",
            value = concept,
            onValueChange = onConceptChange
        )
        EditableCharacterField(
            label = "Clan:",
            value = clan,
            onValueChange = onClanChange
        )
        EditableCharacterField(
            label = "Bloodline:",
            value = bloodline,
            onValueChange = onBloodlineChange
        )
        EditableCharacterField(
            label = "Covenant:",
            value = covenant,
            onValueChange = onCovenantChange
        )
    }
}