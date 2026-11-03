package com.activityhub.monitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.activityhub.monitor.data.AppUsageReader
import com.activityhub.monitor.ui.theme.ActivityHubTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivityHubTheme {
                ActivityHubApp(
                    hasUsageAccess = AppUsageReader.hasUsageAccess(this),
                    openUsageSettings = { AppUsageReader.openUsageSettings(this) },
                    loadUsage = { withContext(Dispatchers.IO) {
                        AppUsageReader.todayUsage(this@MainActivity)
                    } }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActivityHubApp(
    hasUsageAccess: Boolean,
    openUsageSettings: () -> Unit,
    loadUsage: suspend () -> List<com.activityhub.monitor.data.AppUsageItem>
) {
    var rows by remember { mutableStateOf(emptyList<com.activityhub.monitor.data.AppUsageItem>()) }
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(hasUsageAccess) {
        if (hasUsageAccess) {
            refreshing = true
            rows = loadUsage()
            refreshing = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("ACTIVITY HUB")
                        Text("Personal Activity Dashboard", style = MaterialTheme.typography.labelSmall)
                    }
                },
                actions = { IconButton(onClick = {
                    // Re-open dashboard after granting access.
                }) { Icon(Icons.Default.CloudSync, contentDescription = "Cloud sync status") } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("আজকের কার্যকলাপ  /  Today's activity", style = MaterialTheme.typography.titleLarge)
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SummaryCard("App Usage", "${rows.size}", "অ্যাপ / apps", Modifier.weight(1f))
                    SummaryCard("Cloud", "Setup", "Firebase config needed", Modifier.weight(1f))
                }
            }
            item {
                if (!hasUsageAccess) {
                    Card {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Usage Access প্রয়োজন", style = MaterialTheme.typography.titleMedium)
                            Text("অ্যাপ ব্যবহারের সময় পড়তে Android-এর Usage Access অনুমতি দিন।")
                            Button(onClick = openUsageSettings) { Text("অনুমতি চালু করুন") }
                        }
                    }
                } else {
                    Text("ব্যবহৃত অ্যাপ / Apps used", style = MaterialTheme.typography.titleMedium)
                }
            }
            if (hasUsageAccess) {
                if (refreshing) {
                    item { CircularProgressIndicator() }
                } else if (rows.isEmpty()) {
                    item { Text("আজকের কোনো ব্যবহার তথ্য পাওয়া যায়নি।") }
                } else {
                    items(rows) { row ->
                        ListItem(
                            headlineContent = { Text(row.appName) },
                            supportingContent = { Text(row.packageName) },
                            trailingContent = { Text(row.durationLabel) },
                            leadingContent = { Icon(Icons.Default.Apps, contentDescription = null) }
                        )
                        HorizontalDivider()
                    }
                }
            }
            item {
                Text(
                    "গোপনীয়তা: এই প্রাথমিক সংস্করণ শুধু Android-এর অনুমোদিত usage statistics পড়ে। " +
                    "অন্য অ্যাপের private messages, passwords বা in-app searches সংগ্রহ করে না।",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SummaryCard(title: String, value: String, detail: String, modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = MaterialTheme.typography.labelLarge)
            Text(value, style = MaterialTheme.typography.headlineMedium)
            Text(detail, style = MaterialTheme.typography.bodySmall)
        }
    }
}
