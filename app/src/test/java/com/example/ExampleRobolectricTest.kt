package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.InvitationRepository
import com.example.ui.InvitationViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Rehan Studio Invitation", appName)
  }

  @Test
  fun `invitation viewModel lifecycle and rsvp workflow`() {
    val repository = InvitationRepository()
    val viewModel = InvitationViewModel(repository)

    // Initial state: envelope is closed
    assertFalse(viewModel.isEnvelopeOpened.value)

    // Open envelope
    viewModel.openEnvelope()
    assertTrue(viewModel.isEnvelopeOpened.value)

    // Submit RSVP
    val initialRsvpCount = viewModel.rsvps.value.size
    viewModel.submitRsvp(
      guestName = "Zubair Ahmed",
      phone = "+91 99000 11223",
      isAttending = true,
      guestCount = 2,
      dietary = "Traditional Halal",
      blessingMessage = "Heartfelt congratulations!"
    )

    assertEquals(initialRsvpCount + 1, viewModel.rsvps.value.size)
    assertEquals("Zubair Ahmed", viewModel.rsvps.value.first().guestName)
  }
}
