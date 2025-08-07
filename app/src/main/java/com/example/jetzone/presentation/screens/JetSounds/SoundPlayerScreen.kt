package com.example.jetzone.presentation.screens.JetSounds

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.jetzone.R
import com.example.jetzone.presentation.screens.JetSounds.Components.SoundItem
import com.example.jetzone.presentation.screens.JetSounds.Components.SoundRow


@Composable
fun SoundPlayerScreen() {
    val context = LocalContext.current
    var currentPlayingIndex by remember { mutableStateOf<Int?>(null) }
    val mediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }

    val soundList = listOf(
        SoundItem("F-22 Raptor", R.raw.f16),
        SoundItem("J-20 Mighty Dragon", R.raw.su57),
        SoundItem("Rafale", R.raw.f16),
        SoundItem("Eurofighter Typhoon", R.raw.su57),
        SoundItem("Gripen E", R.raw.f16),
        SoundItem("MiG-35", R.raw.su57),
        SoundItem("F-35 Lightning II", R.raw.f16),
        SoundItem("Chengdu FC-31", R.raw.su57),
        SoundItem("KF-21 Boramae", R.raw.f16),
        SoundItem("T-50 Golden Eagle", R.raw.su57),
        SoundItem("HAL Tejas Mk2", R.raw.f16),
        SoundItem("Su-75 Checkmate", R.raw.su57),
        SoundItem("Mirage 2000", R.raw.f16),
        SoundItem("F-15EX Eagle II", R.raw.su57)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp).padding(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        soundList.forEachIndexed { index, sound ->
            SoundRow(
                label = sound.label,
                isPlaying = currentPlayingIndex == index,
                onPlayPause = {
                    if (currentPlayingIndex == index) {
                        mediaPlayer.value?.pause()
                        currentPlayingIndex = null
                    } else {
                        mediaPlayer.value?.release()
                        val player = MediaPlayer.create(context, sound.resId)
                        player.start()
                        mediaPlayer.value = player
                        currentPlayingIndex = index

                        player.setOnCompletionListener {
                            currentPlayingIndex = null
                        }
                    }
                }
            )
        }
    }
}