package com.iwelogic.profile.presentation.ui.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.iwelogic.core_ui.views.*

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {

    when (val state: ProfileState = viewModel.state.value) {
        is ProfileState.Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
        is ProfileState.Error -> {

        }
        is ProfileState.Main -> {
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
                    var expanded by remember { mutableStateOf(false) }
                    val interactionSource = remember { MutableInteractionSource() }
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text("Contacts", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))

                        for (index in 0 until if (expanded) state.contacts.size else 1) {
                            val contact = state.contacts[index]
                            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
                                    Text(contact.name, style = MaterialTheme.typography.bodyMedium)
                                    Text(
                                        contact.value,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                                if (index != state.contacts.size - 1 && expanded)
                                    HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(top = 12.dp))
                            }
                        }

                        Text(
                            if (expanded) "less" else "more",
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    expanded = !expanded
                                }
                                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                CardHolder(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Study history")
                    }
                }

                CardHolder(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Location")
                    }
                }
            }
        }
    }

}