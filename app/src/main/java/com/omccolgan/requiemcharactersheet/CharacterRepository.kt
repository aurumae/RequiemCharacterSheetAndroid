// CharacterRepository.kt
package com.omccolgan.requiemcharactersheet

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream

// Define the serializer
object CharacterSerializer : androidx.datastore.core.Serializer<Character> {
    override val defaultValue: Character = Character.defaultCharacter()

    override suspend fun readFrom(input: InputStream): Character {
        return try {
            Json.decodeFromString(
                Character.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Character, output: OutputStream) {
        output.write(
            Json.encodeToString(Character.serializer(), t).encodeToByteArray()
        )
    }
}

// Extension to create the DataStore
val Context.characterDataStore: DataStore<Character> by dataStore(
    fileName = "character.json",
    serializer = CharacterSerializer
)
