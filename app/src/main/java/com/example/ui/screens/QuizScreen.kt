package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.LearningProgressState
import com.example.data.ThreatModuleId
import com.example.ui.CyberShieldRoutes
import com.example.ui.QuizSessionState
import com.example.ui.components.CyberCard
import com.example.ui.components.CyberShieldFooter
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatusPill
import com.example.ui.theme.LocalCyberColors

@Composable
fun QuizScreen(
    quizState: QuizSessionState,
    progress: LearningProgressState,
    onSelectOption: (Int) -> Unit,
    onSubmitAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onRetakeQuiz: () -> Unit,
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
                badge = "KNOWLEDGE ASSESSMENT",
                title = "Cyber Safety Quiz",
                subtitle = "16 real-world questions across Phishing, Digital Arrest, and Malicious Apps."
            )
        }

        if (!quizState.isCompleted) {
            val q = quizState.currentQuestion
            val questionProgress = (quizState.currentIndex + 1).toFloat() / quizState.totalQuestions.toFloat()

            item {
                CyberCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatusPill(
                            text = q.category.title,
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "Question ${quizState.currentIndex + 1} / ${quizState.totalQuestions}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { questionProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(MaterialTheme.shapes.extraSmall),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceContainerHighest
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = q.question,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        q.options.forEachIndexed { idx, optionText ->
                            val isSelected = quizState.selectedOptionIndex == idx
                            val isCorrectOption = idx == q.correctIndex

                            val border = when {
                                quizState.isAnswerSubmitted && isCorrectOption -> cyber.safe
                                quizState.isAnswerSubmitted && isSelected && !isCorrectOption -> cyber.alert
                                isSelected -> MaterialTheme.colorScheme.secondary
                                else -> cyber.cardBorder
                            }
                            val bg = when {
                                quizState.isAnswerSubmitted && isCorrectOption -> cyber.safeContainer
                                quizState.isAnswerSubmitted && isSelected && !isCorrectOption -> cyber.alertContainer
                                isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .minimumInteractiveComponentSize()
                                    .clickable(enabled = !quizState.isAnswerSubmitted) {
                                        onSelectOption(idx)
                                    }
                                    .testTag("quiz_option_$idx"),
                                shape = MaterialTheme.shapes.small,
                                colors = CardDefaults.cardColors(containerColor = bg),
                                border = BorderStroke(1.dp, border)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { if (!quizState.isAnswerSubmitted) onSelectOption(idx) }
                                    )
                                    Text(
                                        text = optionText,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.weight(1f)
                                    )
                                    if (quizState.isAnswerSubmitted && isCorrectOption) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = "Correct option",
                                            tint = cyber.safe
                                        )
                                    } else if (quizState.isAnswerSubmitted && isSelected && !isCorrectOption) {
                                        Icon(
                                            imageVector = Icons.Filled.Cancel,
                                            contentDescription = "Incorrect option",
                                            tint = cyber.alert
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (!quizState.isAnswerSubmitted) {
                        Button(
                            onClick = onSubmitAnswer,
                            enabled = quizState.selectedOptionIndex != null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .minimumInteractiveComponentSize()
                                .testTag("quiz_submit_answer_button")
                        ) {
                            Text("Submit Answer")
                        }
                    } else {
                        val wasCorrect = quizState.selectedOptionIndex == q.correctIndex
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.small)
                                .background(if (wasCorrect) cyber.safeContainer else cyber.cautionContainer)
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = if (wasCorrect) "Correct Answer!" else "Incorrect — Here's Why:",
                                style = MaterialTheme.typography.titleSmall,
                                color = if (wasCorrect) cyber.safe else cyber.caution,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = q.explanation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = onNextQuestion,
                            modifier = Modifier
                                .fillMaxWidth()
                                .minimumInteractiveComponentSize()
                                .testTag("quiz_next_button")
                        ) {
                            Text(
                                if (quizState.currentIndex + 1 >= quizState.totalQuestions) {
                                    "View Final Score"
                                } else {
                                    "Next Question"
                                }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                        }
                    }
                }
            }
        } else {
            // FINAL RESULTS VIEW
            val score = quizState.totalCorrect
            val total = quizState.totalQuestions
            val pct = (score * 100) / total
            val summaryFeedback = buildPersonalizedSummary(
                score = score,
                total = total,
                phishingScore = quizState.phishingCorrect,
                digitalArrestScore = quizState.digitalArrestCorrect,
                maliciousAppsScore = quizState.maliciousAppsCorrect
            )

            item {
                CyberCard(borderColor = MaterialTheme.colorScheme.secondary) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.EmojiEvents,
                            contentDescription = "Quiz completed",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "Cyber Safety Score: $score/$total",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "$pct% accuracy • Saved to your Learning Dashboard",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = { score.toFloat() / total.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(MaterialTheme.shapes.extraSmall),
                        color = if (pct >= 75) cyber.safe else cyber.caution,
                        trackColor = MaterialTheme.colorScheme.surfaceContainerHighest
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Category Breakdown",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CategoryScoreRow("Phishing Attacks", quizState.phishingCorrect, 6)
                    CategoryScoreRow("Digital Arrest Scams", quizState.digitalArrestCorrect, 5)
                    CategoryScoreRow("Malicious Mobile Apps", quizState.maliciousAppsCorrect, 5)

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Personalized Summary",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = summaryFeedback,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onRetakeQuiz,
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("quiz_retake_button")
                        ) {
                            Icon(imageVector = Icons.Filled.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Retake Quiz")
                        }
                        OutlinedButton(
                            onClick = { onNavigate(CyberShieldRoutes.DASHBOARD) },
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("quiz_view_dashboard_button")
                        ) {
                            Text("View Progress")
                        }
                    }
                }
            }

            if (progress.bestQuizScore != null) {
                item {
                    CyberCard {
                        Text(
                            text = "Best Recorded Score: ${progress.bestQuizScore}/$total (${progress.totalQuizAttempts} total attempts)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        item {
            CyberShieldFooter(onNavigate = onNavigate)
        }
    }
}

@Composable
private fun CategoryScoreRow(label: String, correct: Int, total: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "$correct / $total",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

private fun buildPersonalizedSummary(
    score: Int,
    total: Int,
    phishingScore: Int,
    digitalArrestScore: Int,
    maliciousAppsScore: Int
): String {
    val weakTopics = mutableListOf<String>()
    if (phishingScore < 5) weakTopics.add("Phishing Attacks")
    if (digitalArrestScore < 4) weakTopics.add("Digital Arrest Scams")
    if (maliciousAppsScore < 4) weakTopics.add("Malicious Mobile Apps")

    return when {
        score >= 14 -> "Outstanding cyber awareness! You demonstrated strong judgment across phishing links, digital arrest intimidation, and mobile app permissions. Share these habits with family members and elders."
        score >= 10 -> {
            val focus = if (weakTopics.isNotEmpty()) {
                " Consider reviewing: ${weakTopics.joinToString(", ")}."
            } else ""
            "Good foundational cyber safety awareness.$focus Practicing the interactive simulations will sharpen your instincts under pressure."
        }
        else -> {
            val focus = if (weakTopics.isNotEmpty()) weakTopics.joinToString(", ") else "all three modules"
            "You are building your digital defense. We strongly recommend revisiting $focus and practicing the safe simulator cases before retaking the quiz."
        }
    }
}
