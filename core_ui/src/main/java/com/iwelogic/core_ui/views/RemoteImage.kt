package com.iwelogic.core_ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import coil3.compose.*

@Composable
fun RemoteImage(url: String, modifier: Modifier = Modifier) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = url,
        error = {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text("error\nloading", textAlign = TextAlign.Center)
            }
        },
        loading = {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(40.dp))
            }
        },
        contentScale = ContentScale.Crop,
        contentDescription = ""
    )
}