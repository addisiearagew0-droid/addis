package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.model.KingsRepository
import com.example.ui.screens.KingDetailScreen
import com.example.ui.screens.PhotoMatcherScreen
import com.example.ui.screens.RoyalArchiveScreen
import com.example.ui.theme.EthiopianKingsTheme
import com.example.viewmodel.KingsViewModel

const val ROUTE_PHOTO_MATCHER = "photo_matcher"
const val ROUTE_ROYAL_ARCHIVE = "royal_archive"
const val ROUTE_KING_DETAIL = "king_detail/{kingId}"

private data class TopLevelDestination(
    val route: String,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String,
)

private val topLevelDestinations = listOf(
    TopLevelDestination(
        route = ROUTE_PHOTO_MATCHER,
        labelRes = R.string.nav_photo_matcher,
        selectedIcon = Icons.Filled.PhotoLibrary,
        unselectedIcon = Icons.Outlined.PhotoLibrary,
        testTag = "nav_photo_matcher",
    ),
    TopLevelDestination(
        route = ROUTE_ROYAL_ARCHIVE,
        labelRes = R.string.nav_royal_archive,
        selectedIcon = Icons.AutoMirrored.Filled.MenuBook,
        unselectedIcon = Icons.AutoMirrored.Outlined.MenuBook,
        testTag = "nav_royal_archive",
    ),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EthiopianKingsTheme {
                EthiopianKingsApp()
            }
        }
    }
}

@Composable
fun EthiopianKingsApp(
    kingsViewModel: KingsViewModel = viewModel(),
) {
    val uiState by kingsViewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isTopLevelRoute = topLevelDestinations.any { dest ->
        currentDestination?.hierarchy?.any { it.route == dest.route } == true
    }

    fun navigateToTopLevel(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavRail = maxWidth >= 600.dp

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.safeDrawing,
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                if (!useNavRail && isTopLevelRoute) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ) {
                        topLevelDestinations.forEach { destination ->
                            val selected = currentDestination?.hierarchy?.any {
                                it.route == destination.route
                            } == true
                            NavigationBarItem(
                                selected = selected,
                                onClick = { navigateToTopLevel(destination.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                                        contentDescription = stringResource(destination.labelRes),
                                    )
                                },
                                label = { Text(stringResource(destination.labelRes)) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                                ),
                                modifier = Modifier.testTag(destination.testTag),
                            )
                        }
                    }
                }
            },
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                if (useNavRail && isTopLevelRoute) {
                    NavigationRail(
                        modifier = Modifier.fillMaxHeight(),
                        containerColor = MaterialTheme.colorScheme.surface,
                    ) {
                        topLevelDestinations.forEach { destination ->
                            val selected = currentDestination?.hierarchy?.any {
                                it.route == destination.route
                            } == true
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateToTopLevel(destination.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                                        contentDescription = stringResource(destination.labelRes),
                                    )
                                },
                                label = { Text(stringResource(destination.labelRes)) },
                                modifier = Modifier.testTag(destination.testTag),
                            )
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = ROUTE_PHOTO_MATCHER,
                    modifier = Modifier.weight(1f),
                ) {
                    composable(ROUTE_PHOTO_MATCHER) {
                        PhotoMatcherScreen(
                            uiState = uiState,
                            onSelectKing = kingsViewModel::onSelectKing,
                        )
                    }

                    composable(ROUTE_ROYAL_ARCHIVE) {
                        RoyalArchiveScreen(
                            uiState = uiState,
                            onOpenKingDetailCompact = { king ->
                                navController.navigate("king_detail/${king.id}")
                            },
                            onSelectKingDetailWide = kingsViewModel::onSelectArchiveKing,
                        )
                    }

                    composable(
                        route = ROUTE_KING_DETAIL,
                        arguments = listOf(navArgument("kingId") { type = NavType.StringType }),
                    ) { backStackEntry ->
                        val kingId = backStackEntry.arguments?.getString("kingId")
                        val king = KingsRepository.findById(kingId) ?: KingsRepository.allKings.first()
                        KingDetailScreen(
                            king = king,
                            onBack = { navController.popBackStack() },
                        )
                    }
                }
            }
        }
    }
}
