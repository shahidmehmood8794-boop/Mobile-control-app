package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.EventItem
import com.example.data.model.InvitationData
import com.example.data.model.PhotoItem
import com.example.data.model.RsvpEntry
import com.example.data.model.ScratchReward
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldShimmer
import com.example.ui.theme.ParchmentBg
import com.example.ui.theme.ParchmentCard
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun InvitationMainContent(
  invitation: InvitationData,
  events: List<EventItem>,
  rsvps: List<RsvpEntry>,
  galleryPhotos: List<PhotoItem>,
  scratchReward: ScratchReward,
  selectedPhoto: PhotoItem?,
  isMusicPlaying: Boolean,
  onToggleMusic: () -> Unit,
  onSelectPhoto: (PhotoItem?) -> Unit,
  onSubmitRsvp: (name: String, phone: String, isAttending: Boolean, guestCount: Int, dietary: String, blessing: String) -> Unit,
  showRsvpSuccess: Boolean,
  onDismissRsvpSuccess: () -> Unit,
  onOpenCustomizer: () -> Unit,
  onResealEnvelope: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  val musicInfinite = rememberInfiniteTransition(label = "music_pulse")
  val musicScale by musicInfinite.animateFloat(
    initialValue = 1.0f,
    targetValue = if (isMusicPlaying) 1.15f else 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(600),
      repeatMode = RepeatMode.Reverse
    ),
    label = "music_scale"
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(ParchmentBg)
      .verticalScroll(scrollState)
      .padding(horizontal = 18.dp)
      .testTag("invitation_main_content"),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Spacer(modifier = Modifier.height(16.dp))

    // Top Utility Header: Music Ambient Toggle & Customize Shortcut
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Music Player Capsule
      Surface(
        onClick = onToggleMusic,
        shape = RoundedCornerShape(20.dp),
        color = if (isMusicPlaying) GoldPrimary else Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, GoldLight),
        shadowElevation = 2.dp,
        modifier = Modifier.testTag("music_toggle_btn")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Icon(
            imageVector = if (isMusicPlaying) Icons.Default.MusicNote else Icons.Default.MusicOff,
            contentDescription = "Music toggle",
            tint = if (isMusicPlaying) Color.White else GoldDark,
            modifier = Modifier
              .size(16.dp)
              .scale(musicScale)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (isMusicPlaying) "Melody Playing" else "Play Music",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isMusicPlaying) Color.White else GoldDark
          )
        }
      }

      // Studio Customizer & Re-seal
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
          onClick = onOpenCustomizer,
          modifier = Modifier
            .background(Color.White, CircleShape)
            .border(1.dp, GoldLight.copy(alpha = 0.5f), CircleShape)
            .size(36.dp)
            .testTag("top_customize_btn")
        ) {
          Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Customize",
            tint = GoldDark,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Header Title
    Text(
      text = invitation.headerSubtitle,
      style = MaterialTheme.typography.labelSmall,
      color = GoldDark,
      letterSpacing = 2.sp,
      fontWeight = FontWeight.SemiBold,
      modifier = Modifier.padding(bottom = 6.dp)
    )

    Text(
      text = invitation.familyIntro,
      style = MaterialTheme.typography.labelMedium,
      color = TextMuted,
      letterSpacing = 1.sp
    )

    // Couple Names (Playfair serif styling)
    Text(
      text = "${invitation.groomName} & ${invitation.brideName}",
      fontFamily = FontFamily.Serif,
      fontSize = 34.sp,
      fontWeight = FontWeight.Bold,
      color = RosePrimary,
      textAlign = TextAlign.Center,
      modifier = Modifier
        .padding(vertical = 10.dp)
        .testTag("couple_names_display")
    )

    // Hero Couple Banner Card
    Card(
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = ParchmentCard),
      elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("couple_hero_card")
    ) {
      Box(modifier = Modifier.fillMaxWidth()) {
        Image(
          painter = painterResource(id = R.drawable.img_couple_hero),
          contentDescription = "Wedding Couple",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .clip(RoundedCornerShape(18.dp))
        )

        // Gradient overlay
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .background(
              Brush.verticalGradient(
                listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
              )
            )
        )

        // Save The Date Badge
        Surface(
          color = GoldPrimary,
          shape = RoundedCornerShape(14.dp),
          shadowElevation = 4.dp,
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 12.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
          ) {
            Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "SAVE THE DATE",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              letterSpacing = 1.5.sp
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Poetic Quote
    Text(
      text = "\"${invitation.invitationQuote}\"",
      style = MaterialTheme.typography.bodyMedium,
      fontFamily = FontFamily.Serif,
      fontStyle = FontStyle.Italic,
      color = Color(0xFF555555),
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
    )

    Spacer(modifier = Modifier.height(18.dp))

    // 1. Live Countdown Timer Section
    CountdownTimerSection(invitation = invitation)

    Spacer(modifier = Modifier.height(22.dp))

    // 2. Interactive Scratch Card Section
    ScratchCardSection(reward = scratchReward)

    Spacer(modifier = Modifier.height(22.dp))

    // 3. Events & Itinerary Section (Mehndi, Nikah, Walima)
    EventsSection(events = events)

    Spacer(modifier = Modifier.height(22.dp))

    // 4. Photo Gallery & Moments Reel
    PhotoGallerySection(
      photos = galleryPhotos,
      selectedPhoto = selectedPhoto,
      onSelectPhoto = onSelectPhoto
    )

    Spacer(modifier = Modifier.height(22.dp))

    // 5. RSVP & Warm Blessings Guestbook
    RsvpSection(
      rsvps = rsvps,
      onSubmitRsvp = onSubmitRsvp,
      showSuccessDialog = showRsvpSuccess,
      onDismissSuccessDialog = onDismissRsvpSuccess,
      groomName = invitation.groomName,
      brideName = invitation.brideName
    )

    Spacer(modifier = Modifier.height(24.dp))

    // 6. Studio Footer & Contact Actions
    StudioFooterSection(
      invitation = invitation,
      onOpenCustomizer = onOpenCustomizer,
      onResealEnvelope = onResealEnvelope
    )

    Spacer(modifier = Modifier.height(24.dp))
  }
}
