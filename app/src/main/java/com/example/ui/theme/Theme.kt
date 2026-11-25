package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

data class CyberSemanticColors(
    val safe: Color = SafeEmerald,
    val safeContainer: Color = SafeEmeraldContainer,
    val caution: Color = CautionAmber,
    val cautionContainer: Color = CautionAmberContainer,
    val alert: Color = AlertCrimson,
    val alertContainer: Color = AlertCrimsonContainer,
    val cardBorder: Color = CyberBorder,
    val elevatedCard: Color = CyberNavy800,
    val deepBackdrop: Color = CyberNavy950
)

val LocalCyberColors = staticCompositionLocalOf { CyberSemanticColors() }

private val CyberShieldDarkColorScheme = darkColorScheme(
    primary = ElectricBlue500,
    onPrimary = Color.White,
    primaryContainer = ElectricBlue900,
    onPrimaryContainer = ElectricBlue400,
    secondary = CyberCyan400,
    onSecondary = CyberNavy950,
    secondaryContainer = CyberCyan900,
    onSecondaryContainer = CyberCyan400,
    tertiary = SafeEmerald,
    onTertiary = CyberNavy950,
    tertiaryContainer = SafeEmeraldContainer,
    onTertiaryContainer = SafeEmerald,
    error = AlertCrimson,
    onError = Color.White,
    errorContainer = AlertCrimsonContainer,
    onErrorContainer = AlertCrimson,
    background = CyberNavy900,
    onBackground = SlateTextPrimary,
    surface = CyberNavy850,
    onSurface = SlateTextPrimary,
    surfaceVariant = CyberNavy800,
    onSurfaceVariant = SlateTextSecondary,
    surfaceContainerLowest = CyberNavy950,
    surfaceContainerLow = CyberNavy900,
    surfaceContainer = CyberNavy850,
    surfaceContainerHigh = CyberNavy800,
    surfaceContainerHighest = CyberNavy700,
    outline = CyberBorder,
    outlineVariant = Color(0xFF1A2942)
)

val CyberShieldShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun CyberShieldTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalCyberColors provides CyberSemanticColors()) {
        MaterialTheme(
            colorScheme = CyberShieldDarkColorScheme,
            typography = CyberShieldTypography,
            shapes = CyberShieldShapes,
            content = content
        )
    }
}
