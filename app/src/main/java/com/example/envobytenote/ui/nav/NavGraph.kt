package com.example.envobytenote.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.envobytenote.ui.screens.edit.EditScreen
import com.example.envobytenote.ui.screens.home.HomeScreen

object NavRoutes {
    const val HOME = "home"
    const val EDIT = "edit/{noteId}"
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    onToggleTheme: () -> Unit,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME,
        modifier = modifier
    ) {
        composable(NavRoutes.HOME) {
            HomeScreen(
                onEditNote = { noteId ->
                    navController.navigate("edit/$noteId")
                },
                onToggleTheme = onToggleTheme,
                isDarkTheme = isDarkTheme
            )
        }

        composable(
            route = NavRoutes.EDIT,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: -1L
            EditScreen(noteId = noteId, onBack = { navController.popBackStack() })
        }
    }
}