// Attribute.kt
package com.omccolgan.requiemcharactersheet

import kotlinx.serialization.Serializable

@Serializable
data class Attribute(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    var rating: Int
)
