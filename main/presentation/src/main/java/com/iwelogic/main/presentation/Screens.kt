package com.iwelogic.main.presentation

sealed class Screens(val route : String) {
    data object Profile : Screens("profile")
    data object Projects : Screens("projects")
    data object Settings : Screens("settings")
}