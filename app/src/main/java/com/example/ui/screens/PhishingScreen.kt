package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.LinkOff
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.CyberShieldContent
import com.example.data.LearningProgressState
import com.example.data.ThreatModuleId
import com.example.ui.components.AwarenessBanner
import com.example.ui.components.CyberCard
import com.example.ui.components.CyberShieldFooter
import com.example.ui.components.ModuleCompletionCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatusPill
import com.example.ui.theme.LocalCyberColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhishingScreen(
    progress: LearningProgressState,
    onScenarioSolved: (String) -> Unit,
    onToggleModuleCompleted: (Boolean) -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cyber = LocalCyberColors.current
    var activeScenarioIndex by remember { mutableIntStateOf(0) }
    val scenario = CyberShieldContent.phishingScenarios[activeScenarioIndex]

    var selectedSuspicious by remember(scenario.id) { mutableStateOf<Boolean?>(null) }
    var selectedWarningIdx by remember(scenario.id) { mutableStateOf<Int?>(null) }
    var selectedActionIdx by remember(scenario.id) { mutableStateOf<Int?>(null) }
    var isEvaluated by remember(scenario.id) { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.img_phishing_alert),
                contentDescription = "Phishing awareness banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(MaterialTheme.shapes.large),
                contentScale = ContentScale.Crop
            )
        }

        // A. WHAT IS PHISHING?
        item {
            SectionHeader(
                badge = "MODULE 01 • PHISHING AWARENESS",
                title = "What Is Phishing?",
                subtitle = "Recognize deceptive messages before you tap or share sensitive details."
            )
            Spacer(modifier = Modifier.height(10.dp))
            CyberCard {
                Text(
                    text = CyberShieldContent.phishingExplanation,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // B. TYPES OF PHISHING
        item {
            SectionHeader(
                title = "Common Types of Phishing in India",
                subtitle = "Fraudsters adapt their bait across SMS, calls, email, and UPI."
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CyberShieldContent.phishingTypes.forEach { type ->
                    CyberCard {
                        Text(
                            text = type.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = type.subtitle,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = type.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = MaterialTheme.colorScheme.surfaceContainerHigh
                        ) {
                            Text(
                                text = "Example: ${type.indianExample}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }

        // C. RED FLAG IDENTIFICATION
        item {
            SectionHeader(
                title = "Red Flag Identification",
                subtitle = "Six warning signs found in nearly every phishing message."
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CyberShieldContent.phishingRedFlags.forEach { flag ->
                    CyberCard(borderColor = cyber.caution.copy(alpha = 0.45f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Warning,
                                contentDescription = null,
                                tint = cyber.caution,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = flag.title,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = flag.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Cue: ${flag.exampleCue}",
                            style = MaterialTheme.typography.labelSmall,
                            color = cyber.caution
                        )
                    }
                }
            }
        }

        // D. INTERACTIVE PHISHING SIMULATOR
        item {
            SectionHeader(
                badge = "SAFE EDUCATIONAL SIMULATION",
                title = "Interactive Phishing Simulator",
                subtitle = "Inspect each fictional message safely. All simulated links are strictly non-clickable."
            )
            Spacer(modifier = Modifier.height(10.dp))

            AwarenessBanner(
                title = "Safe Practice Environment",
                message = "No real URLs are opened or loaded. Practice evaluating messages before deciding your next step.",
                isWarning = false
            )
            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CyberShieldContent.phishingScenarios.forEachIndexed { idx, item ->
                    val solved = progress.phishingSolvedIds.contains(item.id)
                    FilterChip(
                        selected = activeScenarioIndex == idx,
                        onClick = { activeScenarioIndex = idx },
                        label = {
                            Text("Case ${idx + 1}" + if (solved) " ✓" else "")
                        },
                        modifier = Modifier.testTag("phishing_case_chip_${idx + 1}"),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            CyberCard(borderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusPill(
                        text = scenario.channel,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        icon = Icons.Filled.Sms
                    )
                    Text(
                        text = "From: ${scenario.senderLabel}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    border = BorderStroke(1.dp, cyber.cardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "\"${scenario.messageBody}\"",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Medium
                        )
                        if (scenario.simulatedUrlDisplay != null) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .background(cyber.cautionContainer)
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LinkOff,
                                    contentDescription = "Non-clickable simulated link",
                                    tint = cyber.caution,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = scenario.simulatedUrlDisplay,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = cyber.caution
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Q1: Legitimate or Suspicious?
                Text(
                    text = "1. Is this message legitimate or suspicious?",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FilterChip(
                        selected = selectedSuspicious == false,
                        onClick = { if (!isEvaluated) selectedSuspicious = false },
                        label = { Text("Legitimate") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("phishing_legitimate_chip")
                    )
                    FilterChip(
                        selected = selectedSuspicious == true,
                        onClick = { if (!isEvaluated) selectedSuspicious = true },
                        label = { Text("Suspicious") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("phishing_suspicious_chip")
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Q2: Warning signs
                Text(
                    text = "2. What is the key indicator / warning sign?",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(6.dp))
                scenario.warningSignOptions.forEachIndexed { idx, opt ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .clickable(enabled = !isEvaluated) { selectedWarningIdx = idx }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedWarningIdx == idx,
                            onClick = { if (!isEvaluated) selectedWarningIdx = idx }
                        )
                        Text(
                            text = opt,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Q3: Next Action
                Text(
                    text = "3. What should you do next?",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(6.dp))
                scenario.nextActionOptions.forEachIndexed { idx, opt ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .clickable(enabled = !isEvaluated) { selectedActionIdx = idx }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedActionIdx == idx,
                            onClick = { if (!isEvaluated) selectedActionIdx = idx }
                        )
                        Text(
                            text = opt,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                val allSelected = selectedSuspicious != null && selectedWarningIdx != null && selectedActionIdx != null
                Button(
                    onClick = {
                        isEvaluated = true
                        onScenarioSolved(scenario.id)
                    },
                    enabled = allSelected && !isEvaluated,
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .testTag("phishing_check_answer_button")
                ) {
                    Text(if (isEvaluated) "Evaluation Complete" else "Check My Analysis")
                }

                AnimatedVisibility(visible = isEvaluated) {
                    val q1Right = selectedSuspicious == scenario.isSuspicious
                    val q2Right = selectedWarningIdx == scenario.correctWarningSignIndex
                    val q3Right = selectedActionIdx == scenario.correctNextActionIndex
                    val allRight = q1Right && q2Right && q3Right

                    Column(
                        modifier = Modifier
                            .padding(top = 14.dp)
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.small)
                            .background(if (allRight) cyber.safeContainer else cyber.cautionContainer)
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = if (allRight) Icons.Filled.CheckCircle else Icons.Filled.ErrorOutline,
                                contentDescription = null,
                                tint = if (allRight) cyber.safe else cyber.caution
                            )
                            Text(
                                text = if (allRight) {
                                    "All 3 Answers Correct!"
                                } else {
                                    "Review Your Assessment"
                                },
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Text(
                            text = "• Verdict: ${if (q1Right) "Correct" else "Incorrect"} (${if (scenario.isSuspicious) "Suspicious" else "Legitimate"})\n" +
                                "• Indicator: ${if (q2Right) "Correct" else "Incorrect — ${scenario.warningSignOptions[scenario.correctWarningSignIndex]}"}\n" +
                                "• Safe Next Step: ${if (q3Right) "Correct" else "Incorrect — ${scenario.nextActionOptions[scenario.correctNextActionIndex]}"}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        HorizontalDivider(color = cyber.cardBorder)
                        Text(
                            text = scenario.explanation,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }

        // MODULE COMPLETION
        item {
            ModuleCompletionCard(
                moduleName = ThreatModuleId.PHISHING.title,
                isCompleted = progress.completedModules.contains(ThreatModuleId.PHISHING),
                onToggleCompleted = onToggleModuleCompleted
            )
        }

        item {
            CyberShieldFooter(onNavigate = onNavigate)
        }
    }
}
