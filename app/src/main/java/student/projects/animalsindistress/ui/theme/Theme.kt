package student.projects.animalsindistress.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light color scheme matching the React website
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = PrimaryForeground,
    primaryContainer = PrimaryGlow,
    onPrimaryContainer = PrimaryForeground,
    
    secondary = Secondary,
    onSecondary = SecondaryForeground,
    secondaryContainer = Secondary,
    onSecondaryContainer = SecondaryForeground,
    
    tertiary = Accent,
    onTertiary = AccentForeground,
    
    background = Background,
    onBackground = Foreground,
    
    surface = Card,
    onSurface = CardForeground,
    surfaceVariant = Muted,
    onSurfaceVariant = MutedForeground,
    
    error = Destructive,
    onError = DestructiveForeground,
    
    outline = Border,
    outlineVariant = Input,
    
    surfaceTint = Color.Transparent
)

@Composable
fun AnimalsInDistressTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Always use light theme to match the React website
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}