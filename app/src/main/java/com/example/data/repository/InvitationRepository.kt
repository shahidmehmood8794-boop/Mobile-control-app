package com.example.data.repository

import com.example.R
import com.example.data.model.EventItem
import com.example.data.model.InvitationData
import com.example.data.model.PhotoItem
import com.example.data.model.RsvpEntry
import com.example.data.model.ScratchReward
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class InvitationRepository {

  private val _invitation = MutableStateFlow(InvitationData())
  val invitation: StateFlow<InvitationData> = _invitation.asStateFlow()

  val defaultEvents = listOf(
    EventItem(
      id = "event_1",
      title = "Mehndi & Sangeet Night",
      subtitle = "An Evening of Henna, Music & Laughter",
      date = "Friday, Nov 27, 2026",
      time = "7:00 PM Onwards",
      venue = "The Grand Pearl Banquet - Crystal Hall",
      dressCode = "Festive Shades of Yellow, Mustard & Emerald",
      description = "Join us for traditional live folk dhol, soulful melodies, exquisite henna artistry, and high-energy dance performances by family and friends.",
      calendarStartHourOffset = 19,
      emoji = "🌿"
    ),
    EventItem(
      id = "event_2",
      title = "Baraat & Nikah Ceremony",
      subtitle = "The Auspicious Union & Sacred Vows",
      date = "Saturday, Nov 28, 2026",
      time = "11:30 AM Afternoon",
      venue = "Royal Palace Lawn & Grand Marquee",
      dressCode = "Royal Traditional & Pastel Elegance",
      description = "Witness the grand Baraat procession followed by the sacred Nikah ceremony, heartfelt prayers, and a lavish royal celebratory feast.",
      calendarStartHourOffset = 11,
      emoji = "💍"
    ),
    EventItem(
      id = "event_3",
      title = "Walima & Grand Reception",
      subtitle = "A Night of Stars, Feasting & Celebrations",
      date = "Sunday, Nov 29, 2026",
      time = "8:00 PM Gala Night",
      venue = "The Imperial Grand Ballroom",
      dressCode = "Formal Black-Tie & Glamorous Evening Wear",
      description = "Celebrate the newly-wedded couple with cake cutting, live violin & ghazal orchestrations, studio portrait sessions, and a grand gourmet dinner.",
      calendarStartHourOffset = 20,
      emoji = "🥂"
    )
  )

  private val _rsvps = MutableStateFlow(
    listOf(
      RsvpEntry(
        id = "1",
        guestName = "Farooq & Yasmin Family",
        phone = "+91 98111 22334",
        isAttending = true,
        guestCount = 4,
        dietary = "Traditional Halal",
        blessingMessage = "Mubarak ho! May Allah bless Rehan and Zoya with perpetual happiness, abundance, and warmth in every step of their new chapter! Can't wait!",
        timestamp = System.currentTimeMillis() - 86400000L * 2
      ),
      RsvpEntry(
        id = "2",
        guestName = "Dr. Sameer & Ayla Khan",
        phone = "+91 98450 99887",
        isAttending = true,
        guestCount = 2,
        dietary = "Traditional Halal",
        blessingMessage = "Heartiest congratulations to the wonderful couple! So excited to witness your special union. May your bond grow sweeter with every passing year.",
        timestamp = System.currentTimeMillis() - 86400000L
      ),
      RsvpEntry(
        id = "3",
        guestName = "Aman Sharma & Friends",
        phone = "+91 97123 45678",
        isAttending = true,
        guestCount = 3,
        dietary = "Pure Vegetarian",
        blessingMessage = "Congratulations Rehan bhai! The groom of the year! Excited for the Sangeet dance face-off and delicious food!",
        timestamp = System.currentTimeMillis() - 3600000L * 5
      )
    )
  )
  val rsvps: StateFlow<List<RsvpEntry>> = _rsvps.asStateFlow()

  val galleryPhotos = listOf(
    PhotoItem(
      id = "p1",
      title = "Eternal Promise",
      subtitle = "Pre-Wedding Signature Shoot by Rehan Studio",
      drawableRes = R.drawable.img_couple_hero
    ),
    PhotoItem(
      id = "p2",
      title = "Celebration Showers",
      subtitle = "Petals, laughter and pure happiness",
      drawableRes = R.drawable.img_celebration_moments
    ),
    PhotoItem(
      id = "p3",
      title = "Rehan Photo Studio",
      subtitle = "Artistic cameras, warm vibes & master craftsmanship",
      drawableRes = R.drawable.img_studio_reel
    )
  )

  val scratchReward = ScratchReward()

  fun addRsvp(entry: RsvpEntry) {
    _rsvps.update { current ->
      listOf(entry) + current
    }
  }

  fun updateInvitation(updated: InvitationData) {
    _invitation.value = updated
  }
}
