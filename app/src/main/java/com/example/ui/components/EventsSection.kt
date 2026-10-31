package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Place
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EventItem
import com.example.ui.theme.EventCardBorder
import com.example.ui.theme.EventCardNight
import com.example.ui.theme.EventGoldHeader
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary

@Composable
fun EventsSection(
  events: List<EventItem>,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .testTag("events_section")
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(bottom = 6.dp)
    ) {
      Icon(
        imageVector = Icons.Default.EventNote,
        contentDescription = null,
        tint = GoldPrimary,
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "ITINERARY & CEREMONIES",
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.2.sp,
        color = GoldPrimary
      )
    }

    Text(
      text = "Join Us for Every Sacred & Joyful Celebration",
      fontFamily = FontFamily.Serif,
      style = MaterialTheme.typography.titleLarge,
      color = Color(0xFF2B2D42),
      modifier = Modifier.padding(bottom = 16.dp)
    )

    events.forEachIndexed { index, event ->
      EventCard(event = event, itemIndex = index)
      Spacer(modifier = Modifier.height(18.dp))
    }
  }
}

@Composable
fun EventCard(
  event: EventItem,
  itemIndex: Int
) {
  val context = LocalContext.current

  fun openDirections(venueName: String) {
    try {
      val mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(venueName))
      val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
      mapIntent.setPackage("com.google.android.apps.maps")
      if (mapIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(mapIntent)
      } else {
        // Fallback to web browser Google Maps
        val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(venueName))
        context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
      }
    } catch (_: Exception) {
      val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(venueName))
      context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
    }
  }

  fun addEventToCalendar(event: EventItem) {
    try {
      val intent = Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        putExtra(CalendarContract.Events.TITLE, event.title)
        putExtra(CalendarContract.Events.DESCRIPTION, "${event.subtitle}\nDress Code: ${event.dressCode}\n${event.description}")
        putExtra(CalendarContract.Events.EVENT_LOCATION, event.venue)
      }
      context.startActivity(intent)
    } catch (_: Exception) {
      Toast.makeText(context, "Could not open calendar", Toast.LENGTH_SHORT).show()
    }
  }

  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = EventCardNight),
    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("event_card_$itemIndex")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(22.dp)
    ) {
      // Header with emoji & category
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "${event.emoji}  ${event.title}",
          fontFamily = FontFamily.Serif,
          fontSize = 22.sp,
          fontWeight = FontWeight.Bold,
          color = EventGoldHeader
        )
      }

      Text(
        text = event.subtitle,
        fontSize = 13.sp,
        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
        color = Color(0xFFD8D9E8),
        modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
      )

      // Date & Time Row
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 3.dp)
      ) {
        Icon(
          imageVector = Icons.Default.CalendarToday,
          contentDescription = null,
          tint = GoldAccent,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = event.date,
          color = Color(0xFFF0F1F5),
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
          imageVector = Icons.Default.AccessTime,
          contentDescription = null,
          tint = GoldAccent,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = event.time,
          color = Color(0xFFF0F1F5),
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium
        )
      }

      // Venue Row
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Place,
          contentDescription = null,
          tint = GoldAccent,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = event.venue,
          color = Color(0xFFECEEF4),
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium
        )
      }

      // Dress Code Pill
      Surface(
        color = Color(0xFF383B54),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(top = 6.dp, bottom = 10.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Checkroom,
            contentDescription = null,
            tint = GoldLight,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Dress Code: ${event.dressCode}",
            fontSize = 11.sp,
            color = Color(0xFFF7EDE2),
            fontWeight = FontWeight.Medium
          )
        }
      }

      // Description
      Text(
        text = event.description,
        fontSize = 13.sp,
        lineHeight = 19.sp,
        color = Color(0xFFE0E0E0),
        modifier = Modifier.padding(bottom = 16.dp)
      )

      // Action Buttons Row: Location & Calendar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Location button (.location-btn style from CSS)
        Button(
          onClick = { openDirections(event.venue) },
          colors = ButtonDefaults.buttonColors(
            containerColor = GoldLight,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(25.dp),
          elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
          modifier = Modifier
            .weight(1f)
            .testTag("direction_btn_$itemIndex")
        ) {
          Icon(Icons.Default.NearMe, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Get Directions", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }

        OutlinedButton(
          onClick = { addEventToCalendar(event) },
          shape = RoundedCornerShape(25.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent),
          modifier = Modifier
            .weight(1f)
            .testTag("calendar_btn_$itemIndex")
        ) {
          Icon(Icons.Default.CalendarToday, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(15.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Add to Calendar", fontSize = 12.sp, color = GoldAccent, fontWeight = FontWeight.Medium)
        }
      }
    }
  }
}
