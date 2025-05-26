// MainActivity.kt
// Šis fails satur galveno aktivitāti, kas uzsāk spēli.

package com.example.towerdefense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.towerdefense.ui.theme.TowerDefenseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ļauj zīmēt saturu zem statusa joslām
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Iestata spēles saturu un dizaina tēmu
        setContent {
            TowerDefenseTheme {
                AppNavigation() // Uzsāk spēles navigāciju starp ekrāniem
            }
        }
    }
}
