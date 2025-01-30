package com.iwelogic.projects.presentation.ui.list

import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import com.iwelogic.core_ui.views.*

@Composable
fun ProjectsScreen(navController: NavController, viewModel: ProjectsViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { uiEffect ->
            when (uiEffect) {
                is ProjectsUiEffect.OpenProjectDetails -> navController.navigate("project/${uiEffect.id}")
            }
        }
    }

    when (val state: ProjectsState = viewModel.state.value) {
        is ProjectsState.Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
        is ProjectsState.Error -> {
            ErrorPage(modifier = Modifier.fillMaxSize()) {
                viewModel.obtainEvent(ProjectsUserEvent.OnClickReload)
            }
        }
        is ProjectsState.Main -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = rememberLazyListState(),
                contentPadding = PaddingValues(16.dp),
                reverseLayout = false,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                flingBehavior = ScrollableDefaults.flingBehavior(),
                userScrollEnabled = true
            ) {
                items(state.projects) {
                    ProjectItem(item = it, modifier = Modifier.fillMaxWidth()) { id ->
                        viewModel.obtainEvent(ProjectsUserEvent.OpenDetails(id))
                    }
                }
            }
        }
    }
}