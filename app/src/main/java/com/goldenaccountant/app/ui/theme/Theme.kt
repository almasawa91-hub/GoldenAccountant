package com.goldenaccountant.app.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Scheme = lightColorScheme(
    primary = Color(0xFF1E40AF),
    secondary = Color(0xFFD4AF37),
    tertiary = Color(0xFFA67C00),
    background = Color(0xFFF8FAFC),
    surface = Color.White,
    error = Color(0xFFDC2626)
)

@Composable fun GoldenTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Scheme, content = content)
}
