package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.BannerGold
import com.example.ui.theme.BannerGreen
import com.example.ui.theme.BannerRed
import com.example.ui.theme.BurnishedGold

@Composable
fun TibebHeroBanner(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        shadowElevation = 6.dp,
        border = BorderStroke(1.5.dp, AntiqueGold.copy(alpha = 0.7f)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF28120D),
                            Color(0xFF5D1313),
                            Color(0xFF2E1A10),
                        )
                    )
                )
        ) {
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                val stripeHeight = 6.dp.toPx()
                val thirdWidth = size.width / 3f

                drawRect(color = BannerGreen, topLeft = Offset.Zero, size = Size(thirdWidth, stripeHeight))
                drawRect(color = BannerGold, topLeft = Offset(thirdWidth, 0f), size = Size(thirdWidth, stripeHeight))
                drawRect(color = BannerRed, topLeft = Offset(thirdWidth * 2f, 0f), size = Size(thirdWidth + 2f, stripeHeight))

                val friezeY = size.height - 12.dp.toPx()
                val diamondStep = 18.dp.toPx()
                val halfD = 5.dp.toPx()
                var x = 12.dp.toPx()
                var index = 0
                while (x < size.width - 12.dp.toPx()) {
                    val diamondColor = when (index % 3) {
                        0 -> BannerGreen.copy(alpha = 0.75f)
                        1 -> BannerGold.copy(alpha = 0.85f)
                        else -> BannerRed.copy(alpha = 0.75f)
                    }
                    val path = Path().apply {
                        moveTo(x, friezeY - halfD)
                        lineTo(x + halfD, friezeY)
                        lineTo(x, friezeY + halfD)
                        lineTo(x - halfD, friezeY)
                        close()
                    }
                    drawPath(path = path, color = diamondColor)
                    x += diamondStep
                    index++
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Surface(
                    shape = CircleShape,
                    color = BurnishedGold.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, BurnishedGold.copy(alpha = 0.7f)),
                ) {
                    Icon(
                        imageVector = Icons.Filled.WorkspacePremium,
                        contentDescription = null,
                        tint = BurnishedGold,
                        modifier = Modifier
                            .padding(10.dp)
                            .size(28.dp),
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFFFFF8E8),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFEBD9B9),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun OrnateGoldDivider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.5.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(Color.Transparent, AntiqueGold.copy(alpha = 0.85f))
                    )
                )
        )
        Canvas(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(12.dp)
        ) {
            val path = Path().apply {
                moveTo(size.width / 2f, 0f)
                lineTo(size.width, size.height / 2f)
                lineTo(size.width / 2f, size.height)
                lineTo(0f, size.height / 2f)
                close()
            }
            drawPath(path = path, color = AntiqueGold)
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.5.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(AntiqueGold.copy(alpha = 0.85f), Color.Transparent)
                    )
                )
        )
    }
}
