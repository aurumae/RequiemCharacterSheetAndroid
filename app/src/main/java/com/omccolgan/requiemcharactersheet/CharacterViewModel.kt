// CharacterViewModel.kt
package com.omccolgan.requiemcharactersheet

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Extension property for DataStore
private val Context.dataStore by preferencesDataStore(name = "character_prefs")

class CharacterViewModel(private val context: Context) : ViewModel() {
    private val _character = context.characterDataStore.data
        .stateIn(viewModelScope, SharingStarted.Eagerly, Character.defaultCharacter())

    val character: StateFlow<Character> = _character

    // Define keys for each CHARACTER field
    companion object {
        val NAME_KEY = stringPreferencesKey("name")
        val PLAYER_KEY = stringPreferencesKey("player")
        val CHRONICLE_KEY = stringPreferencesKey("chronicle")
        val MASK_KEY = stringPreferencesKey("mask")
        val DIRGE_KEY = stringPreferencesKey("dirge")
        val CONCEPT_KEY = stringPreferencesKey("concept")
        val CLAN_KEY = stringPreferencesKey("clan")
        val BLOODLINE_KEY = stringPreferencesKey("bloodline")
        val COVENANT_KEY = stringPreferencesKey("covenant")
    }

    // StateFlows for each CHARACTER field
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _player = MutableStateFlow("")
    val player: StateFlow<String> = _player.asStateFlow()

    private val _chronicle = MutableStateFlow("")
    val chronicle: StateFlow<String> = _chronicle.asStateFlow()

    private val _mask = MutableStateFlow("")
    val mask: StateFlow<String> = _mask.asStateFlow()

    private val _dirge = MutableStateFlow("")
    val dirge: StateFlow<String> = _dirge.asStateFlow()

    private val _concept = MutableStateFlow("")
    val concept: StateFlow<String> = _concept.asStateFlow()

    private val _clan = MutableStateFlow("")
    val clan: StateFlow<String> = _clan.asStateFlow()

    private val _bloodline = MutableStateFlow("")
    val bloodline: StateFlow<String> = _bloodline.asStateFlow()

    private val _covenant = MutableStateFlow("")
    val covenant: StateFlow<String> = _covenant.asStateFlow()


    init {
        // Load data from DataStore
        viewModelScope.launch {
            character.collect { character ->
                Log.d("CharacterViewModel", "Loaded character with attributes: ${character.attributes.map { it.name to it.rating }}")
            }
            context.dataStore.data.collect { preferences ->
                _name.value = preferences[NAME_KEY] ?: ""
                _player.value = preferences[PLAYER_KEY] ?: ""
                _chronicle.value = preferences[CHRONICLE_KEY] ?: ""
                _mask.value = preferences[MASK_KEY] ?: ""
                _dirge.value = preferences[DIRGE_KEY] ?: ""
                _concept.value = preferences[CONCEPT_KEY] ?: ""
                _clan.value = preferences[CLAN_KEY] ?: ""
                _bloodline.value = preferences[BLOODLINE_KEY] ?: ""
                _covenant.value = preferences[COVENANT_KEY] ?: ""
            }
        }
    }

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

    // Update functions that save to DataStore
    fun updateName(newName: String) {
        _name.value = newName
        saveToDataStore(NAME_KEY, newName)
    }

    fun updatePlayer(newPlayer: String) {
        _player.value = newPlayer
        saveToDataStore(PLAYER_KEY, newPlayer)
    }

    fun updateChronicle(newChronicle: String) {
        _chronicle.value = newChronicle
        saveToDataStore(CHRONICLE_KEY, newChronicle)
    }

    fun updateMask(newMask: String) {
        _mask.value = newMask
        saveToDataStore(MASK_KEY, newMask)
    }

    fun updateDirge(newDirge: String) {
        _dirge.value = newDirge
        saveToDataStore(DIRGE_KEY, newDirge)
    }

    fun updateConcept(newConcept: String) {
        _concept.value = newConcept
        saveToDataStore(CONCEPT_KEY, newConcept)
    }

    fun updateClan(newClan: String) {
        _clan.value = newClan
        saveToDataStore(CLAN_KEY, newClan)
    }

    fun updateBloodline(newBloodline: String) {
        _bloodline.value = newBloodline
        saveToDataStore(BLOODLINE_KEY, newBloodline)
    }

    fun updateCovenant(newCovenant: String) {
        _covenant.value = newCovenant
        saveToDataStore(COVENANT_KEY, newCovenant)
    }

    private fun saveToDataStore(key: Preferences.Key<String>, value: String) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[key] = value
            }
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

    fun updateHumanity(newHumanity: Int) {
        viewModelScope.launch {
            val updatedCharacter = _character.value.copy(humanity = newHumanity)
            context.characterDataStore.updateData { updatedCharacter }
        }
    }
}