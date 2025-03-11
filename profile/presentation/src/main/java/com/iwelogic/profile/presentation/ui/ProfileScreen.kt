package com.iwelogic.profile.presentation.ui

import android.content.*
import android.net.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.iwelogic.core_ui.views.*
import com.iwelogic.profile.presentation.R
import com.iwelogic.profile.presentation.models.*
import com.iwelogic.profile.presentation.ui.ProfileState.*
import com.iwelogic.profile.presentation.ui.views.*


@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.event.collect { effect ->
            when (effect) {
                is ProfileEvent.DialPhone -> {
                    context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${effect.phone}")))
                }
                is ProfileEvent.OpenLink -> {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(effect.url)))
                }
            }
        }
    }

    ProfileView(
        state = viewModel.state.value,
        onClickReload = {
            viewModel.handleIntent(ProfileIntent.OnClickReload)
        },
        onClickContact = { contact ->
            viewModel.handleIntent(ProfileIntent.OnClickContact(contact))
        },
        onClickJob = { job ->
            viewModel.handleIntent(ProfileIntent.OnClickJob(job))
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    state: ProfileState,
    onClickReload: () -> Unit,
    onClickContact: (Contact) -> Unit,
    onClickJob: (Job) -> Unit
) {
    when (state) {
        is Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
        is Error -> {
            ErrorPage(
                modifier = Modifier.fillMaxSize(),
                onClickReload = onClickReload
            )
        }
        is Main -> {
            PullToRefreshBox(
                isRefreshing = false,
                onRefresh = onClickReload,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                ) {
                    CardHolder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                    ) {
                        ProfileCard(
                            modifier = Modifier.fillMaxWidth(),
                            profile = state.profile
                        )
                    }

                    CardHolder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        ExpandableText(
                            modifier = Modifier.padding(16.dp),
                            text = state.profile.about.replace("\\n", "\n")
                        )
                    }

                    CardHolder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        ExpandableBloc(
                            title = stringResource(R.string.contacts),
                            modifier = Modifier.padding(16.dp)
                        ) { isExpanded ->
                            for (index in 0 until if (isExpanded) state.contacts.size else 1) {
                                ContactItem(
                                    contact = state.contacts[index],
                                    isDivider = index != state.contacts.size - 1 && isExpanded,
                                    onClickContact
                                )
                            }
                        }
                    }

                    CardHolder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        ExpandableBloc(
                            title = stringResource(R.string.jobs),
                            modifier = Modifier.padding(16.dp)
                        ) { isExpanded ->
                            for (index in 0 until if (isExpanded) state.jobs.size else 1) {
                                JobItem(
                                    job = state.jobs[index],
                                    isDivider = index != state.jobs.size - 1 && isExpanded,
                                    onClickJob
                                )
                            }
                        }
                    }

                    CardHolder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
                    ) {
                        ExpandableBloc(
                            title = stringResource(R.string.education),
                            modifier = Modifier.padding(16.dp)
                        ) { isExpanded ->
                            for (index in 0 until if (isExpanded) state.studies.size else 1) {
                                StudyItem(
                                    study = state.studies[index],
                                    isDivider = index != state.jobs.size - 1 && isExpanded
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileView(Main.preview, {}, {}, {})
}