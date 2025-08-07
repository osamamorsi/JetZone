package com.example.jetzone.presentation.screens.AiJetIdentifier

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.jetzone.presentation.screens.AiJetIdentifier.Components.JetApiResponse
import com.example.jetzone.presentation.screens.AiJetIdentifier.Components.JetInfo
import com.example.jetzone.presentation.screens.AiJetIdentifier.Components.JetInfoView
import com.example.jetzone.presentation.screens.AiJetIdentifier.Components.uploadJetImage
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun JetIdentifierScreen() {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var jetInfo by remember { mutableStateOf<JetInfo?>(null) }
    var errorText by remember { mutableStateOf<String?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        jetInfo = null
        errorText = null

        uri?.let {
            isLoading = true
            uploadJetImage(context, uri) { result, error ->
                isLoading = false
                if (error != null) {
                    errorText = error
                } else {
                    try {
                        val apiResponse = Json.decodeFromString<JetApiResponse>(result ?: "")
                        val cleanJson = apiResponse.raw
                            .replace("```json", "")
                            .replace("```", "")
                            .trim()
                        println("Cleaned JSON:\n$cleanJson")

                        val json = Json { ignoreUnknownKeys = true }
                        jetInfo = json.decodeFromString<JetInfo>(cleanJson)
                    } catch (e: Exception) {
                        println("Parsing Exception: ${e.localizedMessage}")
                        errorText = "Failed to parse Jet info"
                    }
                }
            }
        }
    }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .padding(bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            imagePickerLauncher.launch("image/*")
        }) {
            Text("Upload Jet Image")
        }
        selectedImageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Selected Jet",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
        if (isLoading) {
            LoadingIndicator()
        }
        errorText?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        jetInfo?.let {
            JetInfoView(it)
        }
    }
}