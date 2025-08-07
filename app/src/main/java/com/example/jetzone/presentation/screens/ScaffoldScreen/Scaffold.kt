package com.example.jetzone.presentation.screens.ScaffoldScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetzone.presentation.screens.Jets3DModel.Model.JetModel
import com.example.jetzone.presentation.screens.ScaffoldScreen.Components.MyBottomBar
import com.example.jetzone.presentation.screens.ScaffoldScreen.Components.MyTopAppBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ScaffoldContainer(
    navController: NavHostController,
    isLoading: Boolean,
    jetModels: List<JetModel>,
    content: @Composable () -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("/") ?: "Home"

    val bottomBarRoutes =
        listOf("Home", "CompareJets", "FighterJetSearchScreen", "quiz", "aiJetFinder")
    var lastBottomBarRoute by rememberSaveable { mutableStateOf("Home") }

    if (currentRoute in bottomBarRoutes) {
        lastBottomBarRoute = currentRoute
    }

    Scaffold(
        topBar = {
            MyTopAppBar(navController = navController, jetModels = jetModels)
        },
        bottomBar = {
            MyBottomBar(
                navController = navController,
                currentSelectedRoute = lastBottomBarRoute
            )
        },
        contentWindowInsets = WindowInsets(0),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    end = innerPadding.calculateEndPadding(layoutDirection)
                )
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator()
                }
            } else {
                content()
            }
        }
    }
}