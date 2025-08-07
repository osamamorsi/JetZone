package com.example.jetzone.presentation.screens.CompareJetsScreen.Model

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetzone.presentation.screens.JetSearch.Components.FighterJetDetails


@Composable
fun ComparisonTable(
    jet1: FighterJetDetails,
    jet2: FighterJetDetails,
    onNavigateToJet: (FighterJetDetails) -> Unit
) {
    val labelStyle = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
    val valueStyle = MaterialTheme.typography.bodyLarge

    @Composable
    fun InfoRow(label: String, val1: String, val2: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(vertical = 8.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(label, modifier = Modifier.weight(1f), style = labelStyle)
            Text(val1, modifier = Modifier.weight(1f), style = valueStyle)
            Text(val2, modifier = Modifier.weight(1f), style = valueStyle)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        JetImage(jet1.imageRes)
        JetImage(jet2.imageRes)
    }

    Spacer(modifier = Modifier.height(12.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        InfoRow("Model", jet1.modelName, jet2.modelName)
        Spacer(modifier = Modifier.height(5.dp))
        InfoRow("Full Name", jet1.fullName, jet2.fullName)
        Spacer(modifier = Modifier.height(5.dp))
        InfoRow("Top Speed", jet1.topSpeed, jet2.topSpeed)
        Spacer(modifier = Modifier.height(5.dp))
        InfoRow("Generation", jet1.generation, jet2.generation)
        Spacer(modifier = Modifier.height(5.dp))
        InfoRow("Missiles", jet1.missiles, jet2.missiles)
        Spacer(modifier = Modifier.height(5.dp))
        InfoRow("Country", jet1.manufacturerCountry, jet2.manufacturerCountry)
        Spacer(modifier = Modifier.height(5.dp))
        InfoRow("First Flight", jet1.firstFlight, jet2.firstFlight)
        Spacer(modifier = Modifier.height(5.dp))
        InfoRow("Height", jet1.height, jet2.height)
        Spacer(modifier = Modifier.height(5.dp))
        InfoRow("Wingspan", jet1.wingspan, jet2.wingspan)
        Spacer(modifier = Modifier.height(5.dp))
        InfoRow("Range", jet1.range, jet2.range)
    }

    Spacer(modifier = Modifier.height(20.dp))

    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(onClick = { onNavigateToJet(jet1) }, modifier = Modifier.weight(1f)) {
            Text("View ${jet1.modelName}")
        }

        Button(onClick = { onNavigateToJet(jet2) }, modifier = Modifier.weight(1f)) {
            Text("View ${jet2.modelName}")
        }
    }
}


@Composable
fun JetImage(imageRes: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = Modifier
            .size(145.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.onSecondary)
            .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
            .padding(3.dp)
    )
}