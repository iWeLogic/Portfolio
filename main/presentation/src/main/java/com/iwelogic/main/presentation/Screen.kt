package com.iwelogic.main.presentation

import androidx.annotation.*

sealed class Screen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val activeIcon: Int,
    @DrawableRes val inactiveIcon: Int
) {
    data object Profile : Screen("profile", R.string.profile, R.drawable.profile_active, R.drawable.profile)
    data object Projects : Screen("projects", R.string.projects, R.drawable.projects_active, R.drawable.projects)
    data object Settings : Screen("settings", R.string.settings, R.drawable.settings_active, R.drawable.settings)

    companion object {
        fun getByRoute(route: String) : Screen{
            return when(route){
                Profile.route -> Profile
                Projects.route -> Projects
                else -> Settings
            }
        }
    }
}