package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.model.King
import com.example.ui.components.DeveloperCreditCard
import com.example.ui.components.KingStorySection
import com.example.ui.components.TibebHeroBanner
import com.example.viewmodel.KingsUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhotoMatcherScreen(
    uiState: KingsUiState,
    onSelectKing: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedKing = uiState.selectedKing
    var showFullscreenPortrait by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 680.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            TibebHeroBanner(
                title = stringResource(R.string.hero_title),
                subtitle = stringResource(R.string.hero_subtitle),
            )

            // Royal Portraits Selector & HD Showcase Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text(
                        text = stringResource(R.string.btn_use_sample_portrait),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                    )

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        uiState.kings.forEach { king ->
                            val isSelected = selectedKing.id == king.id
                            Column(
                                modifier = Modifier
                                    .width(74.dp)
                                    .clip(MaterialTheme.shapes.small)
                                    .clickable { onSelectKing(king.id) }
                                    .minimumInteractiveComponentSize()
                                    .padding(4.dp)
                                    .testTag("sample_king_${king.id}"),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Image(
                                    painter = painterResource(id = king.portraitResId),
                                    contentDescription = king.shortName,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(58.dp)
                                        .clip(CircleShape)
                                        .border(
                                            width = if (isSelected) 3.dp else 1.5.dp,
                                            color = if (isSelected) {
                                                MaterialTheme.colorScheme.primary
                                            } else {
                                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
                                            },
                                            shape = CircleShape,
                                        ),
                                )
                                Text(
                                    text = king.shortName
                                        .replace("ንግሥተ ነገሥታት ", "")
                                        .replace("ጠቅላይ ሚኒስትር ", "")
                                        .replace("ኮሎኔል ", "")
                                        .replace("አፄ ", "")
                                        .replace("ቀዳማዊ ", ""),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }

                    // Full 3:4 Uncropped Portrait Preview with Tap-to-Zoom
                    Box(
                        modifier = Modifier
                            .widthIn(max = 320.dp)
                            .fillMaxWidth()
                            .aspectRatio(3f / 4f)
                            .clip(MaterialTheme.shapes.large)
                            .border(
                                width = 3.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = MaterialTheme.shapes.large,
                            )
                            .clickable { showFullscreenPortrait = true }
                            .testTag("selected_photo_image"),
                    ) {
                        Image(
                            painter = painterResource(id = selectedKing.portraitResId),
                            contentDescription = "${selectedKing.fullName} ${stringResource(R.string.king_portrait_cd)}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                        )

                        Surface(
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.58f),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(10.dp),
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ZoomIn,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp),
                                )
                                Text(
                                    text = stringResource(R.string.tap_to_zoom_portrait),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White,
                                )
                            }
                        }
                    }
                }
            }

            // Revealed Story Card
            KingStorySection(
                king = selectedKing,
                showPortraitThumbnail = false,
                modifier = Modifier.testTag("king_story_section"),
            )

            DeveloperCreditCard()

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (showFullscreenPortrait) {
        FullscreenPortraitDialog(
            king = selectedKing,
            onDismiss = { showFullscreenPortrait = false },
        )
    }
}

@Composable
fun FullscreenPortraitDialog(
    king: King,
    onDismiss: () -> Unit,
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.94f))
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val newScale = (scale * zoom).coerceIn(1f, 4f)
                        scale = newScale
                        offset = if (newScale > 1f) {
                            offset + pan
                        } else {
                            Offset.Zero
                        }
                    }
                },
        ) {
            Image(
                painter = painterResource(id = king.portraitResId),
                contentDescription = "${king.fullName} ${stringResource(R.string.king_portrait_cd)}",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y,
                    ),
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = king.fullName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = king.eraYears,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                )
            }

            IconButton(
                onClick = onDismiss,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.65f),
                    contentColor = Color.White,
                ),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(20.dp)
                    .testTag("close_fullscreen_portrait"),
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.close_fullscreen_cd),
                )
            }
        }
    }
}
