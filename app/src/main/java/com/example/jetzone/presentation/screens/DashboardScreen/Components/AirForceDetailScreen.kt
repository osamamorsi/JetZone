package com.example.jetzone.presentation.screens.DashboardScreen.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetzone.R
import com.example.jetzone.presentation.screens.JetSearch.Components.getAllFighterJets

@Composable
fun AirForceDetailScreen(countryKey: String, navController: NavHostController) {
    val jetModels = countryJetsMap[countryKey] ?: emptyList()
    val counts = countryJetCounts[countryKey] ?: emptyMap()
    val total = counts.values.sum()
    val AirforceLogo = countryLogos[countryKey] ?: R.drawable.flag_russia

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 100.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(vertical = 10.dp)
        ) {
            Image(
                painter = painterResource(id = AirforceLogo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(
            "Total Jets : More Than $total",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(8.dp))
        LazyColumn {
            items(jetModels) { modelName ->
                val jet = getAllFighterJets().find {
                    it.modelName.equals(modelName, ignoreCase = true)
                            || it.fullName.contains(modelName, ignoreCase = true)
                }
                val count = counts[modelName] ?: 0
                if (jet != null) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { navController.navigate("jet_detail/${jet.id}") },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = jet.imageRes),
                                contentDescription = jet.modelName,
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(60.dp),
                            )
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(jet.fullName, style = MaterialTheme.typography.titleSmall)
                                Text("Count: $count", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}