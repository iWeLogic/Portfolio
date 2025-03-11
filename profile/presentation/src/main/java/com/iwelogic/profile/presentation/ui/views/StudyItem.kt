package com.iwelogic.profile.presentation.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.iwelogic.profile.presentation.models.*

@Composable
fun StudyItem(study: Study, isDivider: Boolean) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(
                study.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1.0f)
            )
            Text(
                study.duration,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        if (isDivider)
            HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(top = 12.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun StudyItemPreview(){
    StudyItem(study = Study.preview, false)
}