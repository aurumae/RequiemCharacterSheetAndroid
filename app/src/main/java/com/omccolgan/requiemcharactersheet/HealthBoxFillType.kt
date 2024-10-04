// HealthBoxFillType.kt
package com.omccolgan.requiemcharactersheet

import kotlinx.serialization.Serializable

@Serializable
enum class HealthBoxFillType {
    NONE,
    BASHING,
    LETHAL,
    AGGRAVATED
}
