// HealthBox.kt
package com.omccolgan.requiemcharactersheet

import kotlinx.serialization.Serializable

@Serializable
data class HealthBox(
    val id: String = java.util.UUID.randomUUID().toString(),
    var damageType: HealthBoxFillType = HealthBoxFillType.NONE
)
