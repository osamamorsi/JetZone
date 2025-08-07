package com.example.jetzone.presentation.screens.JetSearch.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun FighterJetDetailScreen(jetId: Int, navController: NavController) {
    val jet = getAllFighterJets().find { it.id == jetId } ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 10.dp).padding(bottom = 90.dp)
    ) {
        Image(
            painter = painterResource(id = jet.imageRes),
            contentDescription = jet.modelName,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(MaterialTheme.colorScheme.onPrimary)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                jet.fullName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            jet.threeDModelRoute?.let { route ->
                Button(modifier = Modifier.padding(horizontal = 3.dp), onClick = {
                    navController.navigate("modelViewer/${jet.modelName}")
                }) {
                    Icon(
                        imageVector = Icons.Filled.ViewInAr,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("3D Model")
                }
            }
        }

        Column(modifier = Modifier.padding(10.dp)) {

            Row(
                modifier = Modifier
                    .padding(bottom = 7.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = jet.countryFlagRes),
                    contentDescription = jet.manufacturerCountry,
                    modifier = Modifier
                        .size(35.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    "Made in ${jet.manufacturerCountry}",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(10.dp)
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 7.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Height:\n${jet.height}",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Wingspan:\n${jet.wingspan}",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 7.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Top Speed:\n${jet.topSpeed}",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Generation:\n${jet.generation}",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(bottom = 7.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onPrimary)

            ) {
                Text(
                    "Missiles: ${jet.missiles}",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(bottom = 7.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 7.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.onPrimary)
                ) {
                    Text(
                        "First Flight:\n${jet.firstFlight}",
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Row(
                    Modifier
                        .padding(bottom = 7.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.onPrimary)

                ) {
                    Text(
                        "Range:\n${jet.range}",
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}