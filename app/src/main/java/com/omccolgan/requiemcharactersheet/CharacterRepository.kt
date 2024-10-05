// CharacterRepository.kt
package com.omccolgan.requiemcharactersheet

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import android.util.Log

// Define the serializer
object CharacterSerializer : Serializer<Character> {
    override val defaultValue: Character = Character.defaultCharacter()

    override suspend fun readFrom(input: InputStream): Character {
        return try {
            val jsonString = input.readBytes().decodeToString()
            Log.d("CharacterSerializer", "Deserializing: $jsonString")
            Json.decodeFromString(
                Character.serializer(),
                jsonString
            )
        } catch (e: Exception) {
            Log.e("CharacterSerializer", "Error deserializing character", e)
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
