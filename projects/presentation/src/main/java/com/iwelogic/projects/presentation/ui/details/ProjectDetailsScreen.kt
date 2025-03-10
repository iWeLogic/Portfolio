package com.iwelogic.projects.presentation.ui.details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import com.iwelogic.core_ui.views.*
import com.iwelogic.projects.presentation.R

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailsScreen(id: String?, navController: NavController, viewModel: ProjectDetailsViewModel = hiltViewModel()) {

    val loading = stringResource(R.string.loading)
    var title by remember { mutableStateOf(loading) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .clip(RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp)),
                title = {
                    TypingEffect(title) { text ->
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
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                }
                is ProjectDetailsState.Error -> {
                    ErrorPage(modifier = Modifier.fillMaxSize()) {
                        viewModel.handleIntent(ProjectDetailsIntent.OnClickReload)
                    }
                }
                is ProjectDetailsState.Main -> {
                    title = state.project.name
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                    ) {
                        val pagerState = rememberPagerState(pageCount = {
                            state.project.images.size
                        })

                        HorizontalPager(state = pagerState) { page ->
                            Box(modifier = Modifier.fillMaxWidth()) {
                                RemoteImage(
                                    state.project.images[page],
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1.66f)
                                        .clip(RoundedCornerShape(16.dp))
                                )
                            }
                        }

                        Row (modifier = Modifier.padding(16.dp)){
                            Row(
                                modifier = Modifier.weight(1.0f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.download),
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                    contentDescription = "downloads",
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = state.project.downloads,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .weight(1.0f)
                                    .padding(start = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.rating),
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                    contentDescription = "downloads",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = state.project.rating,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        FlowRow(modifier = Modifier.padding(horizontal = 8.dp)) {
                            state.project.tags.forEach {
                                Text(
                                    modifier = Modifier.padding(start = 8.dp).background(
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = RoundedCornerShape(16.dp)
                                    ).padding(horizontal = 8.dp, vertical = 4.dp),
                                    text = it,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        ExpandableText(
                            modifier = Modifier.padding(16.dp),
                            text = state.project.description.replace("\\n", "\n")
                        )
                    }
                }
            }
        }
    )
}