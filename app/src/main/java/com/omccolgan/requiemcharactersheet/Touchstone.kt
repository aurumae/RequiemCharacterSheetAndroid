// Touchstone.kt
package com.omccolgan.requiemcharactersheet

import kotlinx.serialization.Serializable

@Serializable
data class Touchstone(
    val id: String = java.util.UUID.randomUUID().toString(),
    val level: Int,
    var text: String = ""
)
