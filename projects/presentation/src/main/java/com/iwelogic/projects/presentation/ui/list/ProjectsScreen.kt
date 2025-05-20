package com.iwelogic.projects.presentation.ui.list

import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import androidx.lifecycle.*
import androidx.lifecycle.compose.*
import androidx.navigation.*
import com.iwelogic.core.navigation.*
import com.iwelogic.core_ui.views.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(navController: NavController, viewModel: ProjectsViewModel = hiltViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(Unit) {
        viewModel.event
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .collect { uiEffect ->
                when (uiEffect) {
                    is ProjectsEvent.OpenProjectDetails -> navController.navigate("${Screen.Project.route}/${uiEffect.id}")
                }
            }
    }

    when (val state: ProjectsState = viewModel.state.collectAsStateWithLifecycle().value) {
        is ProjectsState.Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
        is ProjectsState.Error -> {
            ErrorPage(modifier = Modifier.fillMaxSize()) {
                viewModel.handleIntent(ProjectsIntent.OnClickReload)
            }
        }
        is ProjectsState.Main -> {
            PullToRefreshBox(
                isRefreshing = false,
                onRefresh = {
                    viewModel.handleIntent(ProjectsIntent.OnClickReload)
                },
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = rememberLazyListState(),
                    contentPadding = PaddingValues(16.dp),
                    reverseLayout = false,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    flingBehavior = ScrollableDefaults.flingBehavior(),
                    userScrollEnabled = true,
                ) {
                    items(state.projects, key = { project -> project.id }) {
                        ProjectItem(item = it, modifier = Modifier.fillMaxWidth()) { id ->
                            viewModel.handleIntent(ProjectsIntent.OpenDetails(id))
                        }
                    }
                }
            }
        }
    }
}