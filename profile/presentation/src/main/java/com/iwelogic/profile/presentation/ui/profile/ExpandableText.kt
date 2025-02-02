package com.iwelogic.profile.presentation.ui.profile

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import com.iwelogic.profile.presentation.R
import com.webtoonscorp.android.readmore.foundation.*
import com.webtoonscorp.android.readmore.material3.*

@Composable
fun ExpandableText(text: String, modifier: Modifier = Modifier) {

    var expanded by remember { mutableStateOf(false) }

    ReadMoreText(
        text = text,
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        },
        style = MaterialTheme.typography.bodyMedium,
        readMoreText = stringResource(R.string.read_more),
        readMoreColor = MaterialTheme.colorScheme.primary,
        readMoreFontWeight = FontWeight.Bold,
        readMoreMaxLines = 4,
        readMoreOverflow = ReadMoreTextOverflow.Ellipsis,
        readLessText = stringResource(R.string.read_less),
        readLessColor = MaterialTheme.colorScheme.primary,
        readLessFontWeight = FontWeight.Bold,
        toggleArea = ToggleArea.More,
    )
}