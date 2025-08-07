package com.example.jetzone.presentation.screens.JetSearch.Components


data class FighterJetDetails(
    val id: Int,
    val modelName: String,
    val imageRes: Int,
    val fullName: String,
    val topSpeed: String,
    val generation: String,
    val missiles: String,
    val manufacturerCountry: String,
    val countryFlagRes: Int,
    val firstFlight: String,
    val height: String,
    val wingspan: String,
    val range: String,
    val threeDModelRoute: String? = null,
)
