package com.githubbrowser.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CircleAvatarImage(
    avatarUrl: String, size: Int = 110,
) {
    Card(
        modifier = Modifier
            .size(size.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Image(
            modifier = Modifier
                .size(size.dp)
                .padding(8.dp)
                .clip(CircleShape),
            painter = rememberAsyncImagePainter(avatarUrl),
            contentDescription = null,
        )
    }
}