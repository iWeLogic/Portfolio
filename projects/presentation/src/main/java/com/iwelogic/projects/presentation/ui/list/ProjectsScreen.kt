package com.iwelogic.projects.presentation.ui.list

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*

@Composable
fun ProjectsScreen(navController: NavController, viewModel: ProjectsViewModel = hiltViewModel()) {

    when (val state: ProjectsState = viewModel.state.value) {
        is ProjectsState.Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
        is ProjectsState.Error -> {

        }
        is ProjectsState.Main -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = rememberLazyListState(),
                contentPadding = PaddingValues(16.dp),
                reverseLayout = false,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                flingBehavior = ScrollableDefaults.flingBehavior(),
                userScrollEnabled = true
            ) {
                items(state.projects) {
                    Text(
                        text = it.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                navController.navigate("project/${it.id}")
                            },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}