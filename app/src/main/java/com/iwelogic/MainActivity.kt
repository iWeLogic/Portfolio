package com.iwelogic

import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.iwelogic.main.presentation.*
import com.iwelogic.projects.presentation.ui.details.*
import com.iwelogic.core_ui.theme.PortfolioTheme
import dagger.hilt.android.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PortfolioTheme {
                val navController = rememberNavController()
                val isDarkMode = isSystemInDarkTheme()
                val context = this
                val statusBar = MaterialTheme.colorScheme.primaryContainer.toArgb()
                val navigationBar = MaterialTheme.colorScheme.primaryContainer.toArgb()
                DisposableEffect(isDarkMode) {
                    context.enableEdgeToEdge(
                        statusBarStyle = if (!isDarkMode) {
                            SystemBarStyle.dark(
                                statusBar
                            )
                        } else {
                            SystemBarStyle.dark(
                                statusBar
                            )
                        },
                        navigationBarStyle = if (!isDarkMode) {
                            SystemBarStyle.dark(navigationBar)
                        } else {
                            SystemBarStyle.dark(navigationBar)
                        }
                    )
                    onDispose { }
                }

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
                            arguments = listOf(navArgument("id") { type = NavType.StringType }
                            )
                        ) {
                            ProjectDetailsScreen(it.arguments?.getString("id"), navController = navController)
                        }
                    }
                }
            }
        }
    }
}