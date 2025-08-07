package com.example.jetzone.presentation.screens.CompareJetsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetzone.presentation.screens.CompareJetsScreen.Model.ComparisonTable
import com.example.jetzone.presentation.screens.CompareJetsScreen.Model.JetDropdown
import com.example.jetzone.presentation.screens.JetSearch.Components.FighterJetDetails


@Composable
fun CompareJetsScreen(
    jets: List<FighterJetDetails>,
    onNavigateToJet: (FighterJetDetails) -> Unit
) {
    var selectedJet1 by remember { mutableStateOf<FighterJetDetails?>(null) }
    var selectedJet2 by remember { mutableStateOf<FighterJetDetails?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp).padding(bottom = 110.dp)
    ) {
        Text(
            "Select Jets to Compare",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            JetDropdown(jets, selectedJet1) { selectedJet1 = it }
            Spacer(modifier = Modifier.width(10.dp))
            JetDropdown(jets, selectedJet2) { selectedJet2 = it }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (selectedJet1 != null && selectedJet2 != null) {
            ComparisonTable(selectedJet1!!, selectedJet2!!, onNavigateToJet)
        }
    }
}