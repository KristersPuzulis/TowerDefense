// Color.kt
// Šis fails satur visas spēlē izmantotās krāsas – gan tēmas noklusējuma krāsas, gan pielāgotas vizuālajām daļām

package com.example.towerdefense.ui.theme

import androidx.compose.ui.graphics.Color

// Noklusējuma krāsas
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Pielāgotas spēles vizuālās krāsas
val GrassColor = Color(0xFFB9FBC0) // Spēles pamata fons – zāle
val PathColor = Color(0xFFDAB785) // Ceļš, pa kuru pārvietojas pretinieki
val TowerColor = Color.DarkGray // Torņu krāsa, pirms tika ievietots attēls priekš torņa
val HPBarRed = Color.Red // Dzīvības joslas sarkanā daļa 
val HPBarGreen = Color.Green // Dzīvības joslas zaļā daļa
