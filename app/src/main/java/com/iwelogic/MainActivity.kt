package com.iwelogic

import android.graphics.*
import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import com.iwelogic.main.presentation.*
import com.iwelogic.projects.presentation.*
import com.iwelogic.ui.theme.PortfolioTheme
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

                val statusBarLight = Color.WHITE
                val statusBarDark = Color.WHITE
                val navigationBarLight = Color.WHITE
                val navigationBarDark = Color.WHITE

                DisposableEffect(isDarkMode) {
                    context.enableEdgeToEdge(
                        statusBarStyle = if (!isDarkMode) {
                            SystemBarStyle.light(
                                statusBarLight,
                                statusBarDark
                            )
                        } else {
                            SystemBarStyle.dark(
                                statusBarDark
                            )
                        },
                        navigationBarStyle = if(!isDarkMode){
                            SystemBarStyle.light(
                                navigationBarLight,
                                navigationBarDark
                            )
                        } else {
                            SystemBarStyle.dark(navigationBarDark)
                        }
                    )

                    onDispose { }
                }

                Column {
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        modifier = Modifier.weight(1f)
                    ) {
                        composable("main") {
                            MainScreen(navController = navController)
                        }
                        composable("project_details") {
                            ProjectDetailsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PortfolioTheme {
        Greeting("Android")
    }
}