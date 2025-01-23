package com.iwelogic.main.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.iwelogic.core.views.*
import com.iwelogic.profile.presentation.profile.*
import com.iwelogic.projects.presentation.*
import com.iwelogic.settings.presentation.*

@Composable
fun MainScreen(navController: NavController) {
    var selectedScreen by remember { mutableStateOf<Screen>(Screen.Profile) }
    val mainNavController = rememberNavController()
    val screens = listOf(Screen.Profile, Screen.Projects, Screen.Settings)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(50.dp)
                    .clip(RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(end = 16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                       TypingEffect(stringResource(selectedScreen.title)) { text ->
                           Text(
                               text,
                               modifier = Modifier.fillMaxWidth(),
                               textAlign = TextAlign.Center,
                               style = MaterialTheme.typography.titleLarge
                           )
                       }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .navigationBarsPadding()
                    .height(50.dp)
                    .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            ) {
                for (screen in screens) {
                    val isSelected = screen == selectedScreen
                    Box(
                        modifier = Modifier.weight(1.0f),
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        val interactionSource = remember { MutableInteractionSource() }
                        BottomNavItem(
                            modifier = Modifier.clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                selectedScreen = screen
                                mainNavController.navigate(screen.route) {
                                    popUpTo(mainNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            screen = screen,
                            isSelected = isSelected
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = mainNavController,
            startDestination = Screen.Profile.route,
            modifier = Modifier.padding(paddingValues = innerPadding)
        ) {
            composable("profile") {
                ProfileScreen()
            }
            composable("projects") {
                ProjectsScreen(navController = navController)
            }
            composable("settings") {
                SettingsScreen()
            }
        }
    }

}