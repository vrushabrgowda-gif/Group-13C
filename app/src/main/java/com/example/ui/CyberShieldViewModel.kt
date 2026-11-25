package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CyberShieldContent
import com.example.data.CyberShieldPreferencesRepository
import com.example.data.FeedbackEntry
import com.example.data.LearningProgressState
import com.example.data.ProjectMetadata
import com.example.data.QuizQuestion
import com.example.data.ThreatModuleId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class QuizSessionState(
    val questions: List<QuizQuestion> = CyberShieldContent.quizQuestions,
    val currentIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswerSubmitted: Boolean = false,
    val totalCorrect: Int = 0,
    val phishingCorrect: Int = 0,
    val digitalArrestCorrect: Int = 0,
    val maliciousAppsCorrect: Int = 0,
    val isCompleted: Boolean = false
) {
    val currentQuestion: QuizQuestion get() = questions[currentIndex]
    val totalQuestions: Int get() = questions.size
}

class CyberShieldViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CyberShieldPreferencesRepository(application.applicationContext)

    val progressState: StateFlow<LearningProgressState> = repository.progressFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LearningProgressState()
    )

    private val _quizState = MutableStateFlow(QuizSessionState())
    val quizState: StateFlow<QuizSessionState> = _quizState.asStateFlow()

    fun setModuleCompleted(moduleId: ThreatModuleId, completed: Boolean) {
        viewModelScope.launch {
            repository.toggleModuleCompleted(moduleId, completed)
        }
    }

    fun markPhishingScenarioSolved(scenarioId: String) {
        viewModelScope.launch {
            repository.markPhishingScenarioSolved(scenarioId)
        }
    }

    fun markDigitalArrestScenarioSolved(scenarioId: String) {
        viewModelScope.launch {
            repository.markDigitalArrestScenarioSolved(scenarioId)
        }
    }

    fun toggleChecklistItem(itemId: String) {
        viewModelScope.launch {
            repository.toggleChecklistItem(itemId)
        }
    }

    fun selectQuizOption(index: Int) {
        _quizState.update { current ->
            if (current.isAnswerSubmitted || current.isCompleted) current
            else current.copy(selectedOptionIndex = index)
        }
    }

    fun submitQuizAnswer() {
        _quizState.update { current ->
            val selected = current.selectedOptionIndex ?: return@update current
            if (current.isAnswerSubmitted) return@update current

            val q = current.currentQuestion
            val isRight = selected == q.correctIndex

            val newTotal = current.totalCorrect + if (isRight) 1 else 0
            val newPhishing = current.phishingCorrect + if (isRight && q.category == ThreatModuleId.PHISHING) 1 else 0
            val newDigitalArrest = current.digitalArrestCorrect + if (isRight && q.category == ThreatModuleId.DIGITAL_ARREST) 1 else 0
            val newMaliciousApps = current.maliciousAppsCorrect + if (isRight && q.category == ThreatModuleId.MALICIOUS_APPS) 1 else 0

            current.copy(
                isAnswerSubmitted = true,
                totalCorrect = newTotal,
                phishingCorrect = newPhishing,
                digitalArrestCorrect = newDigitalArrest,
                maliciousAppsCorrect = newMaliciousApps
            )
        }
    }

    fun nextQuizQuestion() {
        val current = _quizState.value
        if (!current.isAnswerSubmitted) return

        if (current.currentIndex + 1 >= current.totalQuestions) {
            _quizState.update { it.copy(isCompleted = true) }
            viewModelScope.launch {
                repository.saveQuizResult(
                    totalCorrect = current.totalCorrect,
                    phishingCorrect = current.phishingCorrect,
                    digitalArrestCorrect = current.digitalArrestCorrect,
                    maliciousAppsCorrect = current.maliciousAppsCorrect
                )
            }
        } else {
            _quizState.update {
                it.copy(
                    currentIndex = it.currentIndex + 1,
                    selectedOptionIndex = null,
                    isAnswerSubmitted = false
                )
            }
        }
    }

    fun retakeQuiz() {
        _quizState.value = QuizSessionState()
    }

    fun saveProjectMetadata(metadata: ProjectMetadata) {
        viewModelScope.launch {
            repository.saveProjectMetadata(metadata)
        }
    }

    fun submitFeedback(authorName: String, category: String, message: String) {
        val cleanAuthor = authorName.trim().ifBlank { "Anonymous Citizen" }
        val formattedTime = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date())
        val entry = FeedbackEntry(
            id = UUID.randomUUID().toString().take(8),
            authorName = cleanAuthor,
            category = category,
            message = message.trim(),
            timestampLabel = formattedTime
        )
        viewModelScope.launch {
            repository.addLocalFeedback(entry)
        }
    }

    fun resetProgress() {
        viewModelScope.launch {
            repository.resetLearningProgress()
            _quizState.value = QuizSessionState()
        }
    }
}
