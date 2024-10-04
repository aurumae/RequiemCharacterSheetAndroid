// Bane.kt
package com.omccolgan.requiemcharactersheet

import kotlinx.serialization.Serializable

@Serializable
data class Bane(
    val id: String = java.util.UUID.randomUUID().toString(),
    var name: String = ""
)
