package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.InvitationData
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldPrimary

@Composable
fun CustomizerDialog(
  invitation: InvitationData,
  onDismiss: () -> Unit,
  onSave: (groom: String, bride: String, quote: String, timestamp: Long, venue: String, address: String, phone: String) -> Unit
) {
  var groomName by remember { mutableStateOf(invitation.groomName) }
  var brideName by remember { mutableStateOf(invitation.brideName) }
  var quote by remember { mutableStateOf(invitation.invitationQuote) }
  var venueName by remember { mutableStateOf(invitation.mainVenueName) }
  var venueAddress by remember { mutableStateOf(invitation.mainVenueAddress) }
  var studioPhone by remember { mutableStateOf(invitation.studioPhone) }

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(20.dp),
    containerColor = Color(0xFFFDFBF7),
    title = {
      Text(
        text = "Studio Invitation Customizer",
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        color = GoldDark,
        fontSize = 20.sp
      )
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
      ) {
        Text(
          text = "Personalize names, venues, and contacts for this client invitation:",
          style = MaterialTheme.typography.bodySmall,
          color = Color(0xFF666666)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = groomName,
          onValueChange = { groomName = it },
          label = { Text("Groom's Name") },
          singleLine = true,
          colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("customizer_groom_input")
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = brideName,
          onValueChange = { brideName = it },
          label = { Text("Bride's Name") },
          singleLine = true,
          colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("customizer_bride_input")
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = quote,
          onValueChange = { quote = it },
          label = { Text("Invitation Quote") },
          maxLines = 3,
          colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("customizer_quote_input")
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = venueName,
          onValueChange = { venueName = it },
          label = { Text("Main Venue Name") },
          singleLine = true,
          colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("customizer_venue_input")
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = venueAddress,
          onValueChange = { venueAddress = it },
          label = { Text("Venue Address") },
          singleLine = true,
          colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = studioPhone,
          onValueChange = { studioPhone = it },
          label = { Text("Rehan Studio Phone / WhatsApp") },
          singleLine = true,
          colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldPrimary),
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          onSave(
            groomName.ifBlank { "Rehan" },
            brideName.ifBlank { "Zoya" },
            quote.ifBlank { invitation.invitationQuote },
            invitation.weddingTimestampMillis,
            venueName.ifBlank { invitation.mainVenueName },
            venueAddress.ifBlank { invitation.mainVenueAddress },
            studioPhone.ifBlank { invitation.studioPhone }
          )
        },
        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.testTag("save_customizer_btn")
      ) {
        Text("Save & Apply")
      }
    },
    dismissButton = {
      OutlinedButton(
        onClick = onDismiss,
        shape = RoundedCornerShape(16.dp)
      ) {
        Text("Cancel")
      }
    }
  )
}
