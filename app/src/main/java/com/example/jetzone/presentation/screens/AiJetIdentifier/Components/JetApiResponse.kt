package com.example.jetzone.presentation.screens.AiJetIdentifier.Components

import kotlinx.serialization.Serializable

@Serializable
data class JetInfo(
    val name: String,
    val manufacturer: String,
    val type: String,
    val roles: List<String>,
    val variants: List<String>,
    val engine: String,
    val operators: List<String>,
)


@Serializable
data class JetApiResponse(
    val raw: String,
)
