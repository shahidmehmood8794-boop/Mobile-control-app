package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.InvitationViewModel
import com.example.ui.components.CustomizerDialog
import com.example.ui.components.EnvelopeScreen
import com.example.ui.components.InvitationMainContent
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ParchmentBg

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        InvitationApp()
      }
    }
  }
}

@Composable
fun InvitationApp(
  viewModel: InvitationViewModel = viewModel()
) {
  val isEnvelopeOpened by viewModel.isEnvelopeOpened.collectAsStateWithLifecycle()
  val invitation by viewModel.invitation.collectAsStateWithLifecycle()
  val rsvps by viewModel.rsvps.collectAsStateWithLifecycle()
  val isMusicPlaying by viewModel.isMusicPlaying.collectAsStateWithLifecycle()
  val selectedPhoto by viewModel.selectedPhotoForLightbox.collectAsStateWithLifecycle()
  val showCustomizer by viewModel.showCustomizerDialog.collectAsStateWithLifecycle()
  val showRsvpSuccess by viewModel.showRsvpSuccessDialog.collectAsStateWithLifecycle()

  // Handle system back button to re-seal envelope
  BackHandler(enabled = isEnvelopeOpened) {
    if (selectedPhoto != null) {
      viewModel.selectPhoto(null)
    } else {
      viewModel.sealEnvelope()
    }
  }

  Surface(
    modifier = Modifier
      .fillMaxSize()
      .background(ParchmentBg)
      .windowInsetsPadding(WindowInsets.safeDrawing),
    color = MaterialTheme.colorScheme.background
  ) {
    Crossfade(
      targetState = isEnvelopeOpened,
      animationSpec = tween(durationMillis = 600),
      label = "screen_transition"
    ) { opened ->
      if (!opened) {
        EnvelopeScreen(
          invitation = invitation,
          onOpenComplete = { viewModel.openEnvelope() }
        )
      } else {
        InvitationMainContent(
          invitation = invitation,
          events = viewModel.events,
          rsvps = rsvps,
          galleryPhotos = viewModel.galleryPhotos,
          scratchReward = viewModel.scratchReward,
          selectedPhoto = selectedPhoto,
          isMusicPlaying = isMusicPlaying,
          onToggleMusic = { viewModel.toggleMusic() },
          onSelectPhoto = { viewModel.selectPhoto(it) },
          onSubmitRsvp = { name, phone, attending, count, dietary, blessing ->
            viewModel.submitRsvp(name, phone, attending, count, dietary, blessing)
          },
          showRsvpSuccess = showRsvpSuccess,
          onDismissRsvpSuccess = { viewModel.dismissRsvpSuccess() },
          onOpenCustomizer = { viewModel.setCustomizerVisible(true) },
          onResealEnvelope = { viewModel.sealEnvelope() }
        )
      }
    }

    if (showCustomizer) {
      CustomizerDialog(
        invitation = invitation,
        onDismiss = { viewModel.setCustomizerVisible(false) },
        onSave = { groom, bride, quote, timestamp, venue, address, phone ->
          viewModel.updateInvitationDetails(groom, bride, quote, timestamp, venue, address, phone)
        }
      )
    }
  }
}
