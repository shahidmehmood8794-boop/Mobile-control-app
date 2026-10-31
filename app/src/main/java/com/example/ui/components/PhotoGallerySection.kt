package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.PhotoItem
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ParchmentCard
import com.example.ui.theme.TextDark

@Composable
fun PhotoGallerySection(
  photos: List<PhotoItem>,
  selectedPhoto: PhotoItem?,
  onSelectPhoto: (PhotoItem?) -> Unit,
  modifier: Modifier = Modifier
) {
  // Lightbox Modal Dialog
  selectedPhoto?.let { photo ->
    Dialog(
      onDismissRequest = { onSelectPhoto(null) },
      properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(Color.Black.copy(alpha = 0.94f))
          .padding(16.dp),
        contentAlignment = Alignment.Center
      ) {
        IconButton(
          onClick = { onSelectPhoto(null) },
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(16.dp)
            .background(Color.White.copy(alpha = 0.2f), CircleShape)
            .testTag("close_lightbox_btn")
        ) {
          Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
        }

        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.fillMaxWidth()
        ) {
          Image(
            painter = painterResource(id = photo.drawableRes),
            contentDescription = photo.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
              .fillMaxWidth()
              .height(420.dp)
              .clip(RoundedCornerShape(16.dp))
          )

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = photo.title,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.White
          )

          Text(
            text = photo.subtitle,
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            color = GoldLight,
            modifier = Modifier.padding(top = 4.dp)
          )

          Text(
            text = "Captured & Curated by Rehan Photo Studio",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 8.dp)
          )
        }
      }
    }
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .testTag("gallery_section")
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(bottom = 6.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Collections,
        contentDescription = null,
        tint = GoldPrimary,
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "MOMENTS & PHOTOGRAPHY REEL",
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.2.sp,
        color = GoldDark
      )
    }

    Text(
      text = "Captured with Love by Rehan Photo Studio",
      fontFamily = FontFamily.Serif,
      style = MaterialTheme.typography.titleLarge,
      color = TextDark,
      modifier = Modifier.padding(bottom = 14.dp)
    )

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(14.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      items(photos) { photo ->
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = ParchmentCard),
          elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
          modifier = Modifier
            .width(260.dp)
            .clickable { onSelectPhoto(photo) }
            .testTag("gallery_item_${photo.id}")
        ) {
          Column {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
            ) {
              Image(
                painter = painterResource(id = photo.drawableRes),
                contentDescription = photo.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )

              // Gradient scrim
              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .background(
                    Brush.verticalGradient(
                      listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                    )
                  )
              )

              // Zoom hint badge
              Surface(
                color = Color.Black.copy(alpha = 0.5f),
                shape = CircleShape,
                modifier = Modifier
                  .align(Alignment.TopEnd)
                  .padding(8.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.ZoomIn,
                  contentDescription = "Expand",
                  tint = Color.White,
                  modifier = Modifier
                    .padding(6.dp)
                    .size(16.dp)
                )
              }

              Text(
                text = photo.title,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontSize = 15.sp,
                color = Color.White,
                modifier = Modifier
                  .align(Alignment.BottomStart)
                  .padding(12.dp)
              )
            }

            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = photo.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF666666),
                fontSize = 12.sp
              )
            }
          }
        }
      }
    }
  }
}
