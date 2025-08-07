package com.example.jetzone.presentation.screens.CompareJetsScreen.Model

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.jetzone.presentation.screens.JetSearch.Components.FighterJetDetails
import kotlin.collections.forEach

@Composable
fun JetDropdown(
    jets: List<FighterJetDetails>,
    selectedJet: FighterJetDetails?,
    onJetSelected: (FighterJetDetails) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedJet?.modelName ?: "Select Jet")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            jets.forEach { jet ->
                DropdownMenuItem(
                    text = { Text(jet.modelName) },
                    onClick = {
                        onJetSelected(jet)
                        expanded = false
                    }
                )
            }
        }
    }
}