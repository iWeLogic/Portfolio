package com.iwelogic.profile.presentation.ui.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
                    .verticalScroll(rememberScrollState()),
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                ) {
                    ProfileCard(
                        modifier = Modifier.fillMaxWidth(),
                        profile = Profile(
                            name = "Nazar Novak",
                            position = "Software developer (Android)",
                            avatar = "https://media.licdn.com/dms/image/v2/C4D03AQGb_ud7ivzNgg/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1517614699692?e=1743033600&v=beta&t=Tw3qFazog4jK-lUgJ8fFWZh2Tv-YtXL-qehYWJ6luDI",
                            isOpenToWork = true
                        )
                    )

                }


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    ExpandableText(
                        modifier = Modifier.padding(16.dp),
                        text = "Hi, I'm an Android developer with over eight years of experience in the field. During my career, I’ve developed more than 30 apps, with most of them being built from the ground up. I don’t just code — I can lead teams, gather requirements directly from clients, estimate project timelines, and coordinate mobile, backend, and frontend teams to deliver complete solutions.\n" +
                                "\n" +
                                "I’ve worked with companies like iAgentur, IntelliBoard, and Mediapulsas, while also establishing myself as a reliable freelancer.\n" +
                                "\n" +
                                "I’m a native speaker of Russian and Ukrainian, fluent in English, which helps me collaborate effectively with clients and teams worldwide. I’m passionate about creating apps that are both impactful and user-friendly!"
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Job history")
                    }
                }


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Contacts")
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Study history")
                    }
                }



                Card(
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