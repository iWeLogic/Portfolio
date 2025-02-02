package com.iwelogic.settings.presentation

import android.content.*
import androidx.appcompat.app.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import androidx.core.os.*
import java.util.*

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        val context = LocalContext.current
        var selectedIndex by remember {
            mutableIntStateOf(Language.entries.firstOrNull { it.code ==  getCurrentLocale(context).language}?.index ?: 0)
        }

        Text("Language", style = MaterialTheme.typography.titleMedium)

        TextSwitcher(
            selectedIndex = selectedIndex,
            items = Language.entries.map { it.title },
            onSelectionChange = { index ->
                selectedIndex = index
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(Language.entries.first { it.index == index }.code))
            },
            modifier = Modifier.padding(top = 8.dp)
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

fun getCurrentLocale(context: Context): Locale {
    val locales = AppCompatDelegate.getApplicationLocales()
    return locales.get(0) ?: context.resources.configuration.locales.get(0)
}
