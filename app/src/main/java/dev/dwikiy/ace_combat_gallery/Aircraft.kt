// In Aircraft.kt
package dev.dwikiy.ace_combat_gallery

import androidx.annotation.DrawableRes

data class Aircraft(
    val name: String,
    val nickname: String,
    @DrawableRes val imageRes: Int,
    val description: String
)
