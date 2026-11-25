package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.ThreatModuleId
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DigitalArrestScreen
import com.example.ui.screens.FeedbackScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnHubScreen
import com.example.ui.screens.MaliciousAppsScreen
import com.example.ui.screens.PhishingScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.ResourcesScreen
import com.example.ui.theme.LocalCyberColors

private data class NavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

private val primaryNavItems = listOf(
    NavItem(CyberShieldRoutes.HOME, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    NavItem(CyberShieldRoutes.LEARN, "Learn", Icons.Filled.School, Icons.Outlined.School),
    NavItem(CyberShieldRoutes.QUIZ, "Quiz", Icons.Filled.Quiz, Icons.Outlined.Quiz),
    NavItem(CyberShieldRoutes.RESOURCES, "Resources", Icons.Filled.MenuBook, Icons.Outlined.MenuBook),
    NavItem(CyberShieldRoutes.DASHBOARD, "Progress", Icons.Filled.Analytics, Icons.Outlined.Analytics)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CyberShieldApp(
    viewModel: CyberShieldViewModel = viewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: CyberShieldRoutes.HOME

    val progressState by viewModel.progressState.collectAsStateWithLifecycle()
    val quizState by viewModel.quizState.collectAsStateWithLifecycle()
    val cyber = LocalCyberColors.current

    var showMenu by remember { mutableStateOf(false) }

    val navigateTo: (String) -> Unit = { route ->
        if (route != currentRoute) {
            val isPrimary = primaryNavItems.any { it.route == route }
            navController.navigate(route) {
                if (isPrimary) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                }
                launchSingleTop = true
            }
        }
    }

    val isSubScreen = currentRoute in setOf(
        CyberShieldRoutes.PHISHING,
        CyberShieldRoutes.DIGITAL_ARREST,
        CyberShieldRoutes.MALICIOUS_APPS,
        CyberShieldRoutes.ABOUT,
        CyberShieldRoutes.FEEDBACK
    )

    val screenTitle = when (currentRoute) {
        CyberShieldRoutes.PHISHING -> "Phishing Awareness"
        CyberShieldRoutes.DIGITAL_ARREST -> "Digital Arrest Scams"
        CyberShieldRoutes.MALICIOUS_APPS -> "Malicious Mobile Apps"
        CyberShieldRoutes.ABOUT -> "About VTU Project"
        CyberShieldRoutes.FEEDBACK -> "Contact & Feedback"
        else -> "CyberShield India"
    }

    val activePrimaryRoute = when (currentRoute) {
        CyberShieldRoutes.PHISHING,
        CyberShieldRoutes.DIGITAL_ARREST,
        CyberShieldRoutes.MALICIOUS_APPS -> CyberShieldRoutes.LEARN
        else -> currentRoute
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val useNavigationRail = maxWidth >= 600.dp

        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (!isSubScreen) {
                                Icon(
                                    imageVector = Icons.Filled.Shield,
                                    contentDescription = "CyberShield India logo",
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = screenTitle,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (!isSubScreen) {
                                    Text(
                                        text = "Think Before You Click",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.secondary,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    },
                    navigationIcon = {
                        if (isSubScreen) {
                            IconButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier.testTag("topbar_back_button")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Navigate back"
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { navigateTo(CyberShieldRoutes.ABOUT) },
                            modifier = Modifier.testTag("topbar_about_button")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "About Project",
                                tint = if (currentRoute == CyberShieldRoutes.ABOUT) {
                                    MaterialTheme.colorScheme.secondary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }
                        IconButton(
                            onClick = { navigateTo(CyberShieldRoutes.FEEDBACK) },
                            modifier = Modifier.testTag("topbar_feedback_button")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Feedback,
                                contentDescription = "Contact & Feedback",
                                tint = if (currentRoute == CyberShieldRoutes.FEEDBACK) {
                                    MaterialTheme.colorScheme.secondary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }
                        Box {
                            IconButton(
                                onClick = { showMenu = true },
                                modifier = Modifier.testTag("topbar_menu_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = "All Sections Menu"
                                )
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                val menuItems = listOf(
                                    "Home" to CyberShieldRoutes.HOME,
                                    "Learn Hub" to CyberShieldRoutes.LEARN,
                                    "Phishing Awareness" to CyberShieldRoutes.PHISHING,
                                    "Digital Arrest Scams" to CyberShieldRoutes.DIGITAL_ARREST,
                                    "Malicious Mobile Apps" to CyberShieldRoutes.MALICIOUS_APPS,
                                    "Cyber Safety Quiz" to CyberShieldRoutes.QUIZ,
                                    "Resource Centre & 1930" to CyberShieldRoutes.RESOURCES,
                                    "Learning Dashboard" to CyberShieldRoutes.DASHBOARD,
                                    "About VTU Project" to CyberShieldRoutes.ABOUT,
                                    "Contact & Feedback" to CyberShieldRoutes.FEEDBACK
                                )
                                menuItems.forEach { (label, route) ->
                                    DropdownMenuItem(
                                        text = { Text(label) },
                                        onClick = {
                                            showMenu = false
                                            navigateTo(route)
                                        }
                                    )
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = cyber.deepBackdrop,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            },
            bottomBar = {
                if (!useNavigationRail) {
                    NavigationBar(
                        containerColor = cyber.deepBackdrop,
                        tonalElevation = 0.dp
                    ) {
                        primaryNavItems.forEach { item ->
                            val selected = activePrimaryRoute == item.route
                            NavigationBarItem(
                                selected = selected,
                                onClick = { navigateTo(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = item.label
                                    )
                                },
                                label = {
                                    Text(
                                        text = item.label,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                modifier = Modifier.testTag("bottom_nav_${item.route}"),
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (useNavigationRail) {
                    NavigationRail(
                        containerColor = cyber.deepBackdrop,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        primaryNavItems.forEach { item ->
                            val selected = activePrimaryRoute == item.route
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateTo(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = item.label
                                    )
                                },
                                label = { Text(item.label) },
                                modifier = Modifier.testTag("rail_nav_${item.route}"),
                                colors = NavigationRailItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = CyberShieldRoutes.HOME,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .widthIn(max = 860.dp)
                    ) {
                        composable(CyberShieldRoutes.HOME) {
                            HomeScreen(
                                progress = progressState,
                                onNavigate = navigateTo
                            )
                        }
                        composable(CyberShieldRoutes.LEARN) {
                            LearnHubScreen(
                                progress = progressState,
                                onNavigate = navigateTo
                            )
                        }
                        composable(CyberShieldRoutes.PHISHING) {
                            PhishingScreen(
                                progress = progressState,
                                onScenarioSolved = { id -> viewModel.markPhishingScenarioSolved(id) },
                                onToggleModuleCompleted = { done ->
                                    viewModel.setModuleCompleted(ThreatModuleId.PHISHING, done)
                                },
                                onNavigate = navigateTo
                            )
                        }
                        composable(CyberShieldRoutes.DIGITAL_ARREST) {
                            DigitalArrestScreen(
                                progress = progressState,
                                onScenarioSolved = { id -> viewModel.markDigitalArrestScenarioSolved(id) },
                                onToggleModuleCompleted = { done ->
                                    viewModel.setModuleCompleted(ThreatModuleId.DIGITAL_ARREST, done)
                                },
                                onNavigate = navigateTo
                            )
                        }
                        composable(CyberShieldRoutes.MALICIOUS_APPS) {
                            MaliciousAppsScreen(
                                progress = progressState,
                                onToggleChecklist = { id -> viewModel.toggleChecklistItem(id) },
                                onToggleModuleCompleted = { done ->
                                    viewModel.setModuleCompleted(ThreatModuleId.MALICIOUS_APPS, done)
                                },
                                onNavigate = navigateTo
                            )
                        }
                        composable(CyberShieldRoutes.QUIZ) {
                            QuizScreen(
                                quizState = quizState,
                                progress = progressState,
                                onSelectOption = { viewModel.selectQuizOption(it) },
                                onSubmitAnswer = { viewModel.submitQuizAnswer() },
                                onNextQuestion = { viewModel.nextQuizQuestion() },
                                onRetakeQuiz = { viewModel.retakeQuiz() },
                                onNavigate = navigateTo
                            )
                        }
                        composable(CyberShieldRoutes.RESOURCES) {
                            ResourcesScreen(onNavigate = navigateTo)
                        }
                        composable(CyberShieldRoutes.DASHBOARD) {
                            DashboardScreen(
                                progress = progressState,
                                onNavigate = navigateTo,
                                onResetProgress = { viewModel.resetProgress() }
                            )
                        }
                        composable(CyberShieldRoutes.ABOUT) {
                            AboutScreen(
                                projectMetadata = progressState.projectMetadata,
                                onSaveMetadata = { viewModel.saveProjectMetadata(it) },
                                onNavigate = navigateTo
                            )
                        }
                        composable(CyberShieldRoutes.FEEDBACK) {
                            FeedbackScreen(
                                feedbackHistory = progressState.feedbackHistory,
                                onSubmitFeedback = { author, cat, msg ->
                                    viewModel.submitFeedback(author, cat, msg)
                                },
                                onNavigate = navigateTo
                            )
                        }
                    }
                }
            }
        }
    }
}
