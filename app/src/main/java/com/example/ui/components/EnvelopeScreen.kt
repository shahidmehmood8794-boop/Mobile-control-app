package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.InvitationData
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldShimmer
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.SageBorder
import com.example.ui.theme.SageEnvelopeBg
import com.example.ui.theme.SageEnvelopeDark
import com.example.ui.theme.TextDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun EnvelopeScreen(
  invitation: InvitationData,
  onOpenComplete: () -> Unit,
  modifier: Modifier = Modifier
) {
  var isFlapOpen by remember { mutableStateOf(false) }
  var isCardSlidOut by remember { mutableStateOf(false) }
  var showConfetti by remember { mutableStateOf(false) }
  val scope = rememberCoroutineScope()

  // Continuous gentle pulse on wax seal before opening
  val infiniteTransition = rememberInfiniteTransition(label = "seal_pulse")
  val sealPulseScale by infiniteTransition.animateFloat(
    initialValue = 1.0f,
    targetValue = 1.08f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "seal_scale"
  )

  // Flap rotation: 0 (closed) to 180 (flipped open)
  val flapAngle by animateFloatAsState(
    targetValue = if (isFlapOpen) 180f else 0f,
    animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
    label = "flap_rotation"
  )

  // Card slide out translation Y
  val cardSlideOffset by animateFloatAsState(
    targetValue = if (isCardSlidOut) -160f else 0f,
    animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
    label = "card_slide"
  )

  fun triggerOpen() {
    if (!isFlapOpen) {
      isFlapOpen = true
      showConfetti = true
      scope.launch {
        delay(400)
        isCardSlidOut = true
        delay(1200)
        onOpenComplete()
      }
    }
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.linearGradient(
          colors = listOf(SageEnvelopeBg, SageEnvelopeDark, Color(0xFFABC098)),
          start = Offset(0f, 0f),
          end = Offset(1000f, 1600f)
        )
      )
      .testTag("envelope_screen"),
    contentAlignment = Alignment.Center
  ) {

    // Ambient floating decorative sparkles
    SparkleOverlay(showParticles = showConfetti)

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      // Header tag
      Surface(
        color = Color.White.copy(alpha = 0.85f),
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 2.dp,
        modifier = Modifier.padding(bottom = 16.dp)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = RosePrimary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "REHAN PHOTO STUDIO PRESENTS",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = GoldDark,
            letterSpacing = 1.5.sp
          )
        }
      }

      Text(
        text = "✨ You're Cordially Invited ✨",
        style = MaterialTheme.typography.headlineLarge,
        fontFamily = FontFamily.Serif,
        color = TextDark,
        textAlign = TextAlign.Center
      )

      Text(
        text = "${invitation.groomName} & ${invitation.brideName}'s Wedding Celebration",
        style = MaterialTheme.typography.titleMedium,
        fontFamily = FontFamily.Serif,
        fontStyle = FontStyle.Italic,
        color = RoseDark,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 4.dp, bottom = 28.dp)
      )

      // Royal Envelope Structure
      Box(
        modifier = Modifier
          .width(320.dp)
          .height(230.dp)
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
          ) { triggerOpen() }
          .testTag("envelope_container"),
        contentAlignment = Alignment.Center
      ) {

        // The Sliding Invitation Card inside
        Box(
          modifier = Modifier
            .width(280.dp)
            .height(180.dp)
            .offset { IntOffset(0, cardSlideOffset.dp.roundToPx()) }
            .shadow(if (isCardSlidOut) 12.dp else 2.dp, RoundedCornerShape(12.dp))
            .background(
              Brush.verticalGradient(
                listOf(Color(0xFFFFFDF9), Color(0xFFFBF4E8))
              ),
              RoundedCornerShape(12.dp)
            )
            .border(1.5.dp, GoldLight, RoundedCornerShape(12.dp))
            .padding(16.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "BISMILLAH-IR-RAHMAN-IR-RAHIM",
              style = MaterialTheme.typography.labelSmall,
              color = GoldDark,
              fontSize = 9.sp,
              letterSpacing = 1.sp,
              fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "${invitation.groomName} & ${invitation.brideName}",
              fontFamily = FontFamily.Serif,
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp,
              color = GoldPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Together with their families request your blessings",
              style = MaterialTheme.typography.bodyMedium,
              fontSize = 11.sp,
              color = TextDark,
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                .background(GoldLight.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Icon(Icons.Default.Star, contentDescription = null, tint = GoldDark, modifier = Modifier.size(12.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Save The Auspicious Date", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = GoldDark)
            }
          }
        }

        // Envelope Base Pocket
        EnvelopeBaseCanvas(
          modifier = Modifier
            .fillMaxSize()
            .shadow(10.dp, RoundedCornerShape(14.dp))
        )

        // Envelope Top Flap
        EnvelopeFlapCanvas(
          flapAngle = flapAngle,
          modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
            .align(Alignment.TopCenter)
        )

        // Wax Seal Button centered on envelope flap edge
        if (!isFlapOpen || flapAngle < 90f) {
          Box(
            modifier = Modifier
              .align(Alignment.Center)
              .offset(y = 10.dp)
              .scale(if (!isFlapOpen) sealPulseScale else 1f)
              .clickable { triggerOpen() }
              .testTag("wax_seal_button"),
            contentAlignment = Alignment.Center
          ) {
            WaxSealGraphic(groomInitial = invitation.groomName.take(1))
          }
        }
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Action prompt
      if (!isFlapOpen) {
        Button(
          onClick = { triggerOpen() },
          colors = ButtonDefaults.buttonColors(
            containerColor = GoldPrimary,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(25.dp),
          elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
          modifier = Modifier
            .padding(horizontal = 24.dp)
            .testTag("open_invitation_button")
        ) {
          Icon(Icons.Default.LockOpen, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Tap Seal to Open Invitation",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
          )
        }
      } else {
        Button(
          onClick = { onOpenComplete() },
          colors = ButtonDefaults.buttonColors(
            containerColor = GoldDark,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(25.dp),
          elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
          modifier = Modifier.testTag("proceed_invitation_button")
        ) {
          Text("Enter Full Celebration", fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "Touch anywhere on envelope or wax seal to unveil",
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 12.sp,
        color = Color(0xFF4A5540)
      )
    }
  }
}

@Composable
fun WaxSealGraphic(groomInitial: String) {
  Box(
    modifier = Modifier
      .size(68.dp)
      .shadow(8.dp, CircleShape)
      .clip(CircleShape)
      .background(
        Brush.radialGradient(
          colors = listOf(GoldAccent, GoldLight, GoldDark),
          center = Offset(20f, 20f),
          radius = 70f
        )
      )
      .border(2.5.dp, GoldShimmer.copy(alpha = 0.8f), CircleShape),
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      // Inner decorative ring
      drawCircle(
        color = Color.White.copy(alpha = 0.35f),
        radius = size.minDimension / 2 - 8.dp.toPx(),
        style = Stroke(width = 1.5.dp.toPx())
      )
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = groomInitial.ifEmpty { "R" },
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        color = Color.White
      )
      Text(
        text = "OPEN",
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 9.sp,
        letterSpacing = 1.sp,
        color = Color.White.copy(alpha = 0.9f)
      )
    }
  }
}

@Composable
fun EnvelopeBaseCanvas(modifier: Modifier = Modifier) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height

    // Main envelope body background
    val bodyPath = Path().apply {
      moveTo(0f, 0f)
      lineTo(w, 0f)
      lineTo(w, h)
      lineTo(0f, h)
      close()
    }
    drawPath(bodyPath, color = Color(0xFFFAF7F2), style = Fill)

    // Side pocket folds
    val leftFold = Path().apply {
      moveTo(0f, 0f)
      lineTo(w * 0.48f, h * 0.55f)
      lineTo(0f, h)
      close()
    }
    drawPath(leftFold, color = Color(0xFFF3ECE0), style = Fill)
    drawPath(leftFold, color = SageBorder.copy(alpha = 0.5f), style = Stroke(width = 1.dp.toPx()))

    val rightFold = Path().apply {
      moveTo(w, 0f)
      lineTo(w * 0.52f, h * 0.55f)
      lineTo(w, h)
      close()
    }
    drawPath(rightFold, color = Color(0xFFEFE7DB), style = Fill)
    drawPath(rightFold, color = SageBorder.copy(alpha = 0.5f), style = Stroke(width = 1.dp.toPx()))

    // Bottom pocket fold
    val bottomFold = Path().apply {
      moveTo(0f, h)
      lineTo(w * 0.5f, h * 0.45f)
      lineTo(w, h)
      close()
    }
    drawPath(bottomFold, color = Color(0xFFEBE1D2), style = Fill)
    drawPath(bottomFold, color = GoldLight.copy(alpha = 0.6f), style = Stroke(width = 1.5.dp.toPx()))
  }
}

@Composable
fun EnvelopeFlapCanvas(
  flapAngle: Float,
  modifier: Modifier = Modifier
) {
  Canvas(
    modifier = modifier
      .rotate(if (flapAngle > 90f) 180f else 0f)
  ) {
    val w = size.width
    val h = size.height

    val flapPath = Path().apply {
      moveTo(0f, 0f)
      lineTo(w, 0f)
      lineTo(w * 0.5f, h)
      close()
    }

    val flapBrush = Brush.verticalGradient(
      colors = if (flapAngle > 90f) {
        listOf(Color(0xFFE2D6C5), Color(0xFFEDE3D6))
      } else {
        listOf(Color(0xFFFBF6EE), Color(0xFFF2E6D5))
      }
    )
    drawPath(flapPath, brush = flapBrush, style = Fill)
    drawPath(flapPath, color = SageBorder, style = Stroke(width = 1.5.dp.toPx()))
  }
}

@Composable
fun SparkleOverlay(showParticles: Boolean) {
  val random = remember { Random(42) }
  val particlePositions = remember {
    List(18) {
      Offset(
        x = random.nextFloat(),
        y = random.nextFloat()
      )
    }
  }

  val infinite = rememberInfiniteTransition(label = "particles")
  val floatAnim by infinite.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(4000, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "float"
  )

  Canvas(modifier = Modifier.fillMaxSize()) {
    val w = size.width
    val h = size.height
    particlePositions.forEachIndexed { i, pos ->
      val animatedY = ((pos.y + floatAnim * 0.1f * if (i % 2 == 0) 1 else -1) % 1.0f) * h
      val x = pos.x * w
      val radius = (if (i % 3 == 0) 4.dp else 2.5.dp).toPx()
      val alpha = if (showParticles) 0.85f else 0.4f
      drawCircle(
        color = if (i % 2 == 0) GoldShimmer.copy(alpha = alpha) else Color.White.copy(alpha = alpha),
        center = Offset(x, animatedY),
        radius = radius
      )
    }
  }
}
