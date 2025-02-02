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
import androidx.hilt.navigation.compose.*
import java.util.*

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    Column(modifier = Modifier.padding(16.dp)) {
        val context = LocalContext.current
        var selectedLanguageIndex by remember {
            mutableIntStateOf(Language.entries.firstOrNull { it.code ==  getCurrentLocale(context).language}?.index ?: 0)
        }
        val state = viewModel.state.value

        Text("Language", style = MaterialTheme.typography.titleMedium)

        TextSwitcher(
            selectedIndex = selectedLanguageIndex,
            items = Language.entries.map { it.title },
            onSelectionChange = { index ->
                selectedLanguageIndex = index
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(Language.entries.first { it.index == index }.code))
            },
            modifier = Modifier.padding(top = 8.dp)
        )

        Text("Theme", style = MaterialTheme.typography.titleMedium,  modifier = Modifier.padding(top = 16.dp))

        TextSwitcher(
            selectedIndex = Theme.entries.first { it.isDark == state.isDarkTheme }.index,
            items = Theme.entries.map { it.title },
            onSelectionChange = { index ->
                viewModel.obtainEvent(SettingsEvent.OnChangeTheme( Theme.entries.first { it.index == index }.isDark))
            },
            modifier = Modifier.padding(top = 8.dp)
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
