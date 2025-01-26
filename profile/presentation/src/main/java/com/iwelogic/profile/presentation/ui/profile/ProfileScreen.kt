package com.iwelogic.profile.presentation.ui.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.iwelogic.profile.presentation.models.*

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {

    when (val state: ProfileState = viewModel.state.value) {
        is ProfileState.Loading -> {

        }
        is ProfileState.Error -> {

        }
        is ProfileState.Main -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                ProfileCard(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    profile = Profile(
                        name = "Nazar Novak",
                        position = "Software developer (Android)",
                        avatar = "https://media.licdn.com/dms/image/v2/C4D03AQGb_ud7ivzNgg/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1517614699692?e=1743033600&v=beta&t=Tw3qFazog4jK-lUgJ8fFWZh2Tv-YtXL-qehYWJ6luDI",
                        isOpenToWork = true
                    )
                )

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    ExpandableText(
                        text = "asdas sd asd asd asd as ddas asdas sd asd asd asd as ddas asdas sd asd asd asd as ddas " +
                                "asdas sd asd asd asd as ddas asdas sd asd asd asd as ddas asdas sd asd asd asd as ddas asdas sd asd asd " +
                                "asd as ddas asdas sd asd asd asd as ddas asdas sd asd asd asd as ddas "
                    )
                }

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    Column {
                        Text("Contacts")
                    }
                }

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    Column {
                        Text("Study history")
                    }
                }

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    Column {
                        Text("Job history")
                    }
                }

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    Column {
                        Text("Location")
                    }
                }
            }
        }
    }

}