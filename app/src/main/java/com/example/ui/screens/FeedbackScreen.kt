package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.FeedbackEntry
import com.example.ui.components.AwarenessBanner
import com.example.ui.components.CyberCard
import com.example.ui.components.CyberShieldFooter
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatusPill
import com.example.ui.theme.LocalCyberColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeedbackScreen(
    feedbackHistory: List<FeedbackEntry>,
    onSubmitFeedback: (authorName: String, category: String, message: String) -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cyber = LocalCyberColors.current
    val categories = listOf(
        "General Feedback",
        "Content Suggestion",
        "UI / Usability",
        "Community Outreach",
        "Academic Review"
    )

    var name by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    var message by remember { mutableStateOf("") }
    var validationError by remember { mutableStateOf<String?>(null) }
    var showSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                badge = "CONTACT & FEEDBACK",
                title = "Share Your Feedback",
                subtitle = "Help improve CyberShield India's awareness lessons and simulations."
            )
        }

        item {
            AwarenessBanner(
                title = "Academic Prototype Notice",
                message = "No external backend server is configured for this VTU academic prototype. Submitted feedback is validated and stored locally on this device only.",
                isWarning = false
            )
        }

        item {
            CyberCard {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name (Optional)") },
                    placeholder = { Text("Enter your name or leave blank") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("feedback_name_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Feedback Category",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            label = { Text(category) },
                            modifier = Modifier.testTag("feedback_category_$category")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                OutlinedTextField(
                    value = message,
                    onValueChange = {
                        message = it
                        validationError = null
                        showSuccess = false
                    },
                    label = { Text("Message *") },
                    placeholder = { Text("Write your suggestions, review comments, or awareness topic requests...") },
                    isError = validationError != null,
                    supportingText = validationError?.let { err ->
                        { Text(err, color = cyber.alert) }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("feedback_message_input"),
                    minLines = 4
                )

                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = {
                        val trimmed = message.trim()
                        if (trimmed.length < 10) {
                            validationError = "Please enter at least 10 characters so your feedback is meaningful."
                            showSuccess = false
                        } else {
                            onSubmitFeedback(name, selectedCategory, trimmed)
                            name = ""
                            message = ""
                            validationError = null
                            showSuccess = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .testTag("feedback_submit_button")
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Submit Feedback Locally")
                }

                if (showSuccess) {
                    Spacer(modifier = Modifier.height(12.dp))
                    StatusPill(
                        text = "Feedback validated & saved locally on this device!",
                        containerColor = cyber.safeContainer,
                        contentColor = cyber.safe,
                        icon = Icons.Filled.CheckCircle
                    )
                }
            }
        }

        if (feedbackHistory.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "Saved Local Feedback Log",
                    subtitle = "Entries recorded during local testing and project review."
                )
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    feedbackHistory.forEach { entry ->
                        CyberCard {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = entry.authorName,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = entry.timestampLabel,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            StatusPill(
                                text = entry.category,
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = entry.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        item {
            CyberShieldFooter(onNavigate = onNavigate)
        }
    }
}
