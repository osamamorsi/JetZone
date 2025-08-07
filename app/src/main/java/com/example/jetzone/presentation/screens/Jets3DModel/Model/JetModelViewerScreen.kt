package com.example.jetzone.presentation.screens.Jets3DModel.Model

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import io.github.sceneview.SceneView
import io.github.sceneview.node.ModelNode
import io.github.sceneview.utils.readBuffer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun JetModelViewerScreen(jetModel: JetModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(true) }
    var progress by remember { mutableIntStateOf(0) }
    var sceneView by remember { mutableStateOf<SceneView?>(null) }
    var modelNode by remember { mutableStateOf<ModelNode?>(null) }

    LaunchedEffect(sceneView, jetModel.name) {
        if (sceneView == null) return@LaunchedEffect
        coroutineScope.launch {
            loading = true
            progress = 0

            modelNode?.let { sceneView?.removeChildNode(it) }

            val modelFile = File(context.cacheDir, jetModel.localFileName)
            var modelBuffer = modelFile.takeIf { it.exists() }?.readBuffer()

            if (modelBuffer == null) {
                if (!isNetworkAvailable(context)) {
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                    loading = false
                    return@launch
                }

                try {
                    withContext(Dispatchers.IO) {
                        val url = URL(jetModel.modelUrl)
                        val connection = url.openConnection()
                        val totalSize = connection.contentLength

                        url.openStream().use { input ->
                            FileOutputStream(modelFile).use { output ->
                                val buffer = ByteArray(4096)
                                var bytesRead: Int
                                var downloaded = 0

                                while (input.read(buffer).also { bytesRead = it } != -1) {
                                    output.write(buffer, 0, bytesRead)
                                    downloaded += bytesRead
                                    if (totalSize > 0) {
                                        withContext(Dispatchers.Main) {
                                            progress = (downloaded * 100 / totalSize)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    modelBuffer = modelFile.readBuffer()
                } catch (_: Exception) {
                    modelFile.delete()
                    Toast.makeText(context, "Failed to download model", Toast.LENGTH_SHORT).show()
                    loading = false
                    return@launch
                }
            }

            val modelInstance = try {
                sceneView?.modelLoader?.createModelInstance(modelBuffer)
            } catch (_: Exception) {
                null
            }

            if (modelInstance == null) {
                Toast.makeText(context, "Failed to load model", Toast.LENGTH_SHORT).show()
                loading = false
                return@launch
            }

            val newNode = ModelNode(modelInstance = modelInstance, scaleToUnits = 0.8f)
            sceneView?.addChildNode(newNode)
            modelNode = newNode
            loading = false
            progress = 0
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (loading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    LinearWavyProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.background
                    )
                    Text(
                        text = "Loading: $progress%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        SceneView(ctx).also { sceneView = it }
                    }
                )
            }
        }
    }
}