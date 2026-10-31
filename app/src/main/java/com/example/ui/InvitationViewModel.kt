package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.EventItem
import com.example.data.model.InvitationData
import com.example.data.model.PhotoItem
import com.example.data.model.RsvpEntry
import com.example.data.model.ScratchReward
import com.example.data.repository.InvitationRepository
import com.example.util.FestiveAudioHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class InvitationViewModel(
  private val repository: InvitationRepository = InvitationRepository()
) : ViewModel() {

  val invitation: StateFlow<InvitationData> = repository.invitation
  val events: List<EventItem> = repository.defaultEvents
  val rsvps: StateFlow<List<RsvpEntry>> = repository.rsvps
  val galleryPhotos: List<PhotoItem> = repository.galleryPhotos
  val scratchReward: ScratchReward = repository.scratchReward

  private val _isEnvelopeOpened = MutableStateFlow(false)
  val isEnvelopeOpened: StateFlow<Boolean> = _isEnvelopeOpened.asStateFlow()

  private val _isMusicPlaying = MutableStateFlow(false)
  val isMusicPlaying: StateFlow<Boolean> = _isMusicPlaying.asStateFlow()

  private val _selectedPhotoForLightbox = MutableStateFlow<PhotoItem?>(null)
  val selectedPhotoForLightbox: StateFlow<PhotoItem?> = _selectedPhotoForLightbox.asStateFlow()

  private val _showCustomizerDialog = MutableStateFlow(false)
  val showCustomizerDialog: StateFlow<Boolean> = _showCustomizerDialog.asStateFlow()

  private val _showRsvpSuccessDialog = MutableStateFlow(false)
  val showRsvpSuccessDialog: StateFlow<Boolean> = _showRsvpSuccessDialog.asStateFlow()

  fun openEnvelope() {
    FestiveAudioHelper.playChime()
    _isEnvelopeOpened.value = true
  }

  fun sealEnvelope() {
    _isEnvelopeOpened.value = false
  }

  fun toggleMusic() {
    if (_isMusicPlaying.value) {
      FestiveAudioHelper.stopAmbientMusic { _isMusicPlaying.value = false }
    } else {
      FestiveAudioHelper.startAmbientMusic { _isMusicPlaying.value = it }
    }
  }

  fun submitRsvp(
    guestName: String,
    phone: String,
    isAttending: Boolean,
    guestCount: Int,
    dietary: String,
    blessingMessage: String
  ) {
    if (guestName.isBlank()) return
    val newEntry = RsvpEntry(
      id = UUID.randomUUID().toString(),
      guestName = guestName.trim(),
      phone = phone.trim(),
      isAttending = isAttending,
      guestCount = guestCount,
      dietary = dietary,
      blessingMessage = blessingMessage.trim()
    )
    repository.addRsvp(newEntry)
    FestiveAudioHelper.playChime()
    _showRsvpSuccessDialog.value = true
  }

  fun dismissRsvpSuccess() {
    _showRsvpSuccessDialog.value = false
  }

  fun selectPhoto(photo: PhotoItem?) {
    _selectedPhotoForLightbox.value = photo
  }

  fun setCustomizerVisible(visible: Boolean) {
    _showCustomizerDialog.value = visible
  }

  fun updateInvitationDetails(
    groomName: String,
    brideName: String,
    quote: String,
    timestamp: Long,
    venueName: String,
    venueAddress: String,
    studioPhone: String
  ) {
    val current = invitation.value
    repository.updateInvitation(
      current.copy(
        groomName = groomName,
        brideName = brideName,
        invitationQuote = quote,
        weddingTimestampMillis = timestamp,
        mainVenueName = venueName,
        mainVenueAddress = venueAddress,
        studioPhone = studioPhone
      )
    )
    _showCustomizerDialog.value = false
  }

  override fun onCleared() {
    super.onCleared()
    FestiveAudioHelper.stopAmbientMusic()
  }
}
