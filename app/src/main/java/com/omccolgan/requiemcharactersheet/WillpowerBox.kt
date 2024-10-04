// WillpowerBox.kt
package com.omccolgan.requiemcharactersheet

import kotlinx.serialization.Serializable

@Serializable
data class WillpowerBox(
    val id: String = java.util.UUID.randomUUID().toString(),
    var state: WillpowerBoxFillType = WillpowerBoxFillType.NONE
)
