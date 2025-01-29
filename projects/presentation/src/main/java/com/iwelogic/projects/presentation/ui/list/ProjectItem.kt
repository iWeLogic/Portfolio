package com.iwelogic.projects.presentation.ui.list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.iwelogic.core.views.*
import com.iwelogic.projects.presentation.R
import com.iwelogic.projects.presentation.models.*

@Composable
fun ProjectItem(item: Project, modifier: Modifier = Modifier, onClickItem: (String) -> Unit) {
    CardHolder(
        modifier = modifier
    ) {

        val size = 120.dp
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClickItem(item.id)
                },
        ) {
            RemoteImage(
                url = item.icon, modifier = Modifier
                    .size(size, size)
                    .clip(RoundedCornerShape(CornerSize(16.dp)))
            )
            Column(
                modifier = Modifier
                    .defaultMinSize(minHeight = size)
                    .padding(all = 16.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.weight(1.0f))

                Row {
                    Row(
                        modifier = Modifier.weight(1.0f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.download),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            contentDescription = "downloads",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = item.downloads,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        modifier = Modifier
                            .weight(1.0f)
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.rating),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            contentDescription = "downloads",
                            modifier = Modifier
                                .size(24.dp)
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = item.rating,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }

}