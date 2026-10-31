package com.example.data.model

import androidx.annotation.DrawableRes
import com.example.R

data class InvitationData(
  val groomName: String = "Rehan",
  val brideName: String = "Zoya",
  val headerSubtitle: String = "SPECIAL DIGITAL INVITATION",
  val familyIntro: String = "TOGETHER WITH THEIR FAMILIES",
  val invitationQuote: String = "Invite you to celebrate their union of eternal love and joyful togetherness",
  val weddingTimestampMillis: Long = System.currentTimeMillis() + (28L * 24 * 3600 * 1000) + (14L * 3600 * 1000), // ~28 days ahead
  val mainVenueName: String = "The Grand Pearl Palace & Royal Lawns",
  val mainVenueAddress: String = "Boulevard Road, Civil Lines, Luxury District",
  val studioName: String = "Rehan Photo Studio",
  val studioPhone: String = "+91 98765 43210",
  val studioEmail: String = "rehanedits78666@gmail.com",
  val studioInstagram: String = "rehanphotostudio",
  val studioTagline: String = "Capturing your priceless moments into everlasting memories",
  val musicPlaying: Boolean = false
)

data class EventItem(
  val id: String,
  val title: String,
  val subtitle: String,
  val date: String,
  val time: String,
  val venue: String,
  val dressCode: String,
  val description: String,
  val calendarStartHourOffset: Int,
  val calendarDurationHours: Int = 4,
  val emoji: String = "✨"
)

data class RsvpEntry(
  val id: String,
  val guestName: String,
  val phone: String,
  val isAttending: Boolean,
  val guestCount: Int,
  val dietary: String,
  val blessingMessage: String,
  val timestamp: Long = System.currentTimeMillis()
)

data class ScratchReward(
  val title: String = "VIP GUEST BLESSING & VOUCHER",
  val voucherCode: String = "REHAN-STUDIO-VIP25",
  val discountOffer: String = "25% OFF Wedding & Family Photography",
  val specialPrivilege: String = "Reserved Front-Row Family Seating & Complimentary Portrait Keepsake",
  val secretWish: String = "\"May your presence bring double the smiles and lifelong blessings to our journey!\""
)

data class PhotoItem(
  val id: String,
  val title: String,
  val subtitle: String,
  @DrawableRes val drawableRes: Int
)
