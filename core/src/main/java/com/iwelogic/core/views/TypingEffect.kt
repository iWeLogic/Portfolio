package com.iwelogic.core.views

import androidx.compose.runtime.*
import kotlinx.coroutines.*

@Composable
fun TypingEffect(text: String, content: @Composable (String) -> Unit) {
    var displayedText by remember { mutableStateOf("") }
    LaunchedEffect(text) {
        for (i in text.indices) {
            displayedText = text.substring(0, i + 1)
            delay(50)
        }
    }
    content(displayedText)
}