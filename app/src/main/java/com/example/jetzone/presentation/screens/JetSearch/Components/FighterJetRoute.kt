package com.example.jetzone.presentation.screens.JetSearch.Components

sealed class FighterJetRoute(val route: String) {
    object SearchScreen : FighterJetRoute("search_screen")
    object JetDetailScreen : FighterJetRoute("detail_screen/{jetId}") {
        fun createRoute(jetId: Int) = "detail_screen/$jetId"
    }
}