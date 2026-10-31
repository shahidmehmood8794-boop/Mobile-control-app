package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.InvitationData
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ParchmentCard
import com.example.ui.theme.TextDark
import kotlinx.coroutines.delay

@Composable
fun CountdownTimerSection(
  invitation: InvitationData,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

  LaunchedEffect(Unit) {
    while (true) {
      delay(1000L)
      currentTime = System.currentTimeMillis()
    }
  }

  val remainingMillis = (invitation.weddingTimestampMillis - currentTime).coerceAtLeast(0L)
  val days = remainingMillis / (1000 * 60 * 60 * 24)
  val hours = (remainingMillis / (1000 * 60 * 60)) % 24
  val minutes = (remainingMillis / (1000 * 60)) % 60
  val seconds = (remainingMillis / 1000) % 60

  fun addToCalendar() {
    try {
      val intent = Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        putExtra(CalendarContract.Events.TITLE, "${invitation.groomName} & ${invitation.brideName}'s Wedding")
        putExtra(
          CalendarContract.Events.DESCRIPTION,
          "${invitation.invitationQuote}\nMain Venue: ${invitation.mainVenueName}, ${invitation.mainVenueAddress}"
        )
        putExtra(CalendarContract.Events.EVENT_LOCATION, invitation.mainVenueName)
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, invitation.weddingTimestampMillis)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, invitation.weddingTimestampMillis + 5 * 3600 * 1000)
      }
      context.startActivity(intent)
    } catch (_: Exception) {
      Toast.makeText(context, "Calendar application not found", Toast.LENGTH_SHORT).show()
    }
  }

  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = ParchmentCard),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("countdown_section_card")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Default.HourglassBottom,
          contentDescription = null,
          tint = GoldPrimary,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "THE COUNTDOWN TO FOREVER",
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.Bold,
          color = GoldDark,
          letterSpacing = 1.2.sp
        )
      }

      Text(
        text = "Every Second Brings Us Closer",
        fontFamily = FontFamily.Serif,
        style = MaterialTheme.typography.titleMedium,
        color = TextDark,
        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
      )

      // Time boxes: Days, Hours, Minutes, Seconds
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        TimeBox(value = days.toInt(), label = "DAYS", tag = "countdown_days")
        TimeBox(value = hours.toInt(), label = "HOURS", tag = "countdown_hours")
        TimeBox(value = minutes.toInt(), label = "MINS", tag = "countdown_minutes")
        TimeBox(value = seconds.toInt(), label = "SECS", tag = "countdown_seconds")
      }

      Spacer(modifier = Modifier.height(18.dp))

      Button(
        onClick = { addToCalendar() },
        colors = ButtonDefaults.buttonColors(
          containerColor = Color(0xFFF7EDE2),
          contentColor = GoldDark
        ),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, GoldLight),
        modifier = Modifier.testTag("add_calendar_button")
      ) {
        Icon(
          imageVector = Icons.Default.CalendarToday,
          contentDescription = null,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Save Wedding Date to Calendar",
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  }
}

@Composable
fun TimeBox(
  value: Int,
  label: String,
  tag: String
) {
  Box(
    modifier = Modifier
      .width(68.dp)
      .shadow(3.dp, RoundedCornerShape(12.dp))
      .clip(RoundedCornerShape(12.dp))
      .background(Color.White)
      .border(1.dp, Color(0xFFE9EDC9), RoundedCornerShape(12.dp))
      .padding(vertical = 10.dp, horizontal = 4.dp)
      .testTag(tag),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      AnimatedContent(
        targetState = value,
        transitionSpec = {
          slideInVertically { height -> height } togetherWith slideOutVertically { height -> -height }
        },
        label = "time_box_anim"
      ) { targetValue ->
        Text(
          text = String.format("%02d", targetValue),
          fontSize = 22.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Serif,
          color = GoldPrimary,
          textAlign = TextAlign.Center
        )
      }
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = label,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF777777),
        letterSpacing = 0.5.sp
      )
    }
  }
}
