package com.example.jetzone.presentation.screens.DashboardScreen.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetzone.R

@Composable
fun TopAirForces(navController: NavHostController) {
    val airForces = listOf(
        Triple(R.drawable.egypt, "Egypt", "airforce_egypt"),
        Triple(R.drawable.chinafg, "China", "airforce_china"),
        Triple(R.drawable.flag_usa, "US", "airforce_us"),
        Triple(R.drawable.flag_russia, "Russia", "airforce_russia"),
        Triple(R.drawable.algeria, "Algeria", "airforce_algeria"),
        Triple(R.drawable.turkey, "Turkey", "airforce_turkey"),
        Triple(R.drawable.pakistan, "Pakistan", "airforce_pakistan"),
        Triple(R.drawable.saudiarabia, "SA", "airforce_saudiarabia"),
        Triple(R.drawable.flag_uk, "UK", "airforce_uk"),
        Triple(R.drawable.flag_france, "France", "airforce_france")
    )

    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Top Air Forces",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 3.dp)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(airForces) { (imageRes, title, route) ->
                    CarouselItem(imageRes = imageRes, title = title) {
                        navController.navigate("airforce_detail/$route")
                    }
                }
            }
        }
    }
}

val countryJetsMap = mapOf(
    "airforce_egypt" to listOf("F-16", "Rafale", "MiG-29", "Mirage 2000"),
    "airforce_china" to listOf("J-20", "J-16", "J-10C", "Su-35", "Xi'an H-6", "J-31"),
    "airforce_algeria" to listOf("Su-30", "MiG-29", "MiG-25", "Su-24"),
    "airforce_turkey" to listOf("F-16", "F-4E", "KAAN (TF-X)"),
    "airforce_pakistan" to listOf("JF-17", "F-16", "J-10C", "MiG-29"),
    "airforce_saudiarabia" to listOf("F-15", "Eurofighter Typhoon", "Tornado"),
    "airforce_uk" to listOf("Eurofighter Typhoon", "F-35", "Tornado"),
    "airforce_us" to listOf(
        "F-22", "F-35", "F-15", "F-16", "F/A-18", "F-15",
        "A10c", "B-1 Lancer", "B-2 Spirit", "F-117", "SR-71", "YF-23"
    ),
    "airforce_russia" to listOf(
        "Su-57", "Su-35", "Su-30SM", "MiG-35", "Su-30", "MiG-29",
        "Su-37", "Su-34", "Su-47", "Tu-95", "Tu-22", "MiG-1.44"
    ),
    "airforce_france" to listOf("Rafale", "Mirage 2000")
)
val countryJetCounts = mapOf(
    "airforce_egypt" to mapOf(
        "F-16" to 218,
        "Rafale" to 24,
        "MiG-29" to 46,
        "Mirage 2000" to 19
    ),
    "airforce_china" to mapOf(
        "J-20" to 150,
        "J-16" to 200,
        "J-10C" to 250,
        "Su-35" to 24,
        "Xi'an H-6" to 120,
        "J-31" to 10
    ),
    "airforce_algeria" to mapOf(
        "Su-30" to 58,
        "MiG-29" to 34,
        "MiG-25" to 14,
        "Su-24" to 32
    ),
    "airforce_turkey" to mapOf(
        "F-16" to 245,
        "F-4E" to 54,
        "KAAN (TF-X)" to 0
    ),
    "airforce_pakistan" to mapOf(
        "JF-17" to 138,
        "F-16" to 76,
        "J-10C" to 25,
        "MiG-29" to 12
    ),
    "airforce_saudiarabia" to mapOf(
        "F-15SA" to 84,
        "Eurofighter Typhoon" to 72,
        "Tornado" to 81
    ),
    "airforce_uk" to mapOf(
        "Eurofighter Typhoon" to 119,
        "F-35B" to 27,
        "Tornado" to 0
    ),
    "airforce_us" to mapOf(
        "F-22" to 186,
        "F-35" to 450,
        "F-15" to 24,
        "F-16" to 935,
        "F/A-18" to 600,
        "F-15" to 234,
        "A10c" to 281,
        "B-1 Lancer" to 45,
        "B-2 Spirit" to 20,
        "F-117" to 0,
        "SR-71" to 0,
        "YF-23" to 2
    ),
    "airforce_russia" to mapOf(
        "Su-57" to 22,
        "Su-35" to 110,
        "Su-30" to 120,
        "MiG-35" to 6,
        "Su-30" to 115,
        "MiG-29" to 70,
        "Su-37" to 0,
        "Su-34" to 127,
        "Su-47" to 1,
        "Tu-95" to 55,
        "Tu-22" to 62,
        "MiG-1.44" to 1
    ),
    "airforce_france" to mapOf(
        "Rafale" to 102,
        "Mirage 2000" to 91
    )
)
val countryLogos = mapOf(
    "airforce_egypt" to R.drawable.airforce_egypt,
    "airforce_china" to R.drawable.airforce_china,
    "airforce_algeria" to R.drawable.airforce_algeria,
    "airforce_turkey" to R.drawable.airforce_turkey,
    "airforce_pakistan" to R.drawable.airforce_pakistan,
    "airforce_saudiarabia" to R.drawable.airforce_saudiarabia,
    "airforce_uk" to R.drawable.airforce_uk,
    "airforce_us" to R.drawable.airforce_us,
    "airforce_russia" to R.drawable.airforce_russia,
    "airforce_france" to R.drawable.airforce_france
)


@Composable
fun CarouselItem(
    imageRes: Int,
    title: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(55.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
                    )
                )
        )
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 6.dp)
                .padding(horizontal = 5.dp)
        )
    }
}