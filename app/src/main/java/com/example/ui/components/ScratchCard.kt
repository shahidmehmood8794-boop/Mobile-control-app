package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ScratchReward
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldShimmer
import com.example.ui.theme.ParchmentCard
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.TextDark
import com.example.util.FestiveAudioHelper

@Composable
fun ScratchCardSection(
  reward: ScratchReward,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var isRevealed by remember { mutableStateOf(false) }
  var scratchPercent by remember { mutableFloatStateOf(0f) }
  val scratchedPaths = remember { mutableStateListOf<List<Offset>>() }
  var copiedToClipboard by remember { mutableStateOf(false) }

  fun resetScratch() {
    scratchedPaths.clear()
    scratchPercent = 0f
    isRevealed = false
    copiedToClipboard = false
  }

  fun copyCode() {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Rehan Studio Voucher", reward.voucherCode)
    clipboard.setPrimaryClip(clip)
    copiedToClipboard = true
    Toast.makeText(context, "Voucher code copied to clipboard!", Toast.LENGTH_SHORT).show()
  }

  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = ParchmentCard),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("scratch_section_card")
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
          imageVector = Icons.Default.Stars,
          contentDescription = null,
          tint = GoldPrimary,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "SPECIAL BLESSING SCRATCH CARD",
          style = MaterialTheme.typography.labelLarge,
          color = GoldDark,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.2.sp
        )
      }

      Text(
        text = "Scratch to Reveal Your Exclusive Surprise",
        style = MaterialTheme.typography.titleMedium,
        fontFamily = FontFamily.Serif,
        color = TextDark,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
      )

      // The Interactive Scratch Canvas Container
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(210.dp)
          .clip(RoundedCornerShape(16.dp))
          .border(2.dp, GoldLight, RoundedCornerShape(16.dp))
          .testTag("scratch_box_container"),
        contentAlignment = Alignment.Center
      ) {

        // Underneath Layer: Revealed Gift & Blessing
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                listOf(Color(0xFFFFFBF2), Color(0xFFF9F1E2))
              )
            )
            .padding(16.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Surface(
              color = GoldPrimary,
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.padding(bottom = 6.dp)
            ) {
              Text(
                text = "✨ REHAN PHOTO STUDIO VIP GIFT ✨",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }

            Text(
              text = reward.discountOffer,
              fontFamily = FontFamily.Serif,
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = GoldDark,
              textAlign = TextAlign.Center
            )

            Text(
              text = reward.specialPrivilege,
              style = MaterialTheme.typography.bodySmall,
              fontSize = 12.sp,
              color = TextDark,
              textAlign = TextAlign.Center,
              modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
            )

            // Promo Voucher Code pill
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color.White,
              border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent),
              shadowElevation = 2.dp,
              modifier = Modifier
                .padding(vertical = 4.dp)
                .testTag("copy_voucher_btn")
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                  .padding(horizontal = 12.dp, vertical = 6.dp)
              ) {
                Text(
                  text = reward.voucherCode,
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 13.sp,
                  letterSpacing = 1.sp,
                  color = GoldPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                  imageVector = if (copiedToClipboard) Icons.Default.Check else Icons.Default.ContentCopy,
                  contentDescription = "Copy code",
                  tint = if (copiedToClipboard) Color(0xFF2E7D32) else GoldDark,
                  modifier = Modifier.size(16.dp)
                )
              }
            }

            Text(
              text = reward.secretWish,
              style = MaterialTheme.typography.bodySmall,
              fontStyle = FontStyle.Italic,
              fontSize = 11.sp,
              color = RoseDark,
              textAlign = TextAlign.Center,
              modifier = Modifier.padding(top = 4.dp)
            )
          }
        }

        // Top Layer: Metallic Scratch Foil
        if (!isRevealed) {
          var currentStroke by remember { mutableStateOf<List<Offset>>(emptyList()) }

          Canvas(
            modifier = Modifier
              .fillMaxSize()
              .pointerInput(Unit) {
                detectDragGestures(
                  onDragStart = { offset ->
                    currentStroke = listOf(offset)
                  },
                  onDrag = { change, _ ->
                    change.consume()
                    val newPoint = change.position
                    currentStroke = currentStroke + newPoint

                    // Compute rough scratch progress
                    if (scratchedPaths.size + currentStroke.size / 6 > 40) {
                      scratchPercent = 1.0f
                      isRevealed = true
                      FestiveAudioHelper.playChime()
                    } else {
                      scratchPercent = ((scratchedPaths.size * 8 + currentStroke.size) / 250f).coerceIn(0f, 1f)
                    }
                  },
                  onDragEnd = {
                    if (currentStroke.isNotEmpty()) {
                      scratchedPaths.add(currentStroke)
                      currentStroke = emptyList()
                    }
                    if (scratchPercent >= 0.35f) {
                      isRevealed = true
                      FestiveAudioHelper.playChime()
                    }
                  }
                )
              }
              .testTag("scratch_foil_canvas")
          ) {
            // Draw metallic gold shimmer foil
            val foilBrush = Brush.linearGradient(
              colors = listOf(GoldLight, GoldDark, GoldAccent, GoldShimmer, GoldLight),
              start = Offset(0f, 0f),
              end = Offset(size.width, size.height)
            )
            drawRect(brush = foilBrush)

            // Draw scratches (erasing strokes)
            val strokeWidthPx = 48.dp.toPx()
            scratchedPaths.forEach { stroke ->
              if (stroke.size > 1) {
                val path = Path().apply {
                  moveTo(stroke.first().x, stroke.first().y)
                  for (i in 1 until stroke.size) {
                    lineTo(stroke[i].x, stroke[i].y)
                  }
                }
                drawPath(
                  path = path,
                  color = Color.Transparent,
                  blendMode = BlendMode.Clear,
                  style = Stroke(
                    width = strokeWidthPx,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                  )
                )
              }
            }

            if (currentStroke.size > 1) {
              val currentPath = Path().apply {
                moveTo(currentStroke.first().x, currentStroke.first().y)
                for (i in 1 until currentStroke.size) {
                  lineTo(currentStroke[i].x, currentStroke[i].y)
                }
              }
              drawPath(
                path = currentPath,
                color = Color.Transparent,
                blendMode = BlendMode.Clear,
                style = Stroke(
                  width = strokeWidthPx,
                  cap = StrokeCap.Round,
                  join = StrokeJoin.Round
                )
              )
            }
          }

          // Shimmer instruction badge overlay if hardly scratched
          if (scratchedPaths.isEmpty() && currentStroke.isEmpty()) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center,
              modifier = Modifier.padding(16.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(44.dp)
                  .background(Color.White.copy(alpha = 0.25f), CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.TouchApp,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(24.dp)
                )
              }
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "✨ SCRATCH HERE ✨",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                letterSpacing = 1.sp,
                color = Color.White
              )
              Text(
                text = "Swipe your finger across the gold foil to reveal!",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Progress & Action Row
      if (!isRevealed) {
        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Scratch progress: ${(scratchPercent * 100).toInt()}%",
              style = MaterialTheme.typography.labelSmall,
              color = GoldDark
            )
            Text(
              text = "Auto-unlocks at 35%",
              style = MaterialTheme.typography.labelSmall,
              color = TextDark.copy(alpha = 0.6f)
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          LinearProgressIndicator(
            progress = { scratchPercent },
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = GoldPrimary,
            trackColor = Color(0xFFE9EDC9)
          )
          Spacer(modifier = Modifier.height(10.dp))
          OutlinedButton(
            onClick = {
              isRevealed = true
              scratchPercent = 1f
              FestiveAudioHelper.playChime()
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.testTag("reveal_all_button")
          ) {
            Text("Reveal Card Now", fontSize = 12.sp, color = GoldDark)
          }
        }
      } else {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          Button(
            onClick = { copyCode() },
            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.testTag("claim_voucher_btn")
          ) {
            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(if (copiedToClipboard) "Code Copied!" else "Copy Voucher Code", fontSize = 12.sp)
          }

          OutlinedButton(
            onClick = { resetScratch() },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.testTag("reset_scratch_btn")
          ) {
            Icon(Icons.Default.LockReset, contentDescription = null, modifier = Modifier.size(16.dp), tint = GoldDark)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Scratch Again", fontSize = 12.sp, color = GoldDark)
          }
        }
      }
    }
  }
}
