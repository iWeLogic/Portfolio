package com.iwelogic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import com.iwelogic.main.presentation.*
import com.iwelogic.projects.presentation.*
import com.iwelogic.ui.theme.PortfolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            Column {
                PortfolioTheme {
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        modifier = Modifier.weight(1f)
                    ) {
                        composable("main") {
                            MainScreen (navController = navController)
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