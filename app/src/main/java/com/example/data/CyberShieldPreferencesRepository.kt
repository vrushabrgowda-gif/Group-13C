package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.cyberShieldDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "cybershield_india_prefs"
)

class CyberShieldPreferencesRepository(private val context: Context) {

    private object Keys {
        val COMPLETED_MODULES = stringSetPreferencesKey("completed_modules")
        val PHISHING_SOLVED = stringSetPreferencesKey("phishing_solved")
        val DIGITAL_ARREST_SOLVED = stringSetPreferencesKey("digital_arrest_solved")
        val CHECKLIST_CHECKED = stringSetPreferencesKey("checklist_checked")
        val LATEST_QUIZ_SCORE = intPreferencesKey("latest_quiz_score")
        val BEST_QUIZ_SCORE = intPreferencesKey("best_quiz_score")
        val TOTAL_QUIZ_ATTEMPTS = intPreferencesKey("total_quiz_attempts")
        val PHISHING_QUIZ_CORRECT = intPreferencesKey("phishing_quiz_correct")
        val DIGITAL_ARREST_QUIZ_CORRECT = intPreferencesKey("digital_arrest_quiz_correct")
        val MALICIOUS_APPS_QUIZ_CORRECT = intPreferencesKey("malicious_apps_quiz_correct")
        val COLLEGE_NAME = stringPreferencesKey("project_college_name")
        val TEAM_MEMBERS = stringPreferencesKey("project_team_members")
        val USN_NUMBERS = stringPreferencesKey("project_usn_numbers")
        val PROJECT_GUIDE = stringPreferencesKey("project_guide")
        val ACADEMIC_YEAR = stringPreferencesKey("project_academic_year")
        val FEEDBACK_LOG = stringPreferencesKey("feedback_log")
    }

    val progressFlow: Flow<LearningProgressState> = context.cyberShieldDataStore.data.map { prefs ->
        val completedModules = (prefs[Keys.COMPLETED_MODULES] ?: emptySet())
            .mapNotNull { name -> ThreatModuleId.entries.firstOrNull { it.name == name } }
            .toSet()

        val metadata = ProjectMetadata(
            collegeName = prefs[Keys.COLLEGE_NAME] ?: "",
            teamMembers = prefs[Keys.TEAM_MEMBERS] ?: "",
            usnNumbers = prefs[Keys.USN_NUMBERS] ?: "",
            projectGuide = prefs[Keys.PROJECT_GUIDE] ?: "",
            academicYear = prefs[Keys.ACADEMIC_YEAR] ?: ""
        )

        val feedbackEntries = decodeFeedbackEntries(prefs[Keys.FEEDBACK_LOG] ?: "")

        LearningProgressState(
            completedModules = completedModules,
            phishingSolvedIds = prefs[Keys.PHISHING_SOLVED] ?: emptySet(),
            digitalArrestSolvedIds = prefs[Keys.DIGITAL_ARREST_SOLVED] ?: emptySet(),
            checklistCheckedIds = prefs[Keys.CHECKLIST_CHECKED] ?: emptySet(),
            latestQuizScore = prefs[Keys.LATEST_QUIZ_SCORE],
            bestQuizScore = prefs[Keys.BEST_QUIZ_SCORE],
            totalQuizAttempts = prefs[Keys.TOTAL_QUIZ_ATTEMPTS] ?: 0,
            phishingQuizCorrect = prefs[Keys.PHISHING_QUIZ_CORRECT] ?: 0,
            digitalArrestQuizCorrect = prefs[Keys.DIGITAL_ARREST_QUIZ_CORRECT] ?: 0,
            maliciousAppsQuizCorrect = prefs[Keys.MALICIOUS_APPS_QUIZ_CORRECT] ?: 0,
            projectMetadata = metadata,
            feedbackHistory = feedbackEntries
        )
    }

    suspend fun toggleModuleCompleted(moduleId: ThreatModuleId, completed: Boolean) {
        context.cyberShieldDataStore.edit { prefs ->
            val current = (prefs[Keys.COMPLETED_MODULES] ?: emptySet()).toMutableSet()
            if (completed) current.add(moduleId.name) else current.remove(moduleId.name)
            prefs[Keys.COMPLETED_MODULES] = current
        }
    }

    suspend fun markPhishingScenarioSolved(scenarioId: String) {
        context.cyberShieldDataStore.edit { prefs ->
            val current = (prefs[Keys.PHISHING_SOLVED] ?: emptySet()).toMutableSet()
            current.add(scenarioId)
            prefs[Keys.PHISHING_SOLVED] = current
        }
    }

    suspend fun markDigitalArrestScenarioSolved(scenarioId: String) {
        context.cyberShieldDataStore.edit { prefs ->
            val current = (prefs[Keys.DIGITAL_ARREST_SOLVED] ?: emptySet()).toMutableSet()
            current.add(scenarioId)
            prefs[Keys.DIGITAL_ARREST_SOLVED] = current
        }
    }

    suspend fun toggleChecklistItem(itemId: String) {
        context.cyberShieldDataStore.edit { prefs ->
            val current = (prefs[Keys.CHECKLIST_CHECKED] ?: emptySet()).toMutableSet()
            if (current.contains(itemId)) current.remove(itemId) else current.add(itemId)
            prefs[Keys.CHECKLIST_CHECKED] = current
        }
    }

    suspend fun saveQuizResult(
        totalCorrect: Int,
        phishingCorrect: Int,
        digitalArrestCorrect: Int,
        maliciousAppsCorrect: Int
    ) {
        context.cyberShieldDataStore.edit { prefs ->
            prefs[Keys.LATEST_QUIZ_SCORE] = totalCorrect
            val prevBest = prefs[Keys.BEST_QUIZ_SCORE] ?: 0
            prefs[Keys.BEST_QUIZ_SCORE] = maxOf(prevBest, totalCorrect)
            prefs[Keys.TOTAL_QUIZ_ATTEMPTS] = (prefs[Keys.TOTAL_QUIZ_ATTEMPTS] ?: 0) + 1
            prefs[Keys.PHISHING_QUIZ_CORRECT] = phishingCorrect
            prefs[Keys.DIGITAL_ARREST_QUIZ_CORRECT] = digitalArrestCorrect
            prefs[Keys.MALICIOUS_APPS_QUIZ_CORRECT] = maliciousAppsCorrect
        }
    }

    suspend fun saveProjectMetadata(metadata: ProjectMetadata) {
        context.cyberShieldDataStore.edit { prefs ->
            prefs[Keys.COLLEGE_NAME] = metadata.collegeName.trim()
            prefs[Keys.TEAM_MEMBERS] = metadata.teamMembers.trim()
            prefs[Keys.USN_NUMBERS] = metadata.usnNumbers.trim()
            prefs[Keys.PROJECT_GUIDE] = metadata.projectGuide.trim()
            prefs[Keys.ACADEMIC_YEAR] = metadata.academicYear.trim()
        }
    }

    suspend fun addLocalFeedback(entry: FeedbackEntry) {
        context.cyberShieldDataStore.edit { prefs ->
            val existing = decodeFeedbackEntries(prefs[Keys.FEEDBACK_LOG] ?: "")
            val updated = (listOf(entry) + existing).take(10)
            prefs[Keys.FEEDBACK_LOG] = encodeFeedbackEntries(updated)
        }
    }

    suspend fun resetLearningProgress() {
        context.cyberShieldDataStore.edit { prefs ->
            prefs.remove(Keys.COMPLETED_MODULES)
            prefs.remove(Keys.PHISHING_SOLVED)
            prefs.remove(Keys.DIGITAL_ARREST_SOLVED)
            prefs.remove(Keys.CHECKLIST_CHECKED)
            prefs.remove(Keys.LATEST_QUIZ_SCORE)
            prefs.remove(Keys.BEST_QUIZ_SCORE)
            prefs.remove(Keys.TOTAL_QUIZ_ATTEMPTS)
            prefs.remove(Keys.PHISHING_QUIZ_CORRECT)
            prefs.remove(Keys.DIGITAL_ARREST_QUIZ_CORRECT)
            prefs.remove(Keys.MALICIOUS_APPS_QUIZ_CORRECT)
        }
    }

    private fun encodeFeedbackEntries(entries: List<FeedbackEntry>): String {
        return entries.joinToString(separator = "\n<<<ENTRY>>>\n") { entry ->
            listOf(
                entry.id.replace("|", " "),
                entry.authorName.replace("|", " "),
                entry.category.replace("|", " "),
                entry.message.replace("|", " ").replace("\n", " "),
                entry.timestampLabel.replace("|", " ")
            ).joinToString("|")
        }
    }

    private fun decodeFeedbackEntries(raw: String): List<FeedbackEntry> {
        if (raw.isBlank()) return emptyList()
        return raw.split("\n<<<ENTRY>>>\n").mapNotNull { line ->
            val parts = line.split("|")
            if (parts.size >= 5) {
                FeedbackEntry(
                    id = parts[0],
                    authorName = parts[1],
                    category = parts[2],
                    message = parts[3],
                    timestampLabel = parts[4]
                )
            } else null
        }
    }
}
