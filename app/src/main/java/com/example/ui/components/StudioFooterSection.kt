package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.InvitationData
import com.example.ui.theme.EventCardNight
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.RosePrimary

@Composable
fun StudioFooterSection(
  invitation: InvitationData,
  onOpenCustomizer: () -> Unit,
  onResealEnvelope: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  fun callStudio() {
    try {
      val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${invitation.studioPhone.replace(" ", "")}"))
      context.startActivity(intent)
    } catch (_: Exception) {
      Toast.makeText(context, "Cannot open dialer", Toast.LENGTH_SHORT).show()
    }
  }

  fun openWhatsApp() {
    try {
      val cleanPhone = invitation.studioPhone.replace("+", "").replace(" ", "")
      val uri = Uri.parse("https://api.whatsapp.com/send?phone=$cleanPhone&text=" + Uri.encode("Hello Rehan Photo Studio! I would like to inquire about digital invitations & wedding photography packages."))
      val intent = Intent(Intent.ACTION_VIEW, uri)
      context.startActivity(intent)
    } catch (_: Exception) {
      Toast.makeText(context, "WhatsApp not available", Toast.LENGTH_SHORT).show()
    }
  }

  fun sendEmail() {
    try {
      val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${invitation.studioEmail}")).apply {
        putExtra(Intent.EXTRA_SUBJECT, "Photography & Invitation Inquiry - Rehan Photo Studio")
      }
      context.startActivity(intent)
    } catch (_: Exception) {
      Toast.makeText(context, "Email app not found", Toast.LENGTH_SHORT).show()
    }
  }

  fun shareInvitation() {
    try {
      val shareText = """
        ✨ You're Cordially Invited! ✨
        ${invitation.groomName} & ${invitation.brideName} are tying the knot!
        
        "${invitation.invitationQuote}"
        
        📍 Venue: ${invitation.mainVenueName}
        ${invitation.mainVenueAddress}
        
        Crafted with love by ${invitation.studioName}
      """.trimIndent()

      val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
      }
      context.startActivity(Intent.createChooser(sendIntent, "Share Special Digital Invitation"))
    } catch (_: Exception) {
      Toast.makeText(context, "Cannot share invitation", Toast.LENGTH_SHORT).show()
    }
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .testTag("studio_footer_section"),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Card(
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = EventCardNight),
      elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Studio Logo Badge
        Box(
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(
              Brush.radialGradient(
                listOf(GoldAccent, GoldPrimary, GoldDark)
              )
            )
            .border(2.dp, GoldLight, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = invitation.studioName.uppercase(),
          fontFamily = FontFamily.Serif,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          letterSpacing = 1.5.sp,
          color = GoldAccent
        )

        Text(
          text = "SPECIAL DIGITAL INVITATION & CINEMATIC PHOTOGRAPHY",
          style = MaterialTheme.typography.labelSmall,
          color = Color(0xFFC9CCD8),
          letterSpacing = 1.sp,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
        )

        Text(
          text = "\"${invitation.studioTagline}\"",
          style = MaterialTheme.typography.bodySmall,
          fontStyle = FontStyle.Italic,
          color = Color(0xFFE4D5C7),
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(bottom = 18.dp)
        )

        // Contact Buttons Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = { callStudio() },
            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
              .weight(1f)
              .testTag("call_studio_btn")
          ) {
            Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Call", fontSize = 12.sp)
          }

          Button(
            onClick = { openWhatsApp() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
              .weight(1f)
              .testTag("whatsapp_studio_btn")
          ) {
            Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.White)
            Spacer(modifier = Modifier.width(4.dp))
            Text("WhatsApp", fontSize = 12.sp, color = Color.White)
          }

          Button(
            onClick = { sendEmail() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D405B)),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
              .weight(1f)
              .testTag("email_studio_btn")
          ) {
            Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Email", fontSize = 12.sp)
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Secondary Actions: Share Invitation & Customize
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = { shareInvitation() },
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldLight),
            modifier = Modifier
              .weight(1f)
              .testTag("share_invitation_btn")
          ) {
            Icon(Icons.Default.Share, contentDescription = null, tint = GoldLight, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Share Invite", fontSize = 12.sp, color = GoldLight)
          }

          OutlinedButton(
            onClick = { onOpenCustomizer() },
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent),
            modifier = Modifier
              .weight(1f)
              .testTag("customize_invitation_btn")
          ) {
            Icon(Icons.Default.Edit, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Customize", fontSize = 12.sp, color = GoldAccent)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Re-Seal Envelope action
    OutlinedButton(
      onClick = onResealEnvelope,
      shape = RoundedCornerShape(20.dp),
      border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD4C8B8)),
      modifier = Modifier.testTag("reseal_envelope_btn")
    ) {
      Icon(Icons.Default.MailOutline, contentDescription = null, tint = GoldDark, modifier = Modifier.size(16.dp))
      Spacer(modifier = Modifier.width(6.dp))
      Text("Close & Re-seal Envelope", fontSize = 12.sp, color = GoldDark)
    }

    Spacer(modifier = Modifier.height(12.dp))

    Text(
      text = "Designed & Produced with ❤️ for Rehan Photo Studio\nrehanedits78666@gmail.com",
      fontSize = 11.sp,
      color = Color(0xFF888888),
      textAlign = TextAlign.Center,
      lineHeight = 16.sp,
      modifier = Modifier.padding(bottom = 24.dp)
    )
  }
}
