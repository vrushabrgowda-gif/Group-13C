package com.example

import com.example.data.CyberShieldContent
import com.example.data.LearningProgressState
import com.example.data.ThreatModuleId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CyberShieldUnitTest {

    @Test
    fun `quiz contains at least 15 meaningful questions across all three threat categories`() {
        val questions = CyberShieldContent.quizQuestions
        assertTrue(questions.size >= 15)
        assertTrue(questions.any { it.category == ThreatModuleId.PHISHING })
        assertTrue(questions.any { it.category == ThreatModuleId.DIGITAL_ARREST })
        assertTrue(questions.any { it.category == ThreatModuleId.MALICIOUS_APPS })

        questions.forEach { q ->
            assertEquals(4, q.options.size)
            assertTrue(q.correctIndex in 0..3)
            assertTrue(q.explanation.isNotBlank())
        }
    }

    @Test
    fun `phishing simulator scenarios cover safe and suspicious cases with non-clickable URLs`() {
        val scenarios = CyberShieldContent.phishingScenarios
        assertTrue(scenarios.size >= 3)
        assertTrue(scenarios.any { it.isSuspicious })
        assertTrue(scenarios.any { !it.isSuspicious })
        scenarios.forEach { scenario ->
            scenario.simulatedUrlDisplay?.let { url ->
                assertFalse(url.startsWith("http://"))
                assertFalse(url.startsWith("https://"))
            }
        }
    }

    @Test
    fun `learning progress percentage accurately reflects completed user activity`() {
        val empty = LearningProgressState()
        assertEquals(0, empty.overallCompletionPercent)
        assertEquals(0, empty.modulesCompletedCount)

        val complete = LearningProgressState(
            completedModules = ThreatModuleId.entries.toSet(),
            phishingSolvedIds = setOf("phish_1", "phish_2", "phish_3"),
            digitalArrestSolvedIds = setOf("da_1"),
            checklistCheckedIds = setOf("chk_store", "chk_dev", "chk_perm", "chk_apk", "chk_reviews"),
            totalQuizAttempts = 1,
            latestQuizScore = 14
        )
        assertEquals(3, complete.modulesCompletedCount)
        assertEquals(100, complete.overallCompletionPercent)
    }
}
