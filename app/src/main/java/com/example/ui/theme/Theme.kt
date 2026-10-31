package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val InvitationLightColorScheme = lightColorScheme(
  primary = GoldPrimary,
  onPrimary = Color.White,
  primaryContainer = RoseLight,
  onPrimaryContainer = GoldDark,
  secondary = RosePrimary,
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFFDF0ED),
  onSecondaryContainer = RoseDark,
  tertiary = SageBorder,
  onTertiary = Color(0xFF203015),
  background = ParchmentBg,
  onBackground = TextDark,
  surface = ParchmentCard,
  onSurface = TextDark,
  surfaceVariant = Color(0xFFF4EFEA),
  onSurfaceVariant = TextMuted,
  outline = Color(0xFFE0D8D0)
)

private val InvitationDarkColorScheme = darkColorScheme(
  primary = GoldAccent,
  onPrimary = Color(0xFF2D1800),
  primaryContainer = Color(0xFF5A3105),
  onPrimaryContainer = GoldShimmer,
  secondary = RosePrimary,
  onSecondary = Color.White,
  tertiary = SageEnvelopeDark,
  background = Color(0xFF141724),
  onBackground = Color(0xFFF1F1F1),
  surface = EventCardNight,
  onSurface = Color(0xFFF1F1F1),
  surfaceVariant = Color(0xFF232538),
  onSurfaceVariant = Color(0xFFB0B3C6),
  outline = EventCardBorder
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = false,
  // Force bespoke wedding aesthetic colors rather than generic dynamic wallpaper colors
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) InvitationDarkColorScheme else InvitationLightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
