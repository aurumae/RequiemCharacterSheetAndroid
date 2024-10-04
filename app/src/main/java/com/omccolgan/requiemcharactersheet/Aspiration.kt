// Aspiration.kt
package com.omccolgan.requiemcharactersheet

import kotlinx.serialization.Serializable

@Serializable
data class Aspiration(
    val id: String = java.util.UUID.randomUUID().toString(),
    var name: String = ""
)
