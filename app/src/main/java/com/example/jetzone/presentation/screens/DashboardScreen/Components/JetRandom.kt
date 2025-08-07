package com.example.jetzone.presentation.screens.DashboardScreen.Components

import com.example.jetzone.R
import java.time.LocalDate

data class JetData(
    val name: String,
    val imageRes: Int,
    val speedMph: Int,
    val generation: String,
    val progress: Float
)

val JetsList = listOf(
    JetData("Su-57", R.drawable.su57, 2600, "5th Gen", 90f),
    JetData("J-20", R.drawable.j20, 2100, "5th Gen", 90f),
    JetData("Su-35", R.drawable.su35, 2400, "4++ Gen", 92f),
    JetData("Su-37", R.drawable.su37, 2500, "4++ Gen", 91f),
    JetData("F-16", R.drawable.f16, 2400, "4th Gen", 85f),
    JetData("F-22", R.drawable.f22, 2410, "5th Gen", 95f),
    JetData("F-35", R.drawable.f35, 1930, "5th Gen", 89f),
    JetData("Typhoon", R.drawable.typhoon, 2495, "4.5 Gen", 88f),
    JetData("Rafale", R.drawable.rafale, 1912, "4.5 Gen", 86f),
    JetData("JAS 39", R.drawable.jas39, 2470, "4th Gen", 80f),
    JetData("MiG-35", R.drawable.mig35, 2400, "4++ Gen", 84f),
)

fun getJetOfTheDay(): JetData {
    val dayOfYear = LocalDate.now().dayOfYear
    return JetsList[dayOfYear % JetsList.size]
}