package com.iwelogic.projects.presentation.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import com.iwelogic.core.views.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailsScreen(id: String?, navController: NavController, viewModel: ProjectDetailsViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .clip(RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp)),
                title = {
                    TypingEffect("Minecraft") { text ->
                        Text(
                            text,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { innerPadding ->
            when (val state: ProjectDetailsState = viewModel.state.value) {
                is ProjectDetailsState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ProjectDetailsState.Error -> {

                }
                is ProjectDetailsState.Main -> {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(state.project.name)
                    }
                }
            }
        }
    )
}