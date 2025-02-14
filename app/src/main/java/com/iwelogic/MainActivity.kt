package com.iwelogic

import android.content.*
import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.iwelogic.core_ui.*
import com.iwelogic.main.presentation.*
import com.iwelogic.projects.presentation.ui.details.*
import com.iwelogic.core_ui.theme.PortfolioTheme
import com.iwelogic.services.*
import dagger.hilt.android.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var themeHolder: ThemeHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            themeHolder.setSystemDefault(isSystemInDarkTheme())

            var isDarkTheme by remember { mutableStateOf(themeHolder.isDark) }

            LaunchedEffect(Unit) {
                themeHolder.isDarkFlow.collect {
                    isDarkTheme = it
                }
            }

            PortfolioTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val context = this
                val statusBar = MaterialTheme.colorScheme.primaryContainer.toArgb()
                val statusBarTextColor = MaterialTheme.colorScheme.onPrimaryContainer.toArgb()
                val navigationBar = MaterialTheme.colorScheme.primaryContainer.toArgb()
                context.enableEdgeToEdge(
                    statusBarStyle = if (!isDarkTheme) {
                        SystemBarStyle.light(
                            statusBar,
                            statusBarTextColor
                        )
                    } else {
                        SystemBarStyle.dark(
                            statusBar
                        )
                    },
                    navigationBarStyle = if (!isDarkTheme) {
                        SystemBarStyle.light(
                            statusBar,
                            statusBarTextColor
                        )
                    } else {
                        SystemBarStyle.dark(navigationBar)
                    }
                )

                Column {
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        enterTransition = {
                            EnterTransition.None
                        },
                        exitTransition = {
                            ExitTransition.None
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        composable("main") {
                            MainScreen(navController = navController)
                        }
                        composable(
                            route = "project/{id}",
                            deepLinks = listOf(navDeepLink {
                                uriPattern = "https://iwelogic.com/project/{id}"
                                action = Intent.ACTION_VIEW
                            }),
                            arguments = listOf(navArgument("id") { type = NavType.StringType }
                            )
                        ) {
                            ProjectDetailsScreen(it.arguments?.getString("id"), navController = navController)
                        }
                    }
                }

                if (!intent.extras?.getString(KEY_DESTINATION).isNullOrEmpty()) {
                    navController.navigate("${intent.extras?.getString(KEY_DESTINATION)}/${intent.extras?.getString(KEY_ID)}")
                    intent = null
                }
            }
        }
    }
}