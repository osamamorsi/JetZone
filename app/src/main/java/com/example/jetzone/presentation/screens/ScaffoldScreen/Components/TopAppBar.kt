package com.example.jetzone.presentation.screens.ScaffoldScreen.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetzone.R
import com.example.jetzone.presentation.screens.Jets3DModel.Model.JetModel
import com.example.jetzone.presentation.viewModels.quizViewmodel.QuizViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    navController: NavHostController,
    jetModels: List<JetModel>,
    viewModel: QuizViewModel = viewModel(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("/") ?: "Home"
    val jetNameFromRoute = navBackStackEntry?.arguments?.getString("jetName")
    val selectedJet = jetModels.find { it.name == jetNameFromRoute }
    val rawRoute = navBackStackEntry?.destination?.route ?: ""

    var showDialog by remember { mutableStateOf(false) }

    val titleMap = mapOf(
        "settings" to "Settings",
        "JetSelection" to "JetView 3D",
        "FighterJetSearchScreen" to "Search",
        "quiz" to "MachMind",
        "progress" to "Your Progress",
        "CompareJets" to "Jet VS Jet",
        "SoundPlayerScreen" to "Jets Sounds",
        "aiJetFinder" to "AI Jet Detector"
    )

    val title = when (currentRoute) {
        "modelViewer" -> (selectedJet?.name ?: "Jet Model") + " - 3DView"
        "Home" -> "JetZone"
        "detail_screen" -> "Jet Details"
        else -> titleMap[currentRoute] ?: ""
    }

    TopAppBar(
        navigationIcon = {
            val noBackStackRoutes = listOf("Home")
            val showBackButton = currentRoute !in noBackStackRoutes
            if (showBackButton) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            when (currentRoute) {
                "quiz" -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "See Progress",
                            modifier = Modifier
                                .clickable { navController.navigate("progress") }
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                .clip(RoundedCornerShape(15.dp))
                        )
                    }
                }

                "progress" -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Reset Progress",
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { showDialog = true }
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (currentRoute == "Home") {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.jetzone),
                                contentDescription = "Logo",
                                modifier = Modifier.size(60.dp),
                                tint = Color.Unspecified
                            )
                            Text(
                                text = "JetZone",
                                style = MaterialTheme.typography.titleLarge,
                                maxLines = 1
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleLarge,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = .3f),
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Reset") },
            text = { Text("Are you sure you want to reset your quiz progress?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetProgress()
                        showDialog = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}