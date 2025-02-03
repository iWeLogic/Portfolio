package com.iwelogic.profile.presentation.ui.profile.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.iwelogic.profile.presentation.models.*

@Composable
fun ContactItem (contact: Contact, isDivider: Boolean){
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
            Text(contact.name, style = MaterialTheme.typography.bodyMedium)
            Text(
                contact.value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        if (isDivider)
            HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(top = 12.dp))
    }
}