package ua.insearching

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MyNewsTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette

    androidx.compose.material3.MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

private val LightColorPalette = lightColorScheme(
    primary = Color(0xFF765b0b),
    onPrimary = Color(0xFFffffff),
    primaryContainer = Color(0xFFffdf97),
    onPrimaryContainer = Color(0xFF5a4400),
    secondary = Color(0xFFf3e1bb),
    onSecondary = Color(0xFF51452a),
    tertiary = Color(0xFFcaebc6),
    onTertiary = Color(0xFFffffff),
    onTertiaryContainer = Color(0xFF314d32),
    error = Color(0xFFba1a1a),
    surface = Color(0xFFfff8f1),
    surfaceContainer = Color(0xFFf6eddf)
)

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFFe6c26c),
    onPrimary = Color(0xFF3e2e00),
    primaryContainer = Color(0xFF5a4400),
    onPrimaryContainer = Color(0xFFffdf97),
    secondary = Color(0xFF3a2f15),
    onSecondary = Color(0xFF3a2f15),
    tertiary = Color(0xFFafcfab),
    onTertiary = Color(0xFF1b361d),
    onTertiaryContainer = Color(0xFFcaebc6),
    error = Color(0xFFffb4ab),
    surface = Color(0xFF17130b),
    surfaceContainer = Color(0xFF231f17)
)