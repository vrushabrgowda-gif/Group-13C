package com.example.data

enum class ThreatModuleId(
    val title: String,
    val shortTitle: String,
    val route: String
) {
    PHISHING("Phishing Attacks", "Phishing", "phishing"),
    DIGITAL_ARREST("Digital Arrest Scams", "Digital Arrest", "digital_arrest"),
    MALICIOUS_APPS("Malicious Mobile Apps", "Malicious Apps", "malicious_apps")
}

data class PhishingType(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val indianExample: String
)

data class PhishingRedFlag(
    val id: String,
    val title: String,
    val description: String,
    val exampleCue: String
)

data class PhishingScenario(
    val id: String,
    val channel: String,
    val senderLabel: String,
    val messageBody: String,
    val simulatedUrlDisplay: String?,
    val isSuspicious: Boolean,
    val warningSignOptions: List<String>,
    val correctWarningSignIndex: Int,
    val nextActionOptions: List<String>,
    val correctNextActionIndex: Int,
    val explanation: String
)

data class DigitalArrestStep(
    val stepNumber: Int,
    val title: String,
    val description: String
)

data class DigitalArrestOption(
    val id: String,
    val label: String,
    val isSafe: Boolean,
    val feedback: String
)

data class DigitalArrestScenario(
    val id: String,
    val title: String,
    val callerClaim: String,
    val promptText: String,
    val options: List<DigitalArrestOption>,
    val keyTakeaway: String
)

enum class PermissionRiskLevel {
    HIGH_RISK,
    SUSPICIOUS,
    CONTEXTUAL
}

data class AppPermissionDetail(
    val permissionName: String,
    val riskLevel: PermissionRiskLevel,
    val reason: String
)

data class SampleAppProfile(
    val id: String,
    val appName: String,
    val category: String,
    val trustNote: String,
    val isSuspiciousApp: Boolean,
    val permissions: List<AppPermissionDetail>
)

data class SafeAppChecklistItem(
    val id: String,
    val title: String,
    val description: String
)

data class PermissionRiskCombo(
    val id: String,
    val permissionTag: String,
    val summary: String,
    val abuseExample: String
)

data class QuizQuestion(
    val id: Int,
    val category: ThreatModuleId,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class CyberResourceGuide(
    val id: String,
    val title: String,
    val summary: String,
    val actionPoints: List<String>
)

data class ProjectMetadata(
    val collegeName: String = "",
    val teamMembers: String = "",
    val usnNumbers: String = "",
    val projectGuide: String = "",
    val academicYear: String = ""
)

data class FeedbackEntry(
    val id: String,
    val authorName: String,
    val category: String,
    val message: String,
    val timestampLabel: String
)

data class LearningProgressState(
    val completedModules: Set<ThreatModuleId> = emptySet(),
    val phishingSolvedIds: Set<String> = emptySet(),
    val digitalArrestSolvedIds: Set<String> = emptySet(),
    val checklistCheckedIds: Set<String> = emptySet(),
    val latestQuizScore: Int? = null,
    val bestQuizScore: Int? = null,
    val totalQuizAttempts: Int = 0,
    val phishingQuizCorrect: Int = 0,
    val digitalArrestQuizCorrect: Int = 0,
    val maliciousAppsQuizCorrect: Int = 0,
    val projectMetadata: ProjectMetadata = ProjectMetadata(),
    val feedbackHistory: List<FeedbackEntry> = emptyList()
) {
    val modulesCompletedCount: Int get() = completedModules.size

    val overallCompletionPercent: Int
        get() {
            // Transparent calculation:
            // 3 modules (45%), Phishing simulator solved >= 3 (15%), Digital Arrest solved >= 1 (15%),
            // Quiz attempted (15%), Safe App Checklist >= 4 items (10%)
            var total = 0
            total += (completedModules.size * 15)
            if (phishingSolvedIds.size >= 3) total += 15 else total += (phishingSolvedIds.size * 4)
            if (digitalArrestSolvedIds.isNotEmpty()) total += 15
            if (totalQuizAttempts > 0) total += 15
            total += ((checklistCheckedIds.size.coerceAtMost(5)) * 2)
            return total.coerceIn(0, 100)
        }
}
