// Character.kt
package com.omccolgan.requiemcharactersheet

import android.util.Log
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    var attributes: List<Attribute>,
    var healthBoxes: List<HealthBox>,
    var willpowerBoxes: List<WillpowerBox>,
    var skills: List<Skill>,
    var name: String = "",
    var player: String = "",
    var chronicle: String = "",
    var mask: String = "",
    var dirge: String = "",
    var concept: String = "",
    var clan: String = "",
    var bloodline: String = "",
    var covenant: String = "",
    var bloodPotency: Int = 1,
    var maxVitae: Int = 0,
    var vitaePerTurn: Int = 1,
    var currentVitae: Int = 0,
    var humanity: Int = 7,
    var touchstones: List<Touchstone>,
    var disciplines: List<Discipline>,
    var merits: List<Merit>,
    var aspirations: List<Aspiration>,
    var banes: List<Bane>,
    var size: Int = 5,
    var armor: Int = 0,
    var beats: Int = 0,
    var experiences: Int = 0
) {
    // Derived attributes
    val health: Int
        get() = adjustedStamina + size

    val willpower: Int
        get() = resolve + composure

    // Helper properties
    val resolve: Int
        get() = attributes.firstOrNull { it.name == "Resolve" }?.rating ?: 0

    val composure: Int
        get() = attributes.firstOrNull { it.name == "Composure" }?.rating ?: 0

    val adjustedStamina: Int
        get() {
            val baseStamina = attributes.firstOrNull { it.name == "Stamina" }?.rating ?: 0
            val resilienceRating =
                disciplines.firstOrNull { it.name.equals("Resilience", true) }?.rating ?: 0
            return baseStamina + resilienceRating
        }

    companion object {
        fun defaultCharacter(): Character {
            // Initialize attributes
            val attributes = listOf(
                Attribute(name = "Intelligence", rating = 1),
                Attribute(name = "Wits", rating = 1),
                Attribute(name = "Resolve", rating = 1),
                Attribute(name = "Strength", rating = 1),
                Attribute(name = "Dexterity", rating = 1),
                Attribute(name = "Stamina", rating = 1),
                Attribute(name = "Presence", rating = 1),
                Attribute(name = "Manipulation", rating = 1),
                Attribute(name = "Composure", rating = 1)
            )

            Log.d("Character", "Default attributes initialized: ${attributes.map { it.name to it.rating }}")


            // Compute initial health
            val stamina = attributes.firstOrNull { it.name == "Stamina" }?.rating ?: 0
            val size = 5
            val totalHealth = stamina + size
            val healthBoxes = List(totalHealth) { HealthBox() }

            // Compute initial willpower
            val resolve = attributes.firstOrNull { it.name == "Resolve" }?.rating ?: 0
            val composure = attributes.firstOrNull { it.name == "Composure" }?.rating ?: 0
            val totalWillpower = resolve + composure
            val willpowerBoxes = List(totalWillpower) { WillpowerBox() }

            // Initialize touchstones
            val touchstones = (1..10).map { Touchstone(level = it) }

            // Initialize skills, disciplines, merits, etc.
            val disciplines = List(1) { Discipline() }
            val merits = List(1) { Merit() }
            val aspirations = List(1) { Aspiration() }
            val banes = List(1) { Bane() }

            return Character(
                attributes = attributes,
                healthBoxes = healthBoxes,
                willpowerBoxes = willpowerBoxes,
                skills = listOf(), // Initialize as needed
                touchstones = touchstones,
                disciplines = disciplines,
                merits = merits,
                aspirations = aspirations,
                banes = banes
            )
        }
        // Other derived attributes...
    }
}
