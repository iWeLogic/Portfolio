package com.iwelogic.main.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.iwelogic.profile.presentation.*
import com.iwelogic.projects.presentation.*
import com.iwelogic.settings.presentation.*

@Composable
fun MainScreen(navController: NavController) {
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    val mainNavController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = 0 == navigationSelectedItem,
                    label = {
                        Text(Screens.Profile.route)
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = Screens.Profile.route
                        )
                    },
                    onClick = {
                        navigationSelectedItem = 0
                        mainNavController.navigate(Screens.Profile.route) {
                            popUpTo(mainNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    selected = 1 == navigationSelectedItem,
                    label = {
                        Text(Screens.Projects.route)
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = Screens.Projects.route
                        )
                    },
                    onClick = {
                        navigationSelectedItem = 1
                        mainNavController.navigate(Screens.Projects.route) {
                            popUpTo(mainNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    selected = 2 == navigationSelectedItem,
                    label = {
                        Text(Screens.Settings.route)
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = Screens.Settings.route
                        )
                    },
                    onClick = {
                        navigationSelectedItem = 2
                        mainNavController.navigate(Screens.Settings.route) {
                            popUpTo(mainNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = mainNavController,
            startDestination = Screens.Profile.route,
            modifier = Modifier.padding(paddingValues = innerPadding)) {
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