package com.iwelogic.settings.presentation

import android.content.*
import android.net.*
import androidx.appcompat.app.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import androidx.core.os.*
import androidx.hilt.navigation.compose.*
import com.iwelogic.settings.presentation.models.*
import com.iwelogic.settings.presentation.views.*


@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {

    viewModel.obtainEvent(SettingsEvent.CheckLanguage)
    val state by viewModel.state
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { uiEffect ->
            when (uiEffect) {
                is SettingsUIEffect.ChangeLanguage -> {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(uiEffect.code))
                }
                is SettingsUIEffect.RateApp -> {
                    val uri: Uri = Uri.parse("market://details?id=${uiEffect.packageName}")
                    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                    goToMarket.setPackage("com.android.vending")
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                    runCatching {
                        context.startActivity(goToMarket)
                    }.onFailure {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=${uiEffect.packageName}")
                            )
                        )
                    }
                }
                is SettingsUIEffect.SendEmail -> {
                    val selectorIntent = Intent(Intent.ACTION_SENDTO)
                    selectorIntent.setData(Uri.parse("mailto:"))
                    val emailIntent = Intent(Intent.ACTION_SEND)
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(uiEffect.email))
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject))
                    emailIntent.putExtra(Intent.EXTRA_TEXT, String.format(context.getString(R.string.version), state.versionName))
                    emailIntent.selector = selectorIntent
                    context.startActivity(Intent.createChooser(emailIntent, "Send email..."))
                }
                is SettingsUIEffect.ShareApp -> {
                    val sendIntent = Intent()
                    sendIntent.setAction(Intent.ACTION_SEND)
                    sendIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        String.format(context.getString(R.string.share_text), "https://play.google.com/store/apps/details?id=${uiEffect.packageName}")
                    )
                    sendIntent.setType("text/plain")
                    context.startActivity(sendIntent)
                }
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(stringResource(R.string.language), style = MaterialTheme.typography.titleMedium)
        TextSwitcher(
            selectedIndex = state.language.index,
            items = Language.entries.map { it.title },
            onSelectionChange = { index ->
                viewModel.obtainEvent(SettingsEvent.OnClickChangeLanguage(index))

            },
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(stringResource(R.string.theme), style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))

        TextSwitcher(
            selectedIndex = Theme.entries.first { it.isDark == state.isDarkTheme }.index,
            items = Theme.entries.map { stringResource(it.title) },
            onSelectionChange = { index ->
                viewModel.obtainEvent(SettingsEvent.OnClickChangeTheme(Theme.entries.first { it.index == index }.isDark))
            },
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(stringResource(R.string.notifications), style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))

        TextSwitcher(
            selectedIndex = NotificationStatus.entries.first { it.isOn == state.isNotificationOn }.index,
            items = NotificationStatus.entries.map { stringResource(it.title) },
            onSelectionChange = { index ->
                viewModel.obtainEvent(SettingsEvent.OnClickChangeNotificationStatus(NotificationStatus.entries.first { it.index == index }.isOn))
            },
            modifier = Modifier.padding(top = 8.dp)
        )

        SettingsButton(
            icon = R.drawable.message,
            text = stringResource(R.string.write_to_support),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            viewModel.obtainEvent(SettingsEvent.OnClickSupport)
        }

        SettingsButton(
            icon = R.drawable.share,
            text = stringResource(R.string.share_app),
        ) {
            viewModel.obtainEvent(SettingsEvent.OnClickShare)
        }

        SettingsButton(
            icon = R.drawable.rate,
            text = stringResource(R.string.rate_app),
        ) {
            viewModel.obtainEvent(SettingsEvent.OnClickRate)
        }

        Spacer(modifier = Modifier.weight(1.0f))

        Text(
            text = String.format(stringResource(R.string.version), state.versionName),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

