package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.King
import com.example.ui.components.DeveloperCreditCard
import com.example.ui.components.KingStorySection
import com.example.ui.components.TibebHeroBanner
import com.example.viewmodel.KingsUiState

@Composable
fun RoyalArchiveScreen(
    uiState: KingsUiState,
    onOpenKingDetailCompact: (King) -> Unit,
    onSelectKingDetailWide: (String) -> Unit,
    onMatchWithPhoto: (King) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isWideLayout = maxWidth >= 600.dp

        if (isWideLayout) {
            // Canonical List-Detail layout for Medium/Expanded windows
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(0.42f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                ) {
                    item {
                        Column(modifier = Modifier.padding(bottom = 4.dp)) {
                            Text(
                                text = stringResource(R.string.archive_header_title),
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = stringResource(R.string.archive_header_subtitle),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }

                    items(uiState.kings, key = { it.id }) { king ->
                        val isSelected = uiState.archiveDetailKing.id == king.id
                        KingArchiveCard(
                            king = king,
                            isSelected = isSelected,
                            onClick = { onSelectKingDetailWide(king.id) },
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(0.58f)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    val selectedKing = uiState.archiveDetailKing
                    KingStorySection(
                        king = selectedKing,
                        showPortraitThumbnail = true,
                    )
                    Button(
                        onClick = { onMatchWithPhoto(selectedKing) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("wide_match_photo_button"),
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PhotoCamera,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(stringResource(R.string.match_with_photo))
                    }
                    DeveloperCreditCard()
                }
            }
        } else {
            // Compact portrait list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                item {
                    TibebHeroBanner(
                        title = stringResource(R.string.archive_header_title),
                        subtitle = stringResource(R.string.archive_header_subtitle),
                    )
                }

                items(uiState.kings, key = { it.id }) { king ->
                    KingArchiveCard(
                        king = king,
                        isSelected = false,
                        onClick = { onOpenKingDetailCompact(king) },
                    )
                }

                item {
                    DeveloperCreditCard()
                }
            }
        }
    }
}

@Composable
private fun KingArchiveCard(
    king: King,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .testTag("archive_card_${king.id}"),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f)
            } else {
                MaterialTheme.colorScheme.surface
            },
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 2.dp),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            },
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = king.portraitResId),
                contentDescription = "${king.fullName} ${stringResource(R.string.king_portrait_cd)}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(88.dp)
                    .aspectRatio(3f / 4f)
                    .clip(MaterialTheme.shapes.medium),
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                ) {
                    Text(
                        text = king.eraYears,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    )
                }

                Text(
                    text = king.fullName,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = king.biography,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = stringResource(R.string.view_full_history),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }
    }
}
