package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = SoftCrimson,
    onPrimary = Color(0xFF4E0808),
    primaryContainer = CrimsonContainerDark,
    onPrimaryContainer = Color(0xFFFFDAD6),
    secondary = LuminousGold,
    onSecondary = Color(0xFF3E2D00),
    secondaryContainer = GoldContainerDark,
    onSecondaryContainer = Color(0xFFFBE5A2),
    tertiary = SoftEmerald,
    onTertiary = Color(0xFF003914),
    tertiaryContainer = EmeraldContainerDark,
    onTertiaryContainer = Color(0xFFC8F2D2),
    background = ObsidianBackground,
    onBackground = WarmIvoryInk,
    surface = ObsidianSurface,
    onSurface = WarmIvoryInk,
    surfaceVariant = ObsidianSurfaceVariant,
    onSurfaceVariant = WarmSandMuted,
    outline = ObsidianOutline,
)

private val LightColorScheme = lightColorScheme(
    primary = RoyalCrimson,
    onPrimary = Color.White,
    primaryContainer = CrimsonContainerLight,
    onPrimaryContainer = OnCrimsonContainerLight,
    secondary = AntiqueGold,
    onSecondary = Color.White,
    secondaryContainer = GoldContainerLight,
    onSecondaryContainer = OnGoldContainerLight,
    tertiary = EthiopianEmerald,
    onTertiary = Color.White,
    tertiaryContainer = EmeraldContainerLight,
    onTertiaryContainer = OnEmeraldContainerLight,
    background = ParchmentBackground,
    onBackground = EspressoInk,
    surface = ParchmentSurface,
    onSurface = EspressoInk,
    surfaceVariant = ParchmentSurfaceVariant,
    onSurfaceVariant = EspressoMuted,
    outline = ParchmentOutline,
)

val EthiopianShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
)

@Composable
fun EthiopianKingsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = EthiopianShapes,
        content = content,
    )
}
