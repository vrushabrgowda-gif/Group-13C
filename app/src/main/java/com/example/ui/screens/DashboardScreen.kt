package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.CyberShieldContent
import com.example.data.LearningProgressState
import com.example.data.ThreatModuleId
import com.example.ui.CyberShieldRoutes
import com.example.ui.components.CyberCard
import com.example.ui.components.CyberShieldFooter
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatusPill
import com.example.ui.theme.LocalCyberColors

@Composable
fun DashboardScreen(
    progress: LearningProgressState,
    onNavigate: (String) -> Unit,
    onResetProgress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cyber = LocalCyberColors.current
    var showResetDialog by remember { mutableStateOf(false) }

    val unexploredModules = ThreatModuleId.entries.filterNot { progress.completedModules.contains(it) }
    val totalQuestions = CyberShieldContent.quizQuestions.size

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                badge = "PERSISTENT LOCAL PROGRESS",
                title = "Learning Dashboard",
                subtitle = "Your progress, quiz scores, and simulations are saved locally on this device."
            )
        }

        // OVERALL COMPLETION CARD
        item {
            CyberCard(borderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Overall Learning Completion",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "${progress.modulesCompletedCount} of 3 modules completed",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "${progress.overallCompletionPercent}%",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { progress.overallCompletionPercent / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(MaterialTheme.shapes.extraSmall),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceContainerHighest
                )
            }
        }

        // QUIZ SCORES & INTERACTIVE METRICS
        item {
            CyberCard {
                Text(
                    text = "Quiz Performance",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (progress.totalQuizAttempts == 0) {
                    Text(
                        text = "You have not attempted the Cyber Safety Quiz yet. Complete the 16-question assessment to benchmark your awareness.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onNavigate(CyberShieldRoutes.QUIZ) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("dashboard_take_quiz_button")
                    ) {
                        Text("Start Cyber Safety Quiz")
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MetricBox(
                            title = "Latest Score",
                            value = "${progress.latestQuizScore ?: 0}/$totalQuestions",
                            modifier = Modifier.weight(1f)
                        )
                        MetricBox(
                            title = "Best Score",
                            value = "${progress.bestQuizScore ?: 0}/$totalQuestions",
                            modifier = Modifier.weight(1f)
                        )
                        MetricBox(
                            title = "Attempts",
                            value = "${progress.totalQuizAttempts}",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Latest Category Breakdown: Phishing ${progress.phishingQuizCorrect}/6 • Digital Arrest ${progress.digitalArrestQuizCorrect}/5 • Malicious Apps ${progress.maliciousAppsQuizCorrect}/5",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        onClick = { onNavigate(CyberShieldRoutes.QUIZ) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("dashboard_retake_quiz_button")
                    ) {
                        Text("Retake Cyber Safety Quiz")
                    }
                }
            }
        }

        // MODULE & SIMULATION PROGRESS
        item {
            CyberCard {
                Text(
                    text = "Module & Simulator Milestones",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(10.dp))

                ThreatModuleId.entries.forEach { module ->
                    val done = progress.completedModules.contains(module)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = module.title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        StatusPill(
                            text = if (done) "Completed" else "Pending",
                            containerColor = if (done) cyber.safeContainer else MaterialTheme.colorScheme.surfaceContainerHigh,
                            contentColor = if (done) cyber.safe else MaterialTheme.colorScheme.onSurfaceVariant,
                            icon = if (done) Icons.Filled.CheckCircle else null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Phishing Simulator: ${progress.phishingSolvedIds.size}/${CyberShieldContent.phishingScenarios.size} solved",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Digital Arrest Simulator: ${progress.digitalArrestSolvedIds.size}/${CyberShieldContent.digitalArrestScenarios.size} solved",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Safe App Checklist: ${progress.checklistCheckedIds.size}/${CyberShieldContent.safeAppChecklist.size} checked",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // TOPICS YET TO EXPLORE
        item {
            CyberCard {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Explore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Topics Yet to Explore",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                if (unexploredModules.isEmpty() && progress.totalQuizAttempts > 0) {
                    Text(
                        text = "Excellent work! You have explored every threat module and attempted the Cyber Safety Quiz. Visit the Resource Centre anytime to refresh best practices.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = cyber.safe
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        unexploredModules.forEach { module ->
                            OutlinedButton(
                                onClick = { onNavigate(module.route) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .minimumInteractiveComponentSize()
                                    .testTag("dashboard_explore_${module.route}"),
                                border = BorderStroke(1.dp, cyber.cardBorder)
                            ) {
                                Text(
                                    text = "Explore ${module.title}",
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        if (progress.totalQuizAttempts == 0) {
                            OutlinedButton(
                                onClick = { onNavigate(CyberShieldRoutes.QUIZ) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .minimumInteractiveComponentSize()
                                    .testTag("dashboard_explore_quiz"),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
                            ) {
                                Text(
                                    text = "Take the 16-Question Cyber Quiz",
                                    modifier = Modifier.weight(1f),
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // RESET PROGRESS
        item {
            OutlinedButton(
                onClick = { showResetDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .minimumInteractiveComponentSize()
                    .testTag("dashboard_reset_progress_button"),
                border = BorderStroke(1.dp, cyber.alert.copy(alpha = 0.5f))
            ) {
                Icon(
                    imageVector = Icons.Filled.RestartAlt,
                    contentDescription = null,
                    tint = cyber.alert
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Reset Learning Progress", color = cyber.alert)
            }
        }

        item {
            CyberShieldFooter(onNavigate = onNavigate)
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset Learning Progress?") },
            text = {
                Text("This clears your saved module completions, simulator history, quiz scores, and checklist ticks from this device.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onResetProgress()
                        showResetDialog = false
                    },
                    modifier = Modifier.testTag("confirm_reset_button")
                ) {
                    Text("Reset", color = cyber.alert)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun MetricBox(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )
    }
}
