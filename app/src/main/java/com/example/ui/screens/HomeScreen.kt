package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.CyberShieldContent
import com.example.data.LearningProgressState
import com.example.data.ThreatModuleId
import com.example.ui.CyberShieldRoutes
import com.example.ui.components.CyberCard
import com.example.ui.components.CyberShieldFooter
import com.example.ui.components.EmergencyHelplineCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatusPill
import com.example.ui.theme.LocalCyberColors

@Composable
fun HomeScreen(
    progress: LearningProgressState,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cyber = LocalCyberColors.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 1. HERO SECTION
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, cyber.cardBorder)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_hero_shield),
                            contentDescription = "CyberShield India digital protection shield illustration",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)
                                        )
                                    )
                                )
                        )
                        StatusPill(
                            text = "Think Before You Click. Stay Safe Online.",
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.92f),
                            contentColor = MaterialTheme.colorScheme.secondary,
                            icon = Icons.Filled.Shield,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(14.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Your Digital Safety Starts Here.",
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Understand cyber threats. Recognize suspicious activities. Protect yourself and your loved ones from online fraud.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { onNavigate(CyberShieldRoutes.LEARN) },
                                modifier = Modifier
                                    .weight(1f)
                                    .minimumInteractiveComponentSize()
                                    .testTag("home_start_learning_button"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.School,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Start Learning")
                            }

                            OutlinedButton(
                                onClick = { onNavigate(CyberShieldRoutes.QUIZ) },
                                modifier = Modifier
                                    .weight(1f)
                                    .minimumInteractiveComponentSize()
                                    .testTag("home_test_knowledge_button"),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Quiz,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Test Knowledge",
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. REAL USER ACTIVITY LEARNING STATS
        item {
            CyberCard(
                modifier = Modifier
                    .clickable { onNavigate(CyberShieldRoutes.DASHBOARD) }
                    .testTag("home_activity_stats_card"),
                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Your Learning Activity",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Based strictly on your completed modules & quiz runs",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "${progress.overallCompletionPercent}%",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = { progress.overallCompletionPercent / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(MaterialTheme.shapes.extraSmall),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceContainerHighest
                )
                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ActivityStatChip(
                        label = "Modules",
                        value = "${progress.modulesCompletedCount}/3",
                        modifier = Modifier.weight(1f)
                    )
                    ActivityStatChip(
                        label = "Quiz Score",
                        value = progress.latestQuizScore?.let { "$it/${CyberShieldContent.quizQuestions.size}" } ?: "Not taken",
                        modifier = Modifier.weight(1f)
                    )
                    ActivityStatChip(
                        label = "Simulations",
                        value = "${progress.phishingSolvedIds.size + progress.digitalArrestSolvedIds.size}/8",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 3. THREE FEATURED THREAT CATEGORIES
        item {
            SectionHeader(
                badge = "CORE THREAT MODULES",
                title = "Explore India's Top Cyber Threats",
                subtitle = "Interactive guides and safe real-world simulations."
            )
        }

        item {
            val featured = listOf(
                Triple(
                    ThreatModuleId.PHISHING,
                    R.drawable.img_phishing_alert,
                    "Spot fake KYC SMS, UPI collect traps, and cloned banking links with an interactive message simulator."
                ),
                Triple(
                    ThreatModuleId.DIGITAL_ARREST,
                    R.drawable.img_digital_arrest,
                    "Learn why police never arrest over video calls and practice escaping intimidation scams."
                ),
                Triple(
                    ThreatModuleId.MALICIOUS_APPS,
                    R.drawable.img_malicious_apps,
                    "Inspect suspicious app permissions, avoid WhatsApp APK traps, and audit your device hygiene."
                )
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                featured.forEach { (module, drawableRes, summary) ->
                    val completed = progress.completedModules.contains(module)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .clickable { onNavigate(module.route) }
                            .testTag("home_threat_card_${module.route}"),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(
                            1.dp,
                            if (completed) cyber.safe.copy(alpha = 0.6f) else cyber.cardBorder
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = drawableRes),
                                contentDescription = module.title,
                                modifier = Modifier
                                    .size(84.dp)
                                    .clip(MaterialTheme.shapes.small),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = module.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.weight(1f, fill = false)
                                    )
                                    if (completed) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = "Completed",
                                            tint = cyber.safe,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = summary,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = if (completed) "Review Module" else "Open Module",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. ABOUT THE PLATFORM
        item {
            CyberCard {
                StatusPill(
                    text = "VTU COMMUNITY PROJECT • 3RD SEM CSE",
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "About CyberShield India",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "CyberShield India is a citizen-focused cybersecurity awareness initiative developed by 3rd-semester Computer Science & Engineering students under VTU. Designed for students, families, and senior citizens, it turns complex cyber threats into clear visual lessons and risk-free interactive simulations—no sign-in required.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(
                        onClick = { onNavigate(CyberShieldRoutes.ABOUT) },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("home_about_project_button")
                    ) {
                        Text("Project Details")
                    }
                    OutlinedButton(
                        onClick = { onNavigate(CyberShieldRoutes.FEEDBACK) },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("home_feedback_button")
                    ) {
                        Text("Share Feedback")
                    }
                }
            }
        }

        // 5. CYBER SAFETY TIPS
        item {
            CyberCard {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Lightbulb,
                        contentDescription = null,
                        tint = cyber.caution,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Essential Cyber Safety Habits",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                CyberShieldContent.quickSafetyTips.forEachIndexed { idx, tip ->
                    Row(
                        modifier = Modifier.padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "0${idx + 1}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = tip,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // 6. EMERGENCY ASSISTANCE
        item {
            EmergencyHelplineCard()
        }

        // 7. FOOTER
        item {
            CyberShieldFooter(onNavigate = onNavigate)
        }
    }
}

@Composable
private fun ActivityStatChip(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
    }
}
