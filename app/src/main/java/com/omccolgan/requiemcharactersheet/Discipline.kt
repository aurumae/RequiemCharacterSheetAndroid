// Discipline.kt
package com.omccolgan.requiemcharactersheet

import kotlinx.serialization.Serializable

@Serializable
data class Discipline(
    val id: String = java.util.UUID.randomUUID().toString(),
    var name: String = "",
    var rating: Int = 0
)
