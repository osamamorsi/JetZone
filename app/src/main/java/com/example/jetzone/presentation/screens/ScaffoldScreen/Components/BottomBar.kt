package com.example.jetzone.presentation.screens.ScaffoldScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.AppBarRow
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MyBottomBar(
    navController: NavHostController,
    currentSelectedRoute: String,
) {
    val selectedColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    val unselectedColor = Color.Transparent

    Box(
        modifier = Modifier
            .padding(bottom = 25.dp)
            .fillMaxWidth()
            .offset(y = -FloatingToolbarDefaults.ScreenOffset),
        contentAlignment = Alignment.Center
    ) {
        HorizontalFloatingToolbar(
            expanded = true,
            trailingContent = {
                AppBarRow(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    overflowIndicator = { menuState ->
                        IconButton(
                            onClick = {
                                if (menuState.isExpanded) menuState.dismiss() else menuState.show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "More"
                            )
                        }
                    }
                ) {
                    fun toolbarItem(
                        id: String,
                        icon: ImageVector,
                        label: String,
                        onClick: () -> Unit,
                    ) {
                        val isSelected = currentSelectedRoute == id
                        val backgroundColor = if (isSelected) selectedColor else unselectedColor
                        clickableItem(
                            onClick = onClick,
                            icon = {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = backgroundColor,
                                            shape = RoundedCornerShape(20)
                                        )
                                        .padding(5.dp)
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = label,
                                        modifier = Modifier.size(80.dp),
                                        tint = if (isSelected)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            LocalContentColor.current
                                    )
                                }
                            },
                            label = label
                        )
                    }
                    toolbarItem("Home", Icons.Filled.Home, "JetZone") {
                        navController.navigate("Home") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    toolbarItem("CompareJets", Icons.Filled.Compare, "Compare") {
                        navController.navigate("CompareJets") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    toolbarItem(
                        "FighterJetSearchScreen",
                        Icons.Filled.Search,
                        "FighterJetSearchScreen"
                    ) {
                        navController.navigate("FighterJetSearchScreen") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    toolbarItem("quiz", Icons.Filled.VideogameAsset, "quiz") {
                        navController.navigate("quiz") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    toolbarItem("aiJetFinder", Icons.Filled.AutoAwesome, "aiJetFinder") {
                        navController.navigate("aiJetFinder") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            },
            content = {}
        )
    }
}