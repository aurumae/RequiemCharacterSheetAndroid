// Skill.kt
package com.omccolgan.requiemcharactersheet

import kotlinx.serialization.Serializable

@Serializable
data class Skill(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    var rating: Int = 0,
    var specialty: String = ""
)
