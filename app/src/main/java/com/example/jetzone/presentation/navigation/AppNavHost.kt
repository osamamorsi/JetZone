package com.example.jetzone.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.jetzone.presentation.screens.AiJetIdentifier.JetIdentifierScreen
import com.example.jetzone.presentation.screens.CompareJetsScreen.CompareJetsScreen
import com.example.jetzone.presentation.screens.DashboardScreen.DashboardScreen
import com.example.jetzone.presentation.screens.DashboardScreen.Components.AirForceDetailScreen
import com.example.jetzone.presentation.screens.JetSearch.Components.FighterJetDetailScreen
import com.example.jetzone.presentation.screens.JetSearch.Components.FighterJetRoute
import com.example.jetzone.presentation.screens.JetSearch.Components.getAllFighterJets
import com.example.jetzone.presentation.screens.JetSearch.FighterJetSearchScreen
import com.example.jetzone.presentation.screens.JetSounds.SoundPlayerScreen
import com.example.jetzone.presentation.screens.Jets3DModel.JetSelectionScreen
import com.example.jetzone.presentation.screens.Jets3DModel.Model.JetModelViewerScreen
import com.example.jetzone.presentation.screens.Jets3DModel.Model.jetModels
import com.example.jetzone.presentation.screens.QuizScreen.ProgressScreen
import com.example.jetzone.presentation.screens.QuizScreen.QuizScreen
import com.example.jetzone.presentation.screens.ScaffoldScreen.ScaffoldContainer
import com.example.jetzone.presentation.viewModels.quizViewmodel.QuizViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route ?: "Home"
    var isLoading by remember { mutableStateOf(true) }
    val quizViewModel: QuizViewModel = viewModel()

    LaunchedEffect(route) {
        isLoading = true
        delay(400)
        isLoading = false
    }

    ScaffoldContainer(
        navController = navController,
        isLoading = isLoading,
        jetModels = jetModels
    ) {
        NavHost(
            navController = navController,
            startDestination = "Home",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("Home") {
                DashboardScreen(navController = navController)
            }

            composable("JetSelection") {
                JetSelectionScreen(jetModels) { selectedJet ->
                    navController.navigate("modelViewer/$selectedJet")
                }
            }

            composable("FighterJetSearchScreen") {
                FighterJetSearchScreen(navController = navController)
            }

            composable("SoundPlayerScreen") {
                SoundPlayerScreen()
            }

            composable("CompareJets") {
                CompareJetsScreen(
                    jets = getAllFighterJets(),
                    onNavigateToJet = { jet ->
                        navController.navigate("jet_detail/${jet.id}")
                    }
                )
            }

            composable(
                route = "jet_detail/{jetId}",
                arguments = listOf(navArgument("jetId") { type = NavType.IntType })
            ) { backStackEntry ->
                val jetId = backStackEntry.arguments?.getInt("jetId") ?: return@composable
                FighterJetDetailScreen(jetId = jetId, navController = navController)
            }

            composable(FighterJetRoute.SearchScreen.route) {
                FighterJetSearchScreen(navController = navController)
            }

            composable("aiJetFinder") {
                JetIdentifierScreen()
            }

            composable("quiz") {
                QuizScreen(quizViewModel, navController)
            }
            composable("progress") {
                ProgressScreen(quizViewModel, navController)
            }

            composable(
                route = FighterJetRoute.JetDetailScreen.route,
                arguments = listOf(navArgument("jetId") { type = NavType.IntType })
            ) { backStackEntry ->
                val jetId = backStackEntry.arguments?.getInt("jetId") ?: return@composable
                FighterJetDetailScreen(jetId = jetId, navController = navController)
            }

            composable("airforce_detail/{countryKey}") { backStackEntry ->
                AirForceDetailScreen(
                    countryKey = backStackEntry.arguments?.getString("countryKey") ?: "",
                    navController = navController
                )
            }

            composable("jet_detail/{jetId}") { backStackEntry ->
                val jetId =
                    backStackEntry.arguments?.getString("jetId")?.toInt() ?: return@composable
                FighterJetDetailScreen(jetId = jetId, navController = navController)
            }


            composable(
                route = "modelViewer/{jetName}",
                arguments = listOf(navArgument("jetName") { type = NavType.StringType })
            ) { backStackEntry ->
                val jetName = backStackEntry.arguments?.getString("jetName") ?: ""
                val selectedJet = jetModels.find { it.name == jetName }

                if (selectedJet != null) {
                    JetModelViewerScreen(jetModel = selectedJet)
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Model not found", color = MaterialTheme.colorScheme.onBackground)
                    }
                }
            }
        }
    }
}