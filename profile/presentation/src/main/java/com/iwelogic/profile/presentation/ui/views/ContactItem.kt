package com.iwelogic.profile.presentation.ui.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.iwelogic.profile.presentation.models.*

@Composable
fun ContactItem(contact: Contact, isDivider: Boolean, onClick: (Contact) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(
                contact.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                contact.value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onClick(contact)
                }
            )
        }
        if (isDivider)
            HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(top = 12.dp))
    }
}