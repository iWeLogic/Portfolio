package com.iwelogic.profile.presentation.ui.profile.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.iwelogic.profile.presentation.models.*

@Composable
fun JobItem (job: Job, isDivider: Boolean){
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
            Text(job.position, style = MaterialTheme.typography.bodyMedium)
            Text(
                job.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        if (isDivider)
            HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(top = 12.dp))
    }
}