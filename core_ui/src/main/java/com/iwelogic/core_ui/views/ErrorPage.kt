package com.iwelogic.core_ui.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.iwelogic.core_ui.R

@Composable
fun ErrorPage(modifier: Modifier = Modifier, onClickReload: () -> Unit){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, alignment = Alignment.CenterVertically),
        modifier = modifier
    ) {
        Image(painter = painterResource(R.drawable.error_loading), contentDescription = "")

        Text(
            text = "Ups something went wrong",
            style = MaterialTheme.typography.headlineSmall
        )
        Button(
            onClick = onClickReload,
            modifier = Modifier.size(height = 50.dp, width = 200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            Text(
                text = "Reload",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }

}