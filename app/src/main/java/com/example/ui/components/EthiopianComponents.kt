package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.King
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
            // Traditional Ethiopian Tibeb geometric trim drawn via Canvas
            Canvas(
                modifier = Modifier
                    .matchParentSize()
            ) {
                val stripeHeight = 6.dp.toPx()
                val thirdWidth = size.width / 3f

                // Top tri-color Ethiopian heritage ribbon
                drawRect(color = BannerGreen, topLeft = Offset.Zero, size = androidx.compose.ui.geometry.Size(thirdWidth, stripeHeight))
                drawRect(color = BannerGold, topLeft = Offset(thirdWidth, 0f), size = androidx.compose.ui.geometry.Size(thirdWidth, stripeHeight))
                drawRect(color = BannerRed, topLeft = Offset(thirdWidth * 2f, 0f), size = androidx.compose.ui.geometry.Size(thirdWidth + 2f, stripeHeight))

                // Bottom woven Tibeb diamond frieze
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KingStorySection(
    king: King,
    showPortraitThumbnail: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.45f)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (showPortraitThumbnail) {
                    Image(
                        painter = painterResource(id = king.portraitResId),
                        contentDescription = "${king.fullName} ${stringResource(R.string.king_portrait_cd)}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(84.dp)
                            .aspectRatio(3f / 4f)
                            .clip(MaterialTheme.shapes.medium)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = MaterialTheme.shapes.medium,
                            ),
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
                        ),
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CalendarMonth,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.size(15.dp),
                            )
                            Text(
                                text = king.reignEra,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = king.fullName,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(15.dp),
                            )
                            Text(
                                text = king.birthplace,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            OrnateGoldDivider()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.biography_heading),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = king.biography,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp),
                )
                Text(
                    text = stringResource(R.string.achievements_heading),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                king.achievements.forEachIndexed { index, achievement ->
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                        ),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.Top,
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(26.dp),
                            ) {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                    Text(
                                        text = "${index + 1}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSecondary,
                                    )
                                }
                            }
                            Text(
                                text = achievement,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
            }
        }
    }
}
