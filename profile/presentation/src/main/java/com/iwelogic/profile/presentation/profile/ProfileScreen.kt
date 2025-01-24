package com.iwelogic.profile.presentation.profile

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
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape),
                    model = state.photo,
                    loading = {
                        CircularProgressIndicator(modifier = Modifier.padding(30.dp))
                    },
                    error = { state ->
                        Text("error")
                    },
                    contentDescription = "asdas",
                )

                Text("Status Open to work")
                Text("Novak Nazar")
                Text("Software developer (Android)")
                Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    ExpandableText(
                        text = "asdas sd asd asd asd as ddas asdas sd asd asd asd as ddas asdas sd asd asd asd as ddas " +
                                "asdas sd asd asd asd as ddas asdas sd asd asd asd as ddas asdas sd asd asd asd as ddas asdas sd asd asd " +
                                "asd as ddas asdas sd asd asd asd as ddas asdas sd asd asd asd as ddas "
                    )
                }

                Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Column {
                        Text("Contacts")
                    }
                }

                Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Column {
                        Text("Study history")
                    }
                }

                Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Column {
                        Text("Job history")
                    }
                }

                Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Column {
                        Text("Location")
                    }
                }
            }
        }
    }

}