package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppBlocking
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Checkbox
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
import com.example.data.PermissionRiskLevel
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
fun MaliciousAppsScreen(
    progress: LearningProgressState,
    onToggleChecklist: (String) -> Unit,
    onToggleModuleCompleted: (Boolean) -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cyber = LocalCyberColors.current
    var selectedAppIdx by remember { mutableIntStateOf(0) }
    val appProfile = CyberShieldContent.sampleAppProfiles[selectedAppIdx]

    var selectedCombos by remember { mutableStateOf(setOf("sms", "contacts")) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.img_malicious_apps),
                contentDescription = "Malicious mobile application awareness illustration",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(MaterialTheme.shapes.large),
                contentScale = ContentScale.Crop
            )
        }

        // EDUCATIONAL OVERVIEW TOPICS
        item {
            SectionHeader(
                badge = "MODULE 03 • MALICIOUS APPLICATIONS",
                title = "Mobile App & Permission Security",
                subtitle = "Learn how rogue APKs, loan apps, and screen-sharing traps steal data."
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CyberShieldContent.maliciousAppTopics.forEach { (title, body) ->
                    CyberCard {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = body,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // A. APP PERMISSION AWARENESS INSPECTOR
        item {
            SectionHeader(
                badge = "INTERACTIVE PERMISSION INSPECTOR",
                title = "Inspect Sample App Permissions",
                subtitle = "Compare what an app claims to do versus the permissions it asks for."
            )
            Spacer(modifier = Modifier.height(10.dp))

            AwarenessBanner(
                title = "Educational Demonstration Only",
                message = "These are hypothetical app permission profiles for awareness training. CyberShield India does not scan installed apps or claim malware detection.",
                isWarning = false
            )
            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CyberShieldContent.sampleAppProfiles.forEachIndexed { idx, profile ->
                    FilterChip(
                        selected = selectedAppIdx == idx,
                        onClick = { selectedAppIdx = idx },
                        label = { Text(profile.appName.substringBefore(" (")) },
                        modifier = Modifier.testTag("app_inspector_chip_${profile.id}")
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            CyberCard(
                borderColor = if (appProfile.isSuspiciousApp) cyber.alert.copy(alpha = 0.5f) else cyber.safe.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = appProfile.appName,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = appProfile.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    StatusPill(
                        text = if (appProfile.isSuspiciousApp) "Suspicious" else "Contextual",
                        containerColor = if (appProfile.isSuspiciousApp) cyber.alertContainer else cyber.safeContainer,
                        contentColor = if (appProfile.isSuspiciousApp) cyber.alert else cyber.safe,
                        icon = if (appProfile.isSuspiciousApp) Icons.Filled.AppBlocking else Icons.Filled.Shield
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = appProfile.trustNote,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    appProfile.permissions.forEach { perm ->
                        val (pillBg, pillFg, label) = when (perm.riskLevel) {
                            PermissionRiskLevel.HIGH_RISK -> Triple(cyber.alertContainer, cyber.alert, "High Risk")
                            PermissionRiskLevel.SUSPICIOUS -> Triple(cyber.cautionContainer, cyber.caution, "Suspicious")
                            PermissionRiskLevel.CONTEXTUAL -> Triple(cyber.safeContainer, cyber.safe, "Contextual")
                        }
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            border = BorderStroke(1.dp, cyber.cardBorder)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = perm.permissionName,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    StatusPill(
                                        text = label,
                                        containerColor = pillBg,
                                        contentColor = pillFg
                                    )
                                }
                                Text(
                                    text = perm.reason,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        // B. SAFE APP SECURITY CHECKLIST
        item {
            SectionHeader(
                title = "Safe App Security Checklist",
                subtitle = "Track these 7 essential habits—saved locally to your progress."
            )
            Spacer(modifier = Modifier.height(10.dp))
            CyberCard {
                Text(
                    text = "${progress.checklistCheckedIds.size} of ${CyberShieldContent.safeAppChecklist.size} completed",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                CyberShieldContent.safeAppChecklist.forEach { item ->
                    val checked = progress.checklistCheckedIds.contains(item.id)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .clickable { onToggleChecklist(item.id) }
                            .padding(vertical = 6.dp)
                            .testTag("checklist_${item.id}"),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { onToggleChecklist(item.id) }
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleSmall,
                                color = if (checked) cyber.safe else MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // C. APPLICATION RISK EDUCATION (PERMISSION COMBINATION EXPLORER)
        item {
            SectionHeader(
                title = "Permission Combination Risk Explorer",
                subtitle = "Toggle hypothetical permission combinations to understand the privacy risks."
            )
            Spacer(modifier = Modifier.height(10.dp))
            CyberCard {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CyberShieldContent.permissionRiskCombos.forEach { combo ->
                        val selected = selectedCombos.contains(combo.id)
                        FilterChip(
                            selected = selected,
                            onClick = {
                                selectedCombos = if (selected) {
                                    selectedCombos - combo.id
                                } else {
                                    selectedCombos + combo.id
                                }
                            },
                            label = { Text(combo.permissionTag) },
                            modifier = Modifier.testTag("combo_chip_${combo.id}")
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))

                val activeCombos = CyberShieldContent.permissionRiskCombos.filter {
                    selectedCombos.contains(it.id)
                }

                if (activeCombos.isEmpty()) {
                    Text(
                        text = "Select one or more permissions above to see how attackers abuse that combination.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    val riskTier = when {
                        selectedCombos.contains("accessibility") || selectedCombos.size >= 3 ->
                            "Severe Privacy & Financial Takeover Exposure"
                        selectedCombos.size == 2 ->
                            "Elevated Data Harvesting & OTP Risk"
                        else ->
                            "Targeted Privacy Exposure"
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PrivacyTip,
                            contentDescription = null,
                            tint = cyber.caution
                        )
                        Text(
                            text = riskTier,
                            style = MaterialTheme.typography.titleSmall,
                            color = cyber.caution,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        activeCombos.forEach { combo ->
                            Surface(
                                shape = MaterialTheme.shapes.small,
                                color = MaterialTheme.colorScheme.surfaceContainerHigh
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Text(
                                        text = "${combo.permissionTag} — ${combo.summary}",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    Text(
                                        text = combo.abuseExample,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            ModuleCompletionCard(
                moduleName = ThreatModuleId.MALICIOUS_APPS.title,
                isCompleted = progress.completedModules.contains(ThreatModuleId.MALICIOUS_APPS),
                onToggleCompleted = onToggleModuleCompleted
            )
        }

        item {
            CyberShieldFooter(onNavigate = onNavigate)
        }
    }
}
