package cz.jakubricar.sovik.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = M2_Blue600,
    primaryVariant = M2_Blue700,
    secondary = M2_Cyan700,
    secondaryVariant = M2_Cyan700,
    background = M2_BlueGray1000,
    surface = M2_BlueGray1000,
    onPrimary = Color.Black,
    onSecondary = Color.White
)

private val LightColorPalette = lightColors(
    primary = M2_Blue700,
    primaryVariant = M2_Blue800,
    secondary = M2_GoogleBlueLight,
    secondaryVariant = M2_GoogleBlueDark,
    background = M2_Gray50,
    onPrimary = Color.White,
    onSecondary = M2_GoogleOnBlueLight
)

@Composable
fun SovikTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}