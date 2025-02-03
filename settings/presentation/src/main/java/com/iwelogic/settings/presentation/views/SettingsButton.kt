package com.iwelogic.settings.presentation.views

import androidx.annotation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.iwelogic.settings.presentation.R

@Composable
fun SettingsButton(@DrawableRes icon: Int, text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(50.dp)
            .clickable {
                onClick.invoke()
            }) {
        Icon(
            painter = painterResource(icon),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            contentDescription = ""
        )
        Text(text, modifier = Modifier.padding(start = 8.dp))
        Spacer(modifier = Modifier.weight(1.0f))
        Icon(
            painter = painterResource(R.drawable.arrow_next),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            contentDescription = ""
        )
    }
}