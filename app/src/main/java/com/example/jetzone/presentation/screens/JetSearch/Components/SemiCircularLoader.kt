package com.example.jetzone.presentation.screens.JetSearch.Components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SemiCircularLoader(
    modifier: Modifier = Modifier,
    progress: Float = 90f,
    size: Dp = 100.dp,
    animationDuration: Int = 550,
    indicatorColor: Color? = null,
    indicatorBrush: Brush? = null,
    trackColor: Color = Color.LightGray,
    showProgressDot: Boolean = true,
    progressDotColor: Color = Color.White,
    progressDotSize: Dp = 8.dp,
    indicatorThickness: Dp = 20.dp,
    startAngle: Float = 180f,
    maxProgress: Float = 100f,
    centerImage: Painter? = null,
    imageSize: Dp = 100.dp,
) {
    require(!(indicatorColor != null && indicatorBrush != null)) {
        ""
    }

    val endsSpacing = (startAngle - 90) * 2
    val progressAnimation = remember { Animatable(0f) }

    LaunchedEffect(progressAnimation) {
        progressAnimation.animateTo(
            targetValue = progress.coerceIn(0f, 100f),
            animationSpec = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing)
        )
    }

    Box(modifier = modifier.size(size)) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val width = this.size.width
            val height = this.size.height

            drawArc(
                color = trackColor,
                startAngle = startAngle,
                sweepAngle = 360f - endsSpacing,
                useCenter = false,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round)
            )

            val sweepAngle = (progressAnimation.value / maxProgress) * (360f - endsSpacing)

            if (indicatorBrush != null) {
                drawArc(
                    brush = indicatorBrush,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(indicatorThickness.toPx(), cap = StrokeCap.Round)
                )
            } else {
                drawArc(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF00BCD4), Color(0xFF8BC34A))
                    ),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(indicatorThickness.toPx(), cap = StrokeCap.Round)
                )
            }

            if (showProgressDot) {
                val dotRadius = progressDotSize / 2
                val endAngle = startAngle + sweepAngle
                val dotX =
                    width / 2 + width / 2 * cos(Math.toRadians(endAngle.toDouble())).toFloat()
                val dotY =
                    height / 2 + height / 2 * sin(Math.toRadians(endAngle.toDouble())).toFloat()

                drawCircle(
                    color = progressDotColor,
                    center = Offset(dotX, dotY),
                    radius = dotRadius.toPx()
                )
            }
        }

        centerImage?.let {
            Image(
                painter = it,
                contentDescription = "Center Image",
                modifier = Modifier
                    .size(imageSize)
                    .align(Alignment.Center)
            )
        }
    }
}