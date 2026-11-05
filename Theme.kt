package com.activityhub.monitor.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val HubColors = lightColorScheme()

@Composable
fun ActivityHubTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = HubColors, content = content)
}
