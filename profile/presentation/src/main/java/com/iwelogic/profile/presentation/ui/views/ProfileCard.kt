package com.iwelogic.profile.presentation.ui.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.iwelogic.core_ui.*
import com.iwelogic.core_ui.views.*
import com.iwelogic.profile.presentation.R
import com.iwelogic.profile.presentation.models.*


@Composable
fun ProfileCard(profile: Profile, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(16.dp),
                brush = Brush.linearGradient(
                    start = Offset(0f, 1000f),
                    end = Offset(1000f, 0f),
                    colors = listOf(
                        "#5259e6".toColor(),
                        "#3EA3E7".toColor()
                    )
                )
            )
            .padding(16.dp),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF58D95D),
                            Color(0xFF4CAF50)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp),
            text = "Open to work",
            color = Color.White,
        )
        Row {
            RemoteImage(
                url = profile.avatar,
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(3.dp)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier
                    .height(96.dp)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(text = profile.name, color = Color.White)
                    Text(text = profile.position, color = Color(0xB3FFFFFF))
                }
                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        painter = painterResource(R.drawable.clock),
                        tint = Color.White,
                        contentDescription = "experience"
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        text = String.format(stringResource(R.string.experience), profile.years), color = Color.White
                    )
                    Icon(
                        modifier = Modifier.padding(start = 8.dp),
                        painter = painterResource(R.drawable.salary),
                        tint = Color.White,
                        contentDescription = "salary"
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        text = String.format(stringResource(R.string.rate), profile.rate), color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreview(){
    ProfileCard(profile = Profile.preview)
}