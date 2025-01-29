package com.iwelogic.core.views

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import coil3.compose.*

@Composable
fun RemoteImage(url: String, modifier: Modifier){
    SubcomposeAsyncImage(
        modifier = modifier,
        model = url,
        error = {
            Text("error")
        },
        loading = {
            CircularProgressIndicator()
        },
        contentDescription = ""
    )
}