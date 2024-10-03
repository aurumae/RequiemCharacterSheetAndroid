package com.omccolgan.requiemcharactersheet

import java.util.UUID

// Attribute.kt
data class Attribute(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    var rating: Int
)
