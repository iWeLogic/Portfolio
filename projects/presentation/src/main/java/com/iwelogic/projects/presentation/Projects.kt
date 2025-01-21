package com.iwelogic.projects.presentation

import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.*

@Composable
fun ProjectsScreen( navController: NavController) {
    Text(
        text = "ProjectsScreen!",
        modifier = Modifier.clickable{
            navController.navigate("project_details")
        }
    )
}