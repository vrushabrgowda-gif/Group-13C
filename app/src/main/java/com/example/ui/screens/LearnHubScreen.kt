package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.R
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
fun LearnHubScreen(
    progress: LearningProgressState,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cyber = LocalCyberColors.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                badge = "LEARNING MODULES",
                title = "Cyber Threat Learning Centre",
                subtitle = "Master all three modules and complete interactive simulations before taking the 16-question Cyber Safety Quiz."
            )
        }

        item {
            LearnModuleCard(
                moduleId = ThreatModuleId.PHISHING,
                imageRes = R.drawable.img_phishing_alert,
                description = "Understand email, SMS (smishing), voice (vishing), UPI collect traps, and fake courier scams. Practice spotting red flags in our safe 5-message simulator.",
                metaLine = "${progress.phishingSolvedIds.size}/${CyberShieldContent.phishingScenarios.size} simulator scenarios practiced",
                isCompleted = progress.completedModules.contains(ThreatModuleId.PHISHING),
                onOpen = { onNavigate(CyberShieldRoutes.PHISHING) }
            )
        }

        item {
            LearnModuleCard(
                moduleId = ThreatModuleId.DIGITAL_ARREST,
                imageRes = R.drawable.img_digital_arrest,
                description = "Discover how scammers impersonate police, CBI, or TRAI on video calls, recognize psychological isolation tactics, and test your response in realistic scenarios.",
                metaLine = "${progress.digitalArrestSolvedIds.size}/${CyberShieldContent.digitalArrestScenarios.size} scenarios completed",
                isCompleted = progress.completedModules.contains(ThreatModuleId.DIGITAL_ARREST),
                onOpen = { onNavigate(CyberShieldRoutes.DIGITAL_ARREST) }
            )
        }

        item {
            LearnModuleCard(
                moduleId = ThreatModuleId.MALICIOUS_APPS,
                imageRes = R.drawable.img_malicious_apps,
                description = "Learn the risks of forwarded WhatsApp APKs, fake loan apps, and screen-sharing abuse. Inspect suspicious permissions on sample apps and track your safety checklist.",
                metaLine = "${progress.checklistCheckedIds.size}/${CyberShieldContent.safeAppChecklist.size} safety checklist items checked",
                isCompleted = progress.completedModules.contains(ThreatModuleId.MALICIOUS_APPS),
                onOpen = { onNavigate(CyberShieldRoutes.MALICIOUS_APPS) }
            )
        }

        item {
            CyberCard(borderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)) {
                Text(
                    text = "Ready to Verify Your Readiness?",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Take the 16-question Cyber Safety Quiz covering all three modules with instant explanations and a personalized summary.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { onNavigate(CyberShieldRoutes.QUIZ) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .testTag("learn_go_to_quiz_button")
                ) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Launch Cyber Safety Quiz")
                }
            }
        }

        item {
            CyberShieldFooter(onNavigate = onNavigate)
        }
    }
}

@Composable
private fun LearnModuleCard(
    moduleId: ThreatModuleId,
    imageRes: Int,
    description: String,
    metaLine: String,
    isCompleted: Boolean,
    onOpen: () -> Unit
) {
    val cyber = LocalCyberColors.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOpen)
            .testTag("learn_card_${moduleId.route}"),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, if (isCompleted) cyber.safe else cyber.cardBorder)
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = moduleId.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp)
                    .clip(MaterialTheme.shapes.large),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = moduleId.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    StatusPill(
                        text = if (isCompleted) "Completed" else "In Progress",
                        containerColor = if (isCompleted) cyber.safeContainer else MaterialTheme.colorScheme.primaryContainer,
                        contentColor = if (isCompleted) cyber.safe else MaterialTheme.colorScheme.onPrimaryContainer,
                        icon = if (isCompleted) Icons.Filled.CheckCircle else null
                    )
                }

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = metaLine,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                Button(
                    onClick = onOpen,
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .testTag("open_module_${moduleId.route}")
                ) {
                    Text(if (isCompleted) "Review ${moduleId.shortTitle}" else "Start ${moduleId.shortTitle}")
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
