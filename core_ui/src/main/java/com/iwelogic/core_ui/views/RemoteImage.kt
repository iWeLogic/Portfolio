package com.iwelogic.core_ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import coil3.compose.*

@Composable
fun RemoteImage(url: String, modifier: Modifier){
    SubcomposeAsyncImage(
        modifier = modifier,
        model = url,
        error = {
            Box(contentAlignment = Alignment.Center) {
                Text("error\nloading")
            }
        },
        loading = {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(40.dp))
            }
        },
        contentDescription = ""
    )
}