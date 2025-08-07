package com.example.jetzone.presentation.screens.JetSearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetzone.presentation.screens.JetSearch.Components.FighterJetItem
import com.example.jetzone.presentation.screens.JetSearch.Components.FighterJetRoute
import com.example.jetzone.presentation.screens.JetSearch.Components.getAllFighterJets


@Composable
fun FighterJetSearchScreen(navController: NavController) {
    var searchInput by remember { mutableStateOf("") }
    val jetList = getAllFighterJets()
    val matchedJets = remember(searchInput) {
        jetList.filter { it.modelName.contains(searchInput, ignoreCase = true) }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .padding(bottom = 10.dp)
    ) {
        OutlinedTextField(
            value = searchInput,
            onValueChange = { searchInput = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search For Jets") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            singleLine = true,

            )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.padding(bottom = 100.dp)) {
            items(matchedJets) { jet ->
                FighterJetItem(jet = jet, onItemClicked = {
                    navController.navigate(FighterJetRoute.JetDetailScreen.createRoute(jet.id))
                })
            }
        }
    }
}