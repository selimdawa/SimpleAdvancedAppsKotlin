package com.flatcode.simpleadvancedapps.valorant.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.flatcode.simpleadvancedapps.valorant.presentation.theme.ValoBasic

@Composable
fun ErrorText(error: String, modifier: Modifier) {
    Text(
        text = error,
        color = MaterialTheme.colors.error,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    )
}

@Composable
fun HeaderText(header: String) {
    Text(
        text = header,
        color = ValoBasic,
        style = MaterialTheme.typography.h4
    )
}