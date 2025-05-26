// Theme.kt
// Šis fails definē lietotnes vizuālo tēmu – krāsu shēmu, kas var būt gaiša, tumša vai dinamiski pielāgota sistēmas iestatījumiem.

package com.example.towerdefense.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Tumšās tēmas krāsu shēma
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// Gaišās tēmas krāsu shēma
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun TowerDefenseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Ja atbalstīts, izmanto Android dinamiskās krāsas
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        // Pretējā gadījumā izmanto statiskās krāsu shēmas
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Pielieto izvēlēto krāsu shēmu un tipogrāfiju visai lietotnei
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
