package com.iwelogic.core_ui.views

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.iwelogic.core_ui.R

@Composable
fun ExpandableBloc(title: String, modifier: Modifier = Modifier, content: @Composable (Boolean) -> Unit) {

    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier) {

        Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))

        content(isExpanded)

        Text(
            stringResource(if (isExpanded) R.string.less else R.string.more),
            modifier = Modifier
                .align(Alignment.End)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    isExpanded = !isExpanded
                }
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}