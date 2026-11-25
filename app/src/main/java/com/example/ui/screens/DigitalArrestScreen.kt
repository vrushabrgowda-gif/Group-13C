package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
fun DigitalArrestScreen(
    progress: LearningProgressState,
    onScenarioSolved: (String) -> Unit,
    onToggleModuleCompleted: (Boolean) -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cyber = LocalCyberColors.current
    var activeScenarioIndex by remember { mutableIntStateOf(0) }
    val scenario = CyberShieldContent.digitalArrestScenarios[activeScenarioIndex]
    var selectedOptionId by remember(scenario.id) { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // PROMINENT AWARENESS MESSAGE
        item {
            AwarenessBanner(
                title = "CRITICAL LEGAL FACT",
                message = CyberShieldContent.digitalArrestAwarenessMessage,
                isWarning = true
            )
        }

        item {
            Image(
                painter = painterResource(id = R.drawable.img_digital_arrest),
                contentDescription = "Digital arrest scam prevention illustration",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(MaterialTheme.shapes.large),
                contentScale = ContentScale.Crop
            )
        }

        // A. WHAT IS A DIGITAL ARREST SCAM?
        item {
            SectionHeader(
                badge = "MODULE 02 • DIGITAL ARREST SCAMS",
                title = "What Is a Digital Arrest Scam?",
                subtitle = "Cyber extortion disguised as official law enforcement action."
            )
            Spacer(modifier = Modifier.height(10.dp))
            CyberCard {
                Text(
                    text = CyberShieldContent.digitalArrestExplanation,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // B. HOW THE SCAM WORKS
        item {
            SectionHeader(
                title = "How the Scam Works",
                subtitle = "A five-stage intimidation playbook used by fraud syndicates."
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CyberShieldContent.digitalArrestSteps.forEach { step ->
                    CyberCard {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${step.stepNumber}",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = step.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = step.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        // C. WARNING SIGNS
        item {
            SectionHeader(
                title = "Warning Signs to Watch For",
                subtitle = "Spot coercion tactics immediately."
            )
            Spacer(modifier = Modifier.height(10.dp))
            CyberCard(borderColor = cyber.alert.copy(alpha = 0.45f)) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    CyberShieldContent.digitalArrestWarningSigns.forEach { sign ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Warning,
                                contentDescription = null,
                                tint = cyber.alert,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = sign,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // D. INTERACTIVE DIGITAL ARREST SIMULATION
        item {
            SectionHeader(
                badge = "SCENARIO-BASED LEARNING",
                title = "Interactive Digital Arrest Simulation",
                subtitle = "Choose how you would respond in each high-pressure situation."
            )
            Spacer(modifier = Modifier.height(10.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CyberShieldContent.digitalArrestScenarios.forEachIndexed { idx, item ->
                    val solved = progress.digitalArrestSolvedIds.contains(item.id)
                    FilterChip(
                        selected = activeScenarioIndex == idx,
                        onClick = { activeScenarioIndex = idx },
                        label = { Text("Scenario ${idx + 1}" + if (solved) " ✓" else "") },
                        modifier = Modifier.testTag("digital_arrest_scenario_chip_${idx + 1}")
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            CyberCard(borderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)) {
                StatusPill(
                    text = scenario.callerClaim,
                    containerColor = cyber.alertContainer,
                    contentColor = cyber.alert,
                    icon = Icons.Filled.VideocamOff
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = scenario.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    border = BorderStroke(1.dp, cyber.cardBorder)
                ) {
                    Text(
                        text = "\"${scenario.promptText}\"",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(14.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Select your response:",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    scenario.options.forEachIndexed { optIdx, option ->
                        val isChosen = selectedOptionId == option.id
                        val border = when {
                            isChosen && option.isSafe -> cyber.safe
                            isChosen && !option.isSafe -> cyber.alert
                            else -> cyber.cardBorder
                        }
                        val bg = when {
                            isChosen && option.isSafe -> cyber.safeContainer
                            isChosen && !option.isSafe -> cyber.alertContainer
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .minimumInteractiveComponentSize()
                                .clickable {
                                    selectedOptionId = option.id
                                    if (option.isSafe) {
                                        onScenarioSolved(scenario.id)
                                    }
                                }
                                .testTag("da_option_${idxPlusOne(optIdx)}"),
                            shape = MaterialTheme.shapes.small,
                            colors = CardDefaults.cardColors(containerColor = bg),
                            border = BorderStroke(1.dp, border)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "${optIdx + 1}. ${option.label}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(1f)
                                    )
                                    if (isChosen) {
                                        Icon(
                                            imageVector = if (option.isSafe) Icons.Filled.CheckCircle else Icons.Filled.Dangerous,
                                            contentDescription = if (option.isSafe) "Safe response" else "Unsafe response",
                                            tint = if (option.isSafe) cyber.safe else cyber.alert
                                        )
                                    }
                                }
                                AnimatedVisibility(visible = isChosen) {
                                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Text(
                                            text = option.feedback,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        if (option.isSafe) {
                                            Text(
                                                text = "Key Takeaway: ${scenario.keyTakeaway}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = cyber.safe
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            AwarenessBanner(
                title = "Remember",
                message = CyberShieldContent.digitalArrestAwarenessMessage,
                isWarning = false
            )
        }

        item {
            ModuleCompletionCard(
                moduleName = ThreatModuleId.DIGITAL_ARREST.title,
                isCompleted = progress.completedModules.contains(ThreatModuleId.DIGITAL_ARREST),
                onToggleCompleted = onToggleModuleCompleted
            )
        }

        item {
            CyberShieldFooter(onNavigate = onNavigate)
        }
    }
}

private fun idxPlusOne(index: Int): Int = index + 1
