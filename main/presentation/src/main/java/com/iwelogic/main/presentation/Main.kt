package com.iwelogic.main.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.iwelogic.core_ui.views.*
import com.iwelogic.profile.presentation.ui.*
import com.iwelogic.projects.presentation.ui.list.*
import com.iwelogic.settings.presentation.*

@Composable
fun MainScreen(navController: NavController) {
    var selectedScreen by rememberSaveable { mutableStateOf(Tab.Profile.route) }
    val mainNavController = rememberNavController()
    val screens = listOf(Tab.Profile, Tab.Projects, Tab.Settings)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .clip(RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                title = {
                    TypingEffect(stringResource(Tab.getByRoute(selectedScreen).title)) { text ->
                        Text(
                            text,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
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
                    .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
            ) {
                for (screen in screens) {
                    val isSelected = screen.route == selectedScreen
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
                                selectedScreen = screen.route
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
            startDestination = Tab.Profile.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            modifier = Modifier.padding(paddingValues = innerPadding)
        ) {
            composable(Tab.Profile.route) {
                ProfileScreen()
            }
            composable(Tab.Projects.route) {
                ProjectsScreen(navController)
            }
            composable(Tab.Settings.route) {
                SettingsScreen()
            }
        }
    }

}