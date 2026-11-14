package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import kotlinx.coroutines.delay

private data class SupportAccountInfo(
    val id: String,
    val label: String,
    val accountNumber: String,
    val icon: ImageVector,
)

@Composable
fun DeveloperCreditCard(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val phoneNumber = stringResource(R.string.developer_phone_number)
    val telebirrLabel = stringResource(R.string.support_telebirr_label)
    val telebirrAccount = stringResource(R.string.support_telebirr_account)
    val cbeLabel = stringResource(R.string.support_cbe_label)
    val cbeAccount = stringResource(R.string.support_cbe_account)
    val abyssiniaLabel = stringResource(R.string.support_abyssinia_label)
    val abyssiniaAccount = stringResource(R.string.support_abyssinia_account)

    val accounts = remember(
        telebirrLabel,
        telebirrAccount,
        cbeLabel,
        cbeAccount,
        abyssiniaLabel,
        abyssiniaAccount,
    ) {
        listOf(
            SupportAccountInfo(
                id = "telebirr",
                label = telebirrLabel,
                accountNumber = telebirrAccount,
                icon = Icons.Filled.PhoneAndroid,
            ),
            SupportAccountInfo(
                id = "cbe",
                label = cbeLabel,
                accountNumber = cbeAccount,
                icon = Icons.Filled.AccountBalance,
            ),
            SupportAccountInfo(
                id = "abyssinia",
                label = abyssiniaLabel,
                accountNumber = abyssiniaAccount,
                icon = Icons.Filled.AccountBalance,
            ),
        )
    }

    var copiedAccountId by remember { mutableStateOf<String?>(null) }
    var showSupportDialog by remember { mutableStateOf(false) }

    LaunchedEffect(copiedAccountId) {
        if (copiedAccountId != null) {
            delay(2200)
            copiedAccountId = null
        }
    }

    fun copyAccountNumber(account: SupportAccountInfo) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        clipboard?.setPrimaryClip(ClipData.newPlainText(account.label, account.accountNumber))
        copiedAccountId = account.id
        val toastText = context.getString(
            R.string.support_copied_toast,
            account.label,
            account.accountNumber,
        )
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("developer_credit_card"),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            // Developer Credit Header with a prominent Support CTA right beside the credit message
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp),
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.developer_credit_heading),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = stringResource(R.string.developer_credit_message),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }

                Button(
                    onClick = { showSupportDialog = true },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("developer_support_cta_button"),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = stringResource(R.string.support_quick_cta),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            // Side-by-side layout on wider cards; highlighted Support Panel first on compact screens
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val isWideCard = maxWidth >= 520.dp

                if (isWideCard) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.Top,
                    ) {
                        Column(
                            modifier = Modifier.weight(0.36f),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            DeveloperPhoneButton(
                                phoneNumber = phoneNumber,
                                onClick = {
                                    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:$phoneNumber")
                                    }
                                    runCatching { context.startActivity(dialIntent) }
                                },
                            )
                        }

                        SupportAccountsPanel(
                            accounts = accounts,
                            copiedAccountId = copiedAccountId,
                            onCopyClick = ::copyAccountNumber,
                            modifier = Modifier.weight(0.64f),
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        SupportAccountsPanel(
                            accounts = accounts,
                            copiedAccountId = copiedAccountId,
                            onCopyClick = ::copyAccountNumber,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        DeveloperPhoneButton(
                            phoneNumber = phoneNumber,
                            onClick = {
                                val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:$phoneNumber")
                                }
                                runCatching { context.startActivity(dialIntent) }
                            },
                        )
                    }
                }
            }
        }
    }

    if (showSupportDialog) {
        AlertDialog(
            onDismissRequest = { showSupportDialog = false },
            modifier = Modifier.testTag("support_dialog"),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.support_dialog_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        text = stringResource(R.string.support_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    AccountHolderBanner()
                    accounts.forEach { account ->
                        SupportAccountRow(
                            account = account,
                            isCopied = copiedAccountId == account.id,
                            onCopyClick = { copyAccountNumber(account) },
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showSupportDialog = false },
                    modifier = Modifier.testTag("support_dialog_close_button"),
                ) {
                    Text(
                        text = stringResource(R.string.support_dialog_close),
                        fontWeight = FontWeight.Bold,
                    )
                }
            },
        )
    }
}

@Composable
private fun DeveloperPhoneButton(
    phoneNumber: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .minimumInteractiveComponentSize()
            .testTag("developer_phone_button"),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp),
            )
            Text(
                text = stringResource(R.string.developer_phone_display),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun SupportAccountsPanel(
    accounts: List<SupportAccountInfo>,
    copiedAccountId: String?,
    onCopyClick: (SupportAccountInfo) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.testTag("support_accounts_panel"),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.65f)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(6.dp)
                            .size(16.dp),
                    )
                }
                Text(
                    text = stringResource(R.string.support_heading),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Text(
                text = stringResource(R.string.support_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            AccountHolderBanner()

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                accounts.forEach { account ->
                    SupportAccountRow(
                        account = account,
                        isCopied = copiedAccountId == account.id,
                        onCopyClick = { onCopyClick(account) },
                    )
                }
            }
        }
    }
}

@Composable
private fun AccountHolderBanner() {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.85f),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.45f),
        ),
    ) {
        Text(
            text = stringResource(R.string.support_account_holder),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
        )
    }
}

@Composable
private fun SupportAccountRow(
    account: SupportAccountInfo,
    isCopied: Boolean,
    onCopyClick: () -> Unit,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = if (isCopied) 1.5.dp else 1.dp,
            color = if (isCopied) {
                MaterialTheme.colorScheme.tertiary
            } else {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
            },
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onCopyClick)
            .testTag("support_account_${account.id}"),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = if (isCopied) {
                    MaterialTheme.colorScheme.tertiaryContainer
                } else {
                    MaterialTheme.colorScheme.secondaryContainer
                },
            ) {
                Icon(
                    imageVector = account.icon,
                    contentDescription = null,
                    tint = if (isCopied) {
                        MaterialTheme.colorScheme.onTertiaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(20.dp),
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = account.label,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = account.accountNumber,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.6.sp,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            FilledTonalButton(
                onClick = onCopyClick,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .testTag("copy_${account.id}_button"),
                shape = MaterialTheme.shapes.small,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = if (isCopied) {
                        MaterialTheme.colorScheme.tertiaryContainer
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    contentColor = if (isCopied) {
                        MaterialTheme.colorScheme.onTertiaryContainer
                    } else {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    },
                ),
            ) {
                AnimatedContent(
                    targetState = isCopied,
                    label = "copy_state_${account.id}",
                ) { copied ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Icon(
                            imageVector = if (copied) Icons.Filled.Check else Icons.Filled.ContentCopy,
                            contentDescription = stringResource(
                                R.string.support_copy_cd,
                                account.label,
                            ),
                            modifier = Modifier.size(16.dp),
                        )
                        Text(
                            text = stringResource(
                                if (copied) R.string.support_copied_button else R.string.support_copy_button
                            ),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}
