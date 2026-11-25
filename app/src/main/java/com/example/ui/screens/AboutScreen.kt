package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.ProjectMetadata
import com.example.ui.components.CyberCard
import com.example.ui.components.CyberShieldFooter
import com.example.ui.components.SectionHeader
import com.example.ui.theme.LocalCyberColors

@Composable
fun AboutScreen(
    projectMetadata: ProjectMetadata,
    onSaveMetadata: (ProjectMetadata) -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cyber = LocalCyberColors.current

    var collegeName by remember { mutableStateOf(projectMetadata.collegeName) }
    var teamMembers by remember { mutableStateOf(projectMetadata.teamMembers) }
    var usnNumbers by remember { mutableStateOf(projectMetadata.usnNumbers) }
    var projectGuide by remember { mutableStateOf(projectMetadata.projectGuide) }
    var academicYear by remember { mutableStateOf(projectMetadata.academicYear) }
    var savedBanner by remember { mutableStateOf(false) }

    LaunchedEffect(projectMetadata) {
        collegeName = projectMetadata.collegeName
        teamMembers = projectMetadata.teamMembers
        usnNumbers = projectMetadata.usnNumbers
        projectGuide = projectMetadata.projectGuide
        academicYear = projectMetadata.academicYear
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                badge = "VTU COMMUNITY PROJECT",
                title = "About CyberShield India",
                subtitle = "Empowering Indian citizens with practical, accessible cybersecurity education."
            )
        }

        item {
            CyberCard {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.School,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Academic Overview",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                InfoLine("Project Name", "CyberShield India")
                InfoLine("Project Category", "VTU Community Project (3rd Semester)")
                InfoLine("Department", "Computer Science and Engineering")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Project Objective",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "To increase cybersecurity awareness among students, senior citizens and the general public by providing accessible educational resources and interactive learning experiences.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Social Impact",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "As digital payments and online services grow rapidly across India, citizens increasingly face targeted phishing links, 'Digital Arrest' video extortion, and malicious APK fraud. CyberShield India offers risk-free simulations and actionable reporting guidance (1930 / cybercrime.gov.in) without collecting personal data.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // EDITABLE PLACEHOLDERS FOR INSTITUTION / TEAM DETAILS
        item {
            CyberCard {
                Text(
                    text = "Institution & Team Details (Editable Placeholders)",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Enter your VTU college, team members, USNs, guide, and academic year for your demonstration. Saved locally on this device.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = collegeName,
                    onValueChange = { collegeName = it; savedBanner = false },
                    label = { Text("College Name") },
                    placeholder = { Text("Enter your VTU affiliated college name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("about_college_input"),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = teamMembers,
                    onValueChange = { teamMembers = it; savedBanner = false },
                    label = { Text("Project Team Members") },
                    placeholder = { Text("Enter student names (comma-separated)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("about_team_input"),
                    minLines = 2
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = usnNumbers,
                    onValueChange = { usnNumbers = it; savedBanner = false },
                    label = { Text("USN Numbers") },
                    placeholder = { Text("Enter corresponding USN numbers") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("about_usn_input"),
                    minLines = 2
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = projectGuide,
                    onValueChange = { projectGuide = it; savedBanner = false },
                    label = { Text("Project Guide") },
                    placeholder = { Text("Enter faculty guide name & designation") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("about_guide_input"),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = academicYear,
                    onValueChange = { academicYear = it; savedBanner = false },
                    label = { Text("Academic Year") },
                    placeholder = { Text("e.g., 2025-2026") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("about_year_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            onSaveMetadata(
                                ProjectMetadata(
                                    collegeName = collegeName,
                                    teamMembers = teamMembers,
                                    usnNumbers = usnNumbers,
                                    projectGuide = projectGuide,
                                    academicYear = academicYear
                                )
                            )
                            savedBanner = true
                        },
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("about_save_button")
                    ) {
                        Icon(imageVector = Icons.Filled.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save Details")
                    }

                    OutlinedButton(
                        onClick = {
                            val empty = ProjectMetadata()
                            collegeName = ""
                            teamMembers = ""
                            usnNumbers = ""
                            projectGuide = ""
                            academicYear = ""
                            onSaveMetadata(empty)
                            savedBanner = false
                        },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("about_clear_button")
                    ) {
                        Text("Clear")
                    }
                }

                if (savedBanner) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Project team details saved locally for your VTU presentation.",
                        style = MaterialTheme.typography.bodySmall,
                        color = cyber.safe
                    )
                }
            }
        }

        item {
            CyberShieldFooter(onNavigate = onNavigate)
        }
    }
}

@Composable
private fun InfoLine(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 3.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
