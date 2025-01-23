package com.iwelogic.settings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Change language",
        )
        Text(
            text = "enable dark theme",
        )
        Text(
            text = "write to support",
        )
        Text(
            text = "share app",
        )
        Text(
            text = "rate app",
        )
    }
}