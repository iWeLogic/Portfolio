package com.iwelogic.main.presentation

import androidx.annotation.*

sealed class Tab(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val activeIcon: Int,
    @DrawableRes val inactiveIcon: Int
) {
    data object Profile : Tab("profile", R.string.profile, R.drawable.profile_active, R.drawable.profile)
    data object Projects : Tab("projects", R.string.projects, R.drawable.projects_active, R.drawable.projects)
    data object Settings : Tab("settings", R.string.settings, R.drawable.settings_active, R.drawable.settings)

    companion object {
        fun getByRoute(route: String) : Tab{
            return when(route){
                Profile.route -> Profile
                Projects.route -> Projects
                else -> Settings
            }
        }
    }
}