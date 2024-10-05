// CharacterViewModel.kt
package com.omccolgan.requiemcharactersheet

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class CharacterViewModel(private val context: Context) : ViewModel() {
    private val _character = context.characterDataStore.data
        .stateIn(viewModelScope, SharingStarted.Eagerly, Character.defaultCharacter())

    val character: StateFlow<Character> = _character

    init {
        viewModelScope.launch {
            character.collect { character ->
                Log.d("CharacterViewModel", "Loaded character with attributes: ${character.attributes.map { it.name to it.rating }}")
            }
        }
    }

    /*
    fun updateCharacter(newCharacter: Character) {
        viewModelScope.launch {
            context.characterDataStore.updateData { newCharacter }
        }
    }
     */

    // Update functions
    fun updateAttribute(name: String, rating: Int) {
        viewModelScope.launch {
            val updatedAttributes = _character.value.attributes.map {
                if (it.name == name) it.copy(rating = rating) else it
            }
            val updatedCharacter = _character.value.copy(attributes = updatedAttributes)
                .recalculateHealthAndWillpower()
            context.characterDataStore.updateData { updatedCharacter }
        }
    }

    private fun Character.recalculateHealthAndWillpower(): Character {
        val stamina = attributes.firstOrNull { it.name == "Stamina" }?.rating ?: 0
        val size = this.size
        val totalHealth = stamina + size

        val newHealthBoxes = List(totalHealth) { index ->
            if (index < healthBoxes.size) healthBoxes[index]
            else HealthBox()
        }

        val resolve = attributes.firstOrNull { it.name == "Resolve" }?.rating ?: 0
        val composure = attributes.firstOrNull { it.name == "Composure" }?.rating ?: 0
        val totalWillpower = resolve + composure

        val newWillpowerBoxes = List(totalWillpower) { index ->
            if (index < willpowerBoxes.size) willpowerBoxes[index]
            else WillpowerBox()
        }

        return this.copy(
            healthBoxes = newHealthBoxes,
            willpowerBoxes = newWillpowerBoxes
        )
    }

    fun updateHealthBoxDamageType(healthBoxId: String, newDamageType: HealthBoxFillType) {
        viewModelScope.launch {
            val updatedHealthBoxes = _character.value.healthBoxes.map {
                if (it.id == healthBoxId) it.copy(damageType = newDamageType) else it
            }
            val updatedCharacter = _character.value.copy(healthBoxes = updatedHealthBoxes)
            context.characterDataStore.updateData { updatedCharacter }
        }
    }

    fun updateWillpowerBoxState(willpowerBoxId: String, newState: WillpowerBoxFillType) {
        viewModelScope.launch {
            val updatedWillpowerBoxes = _character.value.willpowerBoxes.map {
                if (it.id == willpowerBoxId) it.copy(state = newState) else it
            }
            val updatedCharacter = _character.value.copy(willpowerBoxes = updatedWillpowerBoxes)
            context.characterDataStore.updateData { updatedCharacter }
        }
    }

    fun updateTouchstoneText(touchstoneId: String, newText: String) {
        viewModelScope.launch {
            val updatedTouchstones = _character.value.touchstones.map {
                if (it.id == touchstoneId) it.copy(text = newText) else it
            }
            val updatedCharacter = _character.value.copy(touchstones = updatedTouchstones)
            context.characterDataStore.updateData { updatedCharacter }
        }
    }

    // Other update functions...
}

