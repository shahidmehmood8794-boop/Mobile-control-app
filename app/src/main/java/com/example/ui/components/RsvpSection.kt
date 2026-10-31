package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.RsvpEntry
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ParchmentCard
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RsvpSection(
  rsvps: List<RsvpEntry>,
  onSubmitRsvp: (name: String, phone: String, isAttending: Boolean, guestCount: Int, dietary: String, blessing: String) -> Unit,
  showSuccessDialog: Boolean,
  onDismissSuccessDialog: () -> Unit,
  groomName: String,
  brideName: String,
  modifier: Modifier = Modifier
) {
  var guestName by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var isAttending by remember { mutableStateOf(true) }
  var guestCount by remember { mutableIntStateOf(2) }
  var selectedDietary by remember { mutableStateOf("Traditional Halal") }
  var blessingMessage by remember { mutableStateOf("") }
  var nameError by remember { mutableStateOf(false) }

  val dietaryOptions = listOf("Traditional Halal", "Pure Vegetarian", "Continental")

  if (showSuccessDialog) {
    AlertDialog(
      onDismissRequest = onDismissSuccessDialog,
      icon = {
        Icon(
          imageVector = Icons.Default.Celebration,
          contentDescription = null,
          tint = GoldPrimary,
          modifier = Modifier.size(36.dp)
        )
      },
      title = {
        Text(
          text = "RSVP Received With Warmth!",
          fontFamily = FontFamily.Serif,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center
        )
      },
      text = {
        Text(
          text = "Thank you so much! $groomName & $brideName cherish your loving presence and heartfelt prayers. We look forward to creating everlasting memories together!",
          style = MaterialTheme.typography.bodyMedium,
          textAlign = TextAlign.Center
        )
      },
      confirmButton = {
        Button(
          onClick = {
            onDismissSuccessDialog()
            guestName = ""
            phone = ""
            blessingMessage = ""
          },
          colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
          shape = RoundedCornerShape(20.dp),
          modifier = Modifier.testTag("rsvp_success_confirm_btn")
        ) {
          Text("Ameen / Wonderful")
        }
      }
    )
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .testTag("rsvp_section")
  ) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = ParchmentCard),
      elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(22.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.HowToReg,
            contentDescription = null,
            tint = GoldPrimary,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "KINDLY RESPOND (RSVP)",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = GoldDark,
            letterSpacing = 1.2.sp
          )
        }

        Text(
          text = "Will You Be Joining Our Celebration?",
          fontFamily = FontFamily.Serif,
          style = MaterialTheme.typography.titleLarge,
          color = TextDark,
          modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
        )

        // Attendance toggle buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(14.dp))
              .background(if (isAttending) GoldPrimary else Color(0xFFF3EFEA))
              .clickable { isAttending = true }
              .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "Joyfully Accepts 🎉",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = if (isAttending) Color.White else TextDark
            )
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(14.dp))
              .background(if (!isAttending) RoseDark else Color(0xFFF3EFEA))
              .clickable { isAttending = false }
              .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "Regretfully Declines 💌",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = if (!isAttending) Color.White else TextDark
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Full Name Field
        OutlinedTextField(
          value = guestName,
          onValueChange = {
            guestName = it
            if (it.isNotBlank()) nameError = false
          },
          label = { Text("Your Full Name / Family") },
          leadingIcon = {
            Icon(Icons.Default.Person, contentDescription = null, tint = GoldDark)
          },
          isError = nameError,
          supportingText = {
            if (nameError) Text("Please enter your name")
          },
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GoldPrimary,
            unfocusedBorderColor = Color(0xFFD6CEBF)
          ),
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("rsvp_name_input")
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Phone / WhatsApp Field
        OutlinedTextField(
          value = phone,
          onValueChange = { phone = it },
          label = { Text("Phone / WhatsApp (Optional)") },
          leadingIcon = {
            Icon(Icons.Default.Phone, contentDescription = null, tint = GoldDark)
          },
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GoldPrimary,
            unfocusedBorderColor = Color(0xFFD6CEBF)
          ),
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("rsvp_phone_input")
        )

        if (isAttending) {
          Spacer(modifier = Modifier.height(14.dp))

          // Number of Guests Stepper
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Total Number of Guests",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = TextDark
              )
              Text(
                text = "Including yourself & family",
                fontSize = 11.sp,
                color = TextMuted
              )
            }

            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                .background(Color(0xFFF3EFEA), RoundedCornerShape(20.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              IconButton(
                onClick = { if (guestCount > 1) guestCount-- },
                modifier = Modifier.size(32.dp)
              ) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = GoldDark)
              }
              Text(
                text = "$guestCount",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
              )
              IconButton(
                onClick = { if (guestCount < 12) guestCount++ },
                modifier = Modifier.size(32.dp)
              ) {
                Icon(Icons.Default.Add, contentDescription = "Increase", tint = GoldDark)
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Dietary preference chips
          Text(
            text = "Dietary Preference",
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = TextDark,
            modifier = Modifier.padding(bottom = 6.dp)
          )

          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            dietaryOptions.forEach { option ->
              val isSelected = selectedDietary == option
              FilterChip(
                selected = isSelected,
                onClick = { selectedDietary = option },
                label = { Text(option, fontSize = 12.sp) },
                leadingIcon = {
                  if (isSelected) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                  } else {
                    Icon(Icons.Default.Restaurant, contentDescription = null, modifier = Modifier.size(14.dp))
                  }
                },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = GoldLight,
                  selectedLabelColor = Color.White,
                  selectedLeadingIconColor = Color.White
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Blessing note text field
        OutlinedTextField(
          value = blessingMessage,
          onValueChange = { blessingMessage = it },
          label = { Text("Your Blessings & Warm Wishes for the Couple") },
          placeholder = { Text("e.g. May your life together be blessed with endless happiness!") },
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GoldPrimary,
            unfocusedBorderColor = Color(0xFFD6CEBF)
          ),
          maxLines = 4,
          minLines = 2,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("rsvp_blessing_input")
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Submit Button
        Button(
          onClick = {
            if (guestName.isBlank()) {
              nameError = true
            } else {
              onSubmitRsvp(
                guestName,
                phone,
                isAttending,
                if (isAttending) guestCount else 0,
                selectedDietary,
                blessingMessage
              )
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
          shape = RoundedCornerShape(25.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("submit_rsvp_button")
        ) {
          Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Send RSVP & Warm Blessing",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Community Guestbook / Warm Wishes Carousel
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(bottom = 12.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = null,
        tint = RosePrimary,
        modifier = Modifier.size(18.dp)
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "GUESTBOOK & BLESSINGS (${rsvps.size})",
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        color = RoseDark
      )
    }

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      items(rsvps) { rsvp ->
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE8DFD5)),
          modifier = Modifier
            .width(260.dp)
            .padding(vertical = 4.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(32.dp)
                    .background(GoldLight.copy(alpha = 0.3f), CircleShape),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = rsvp.guestName.take(1).uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = GoldDark
                  )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Text(
                    text = rsvp.guestName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = TextDark,
                    maxLines = 1
                  )
                  Text(
                    text = if (rsvp.isAttending) "Attending (${rsvp.guestCount} guests)" else "Sent with love",
                    fontSize = 11.sp,
                    color = if (rsvp.isAttending) Color(0xFF2E7D32) else RoseDark
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = if (rsvp.blessingMessage.isNotBlank()) "\"${rsvp.blessingMessage}\"" else "\"Best wishes for a lifetime of love and happiness!\"",
              style = MaterialTheme.typography.bodySmall,
              fontStyle = FontStyle.Italic,
              fontSize = 12.sp,
              color = Color(0xFF555555),
              lineHeight = 17.sp
            )
          }
        }
      }
    }
  }
}
