package com.iwelogic.profile.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {

    val state: ProfileState = viewModel.state.value

    when (state) {
        is ProfileState.Loading -> {

        }
        is ProfileState.Error -> {

        }
        is ProfileState.Main -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
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
            }


        }
    }

}