package com.example.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.R
import com.example.model.King
import com.example.ui.components.DeveloperCreditCard
import com.example.ui.components.KingStorySection
import com.example.ui.components.TibebHeroBanner
import com.example.viewmodel.KingsUiState
import com.example.viewmodel.SelectedPhotoSource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PhotoMatcherScreen(
    uiState: KingsUiState,
    onPhotoPicked: (android.net.Uri) -> Unit,
    onSampleSelected: (King) -> Unit,
    onClearPhoto: () -> Unit,
    onSelectKing: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                onPhotoPicked(uri)
            }
        },
    )

    var dropdownExpanded by remember { mutableStateOf(false) }

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

            // Photo Upload & Preview Card
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
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    // Primary Photo Picker Button (Zero-permission PickVisualMedia)
                    Button(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 52.dp)
                            .testTag("pick_photo_button"),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AddPhotoAlternate,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            text = if (uiState.hasPhoto) {
                                stringResource(R.string.btn_change_photo)
                            } else {
                                stringResource(R.string.btn_pick_photo)
                            },
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    // Preview Frame
                    when (val source = uiState.photoSource) {
                        is SelectedPhotoSource.None -> {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(MaterialTheme.shapes.medium)
                                    .clickable {
                                        photoPickerLauncher.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    }
                                    .testTag("empty_photo_preview"),
                                shape = MaterialTheme.shapes.medium,
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                                border = BorderStroke(
                                    1.5.dp,
                                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.45f),
                                ),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp, vertical = 28.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.PhotoLibrary,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                            modifier = Modifier
                                                .padding(14.dp)
                                                .size(28.dp),
                                        )
                                    }
                                    Text(
                                        text = stringResource(R.string.photo_preview_empty_title),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                    )
                                    Text(
                                        text = stringResource(R.string.photo_preview_empty_subtitle),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                        }

                        is SelectedPhotoSource.UserGalleryUri -> {
                            PhotoPreviewBox(
                                onClear = onClearPhoto,
                            ) {
                                AsyncImage(
                                    model = source.uri,
                                    contentDescription = stringResource(R.string.selected_photo_cd),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(260.dp)
                                        .testTag("selected_photo_image"),
                                )
                            }
                        }

                        is SelectedPhotoSource.ArchiveSample -> {
                            PhotoPreviewBox(
                                onClear = onClearPhoto,
                            ) {
                                Image(
                                    painter = painterResource(id = source.drawableRes),
                                    contentDescription = stringResource(R.string.selected_photo_cd),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(260.dp)
                                        .testTag("selected_photo_image"),
                                )
                            }
                        }
                    }

                    // Sample Portraits Row for instant exploration even without device photos
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.btn_use_sample_portrait),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            uiState.kings.forEach { king ->
                                val isCurrentSample =
                                    (uiState.photoSource as? SelectedPhotoSource.ArchiveSample)?.sampleKingId == king.id
                                Column(
                                    modifier = Modifier
                                        .width(74.dp)
                                        .clip(MaterialTheme.shapes.small)
                                        .clickable { onSampleSelected(king) }
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
                                            .size(56.dp)
                                            .clip(CircleShape)
                                            .border(
                                                width = if (isCurrentSample) 3.dp else 1.5.dp,
                                                color = if (isCurrentSample) {
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
                                            .replace("አፄ ", "")
                                            .replace("ቀዳማዊ ", ""),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = if (isCurrentSample) {
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
                    }

                    // King Confirmation Selector (Revealed once a photo is selected, matching HTML)
                    AnimatedVisibility(
                        visible = uiState.hasPhoto,
                        enter = fadeIn() + expandVertically(
                            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                        ),
                        exit = fadeOut() + shrinkVertically(),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Text(
                                text = stringResource(R.string.selector_prompt),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                            )

                            ExposedDropdownMenuBox(
                                expanded = dropdownExpanded,
                                onExpandedChange = { dropdownExpanded = !dropdownExpanded },
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                OutlinedTextField(
                                    value = uiState.selectedKing?.shortName
                                        ?: stringResource(R.string.selector_placeholder),
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = dropdownExpanded
                                        )
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(
                                            alpha = 0.65f
                                        ),
                                    ),
                                    shape = MaterialTheme.shapes.medium,
                                    modifier = Modifier
                                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                        .fillMaxWidth()
                                        .testTag("king_selector_dropdown"),
                                )

                                ExposedDropdownMenu(
                                    expanded = dropdownExpanded,
                                    onDismissRequest = { dropdownExpanded = false },
                                ) {
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = stringResource(R.string.selector_placeholder),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            )
                                        },
                                        onClick = {
                                            onSelectKing(null)
                                            dropdownExpanded = false
                                        },
                                    )
                                    uiState.kings.forEach { king ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = king.shortName,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                )
                                            },
                                            onClick = {
                                                onSelectKing(king.id)
                                                dropdownExpanded = false
                                            },
                                            modifier = Modifier.testTag("dropdown_king_${king.id}"),
                                        )
                                    }
                                }
                            }

                            // Quick confirmation chips for 1-tap accessibility
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                uiState.kings.forEach { king ->
                                    val selected = uiState.selectedKingId == king.id
                                    FilterChip(
                                        selected = selected,
                                        onClick = {
                                            onSelectKing(if (selected) null else king.id)
                                        },
                                        label = { Text(king.shortName) },
                                        leadingIcon = if (selected) {
                                            {
                                                Icon(
                                                    imageVector = Icons.Filled.CheckCircle,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(18.dp),
                                                )
                                            }
                                        } else null,
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        ),
                                        modifier = Modifier
                                            .minimumInteractiveComponentSize()
                                            .testTag("chip_king_${king.id}"),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Revealed Story Card
            AnimatedVisibility(
                visible = uiState.hasPhoto && uiState.selectedKing != null,
                enter = fadeIn() + expandVertically(
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                ),
                exit = fadeOut() + shrinkVertically(),
            ) {
                uiState.selectedKing?.let { king ->
                    KingStorySection(
                        king = king,
                        showPortraitThumbnail = true,
                        modifier = Modifier.testTag("king_story_section"),
                    )
                }
            }

            DeveloperCreditCard()

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PhotoPreviewBox(
    onClear: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.End,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.medium,
                )
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            content()
        }

        OutlinedButton(
            onClick = onClear,
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .testTag("clear_photo_button"),
            shape = MaterialTheme.shapes.small,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(stringResource(R.string.btn_clear_photo))
        }
    }
}
