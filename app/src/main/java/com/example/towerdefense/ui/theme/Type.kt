// Type.kt
// Šis fails definē noklusēto teksta stilu, ko izmanto lietotnes dizaina tēma.

package com.example.towerdefense.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Noklusētais teksta stils lieliem tekstiem
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default, // Noklusētā fontu ģimene
        fontWeight = FontWeight.Normal, // Fonta biezums
        fontSize = 16.sp, // Fonta izmērs
        lineHeight = 24.sp, // Attālums starp rindām
        letterSpacing = 0.5.sp // Burtu atstarpe
    )
)
