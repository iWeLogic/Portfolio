package com.iwelogic.core.navigation

sealed class Screen(
    val route: String,
) {
    data object Main : Screen("main")
    data object Project : Screen("project")
}