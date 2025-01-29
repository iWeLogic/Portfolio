package com.iwelogic.profile.presentation.ui.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.iwelogic.core.views.*

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
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Job history")
                    }
                }


                CardHolder(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Contacts")
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