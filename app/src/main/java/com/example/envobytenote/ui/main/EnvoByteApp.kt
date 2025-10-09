package com.example.envobytenote.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.envobytenote.ui.nav.AppNavGraph
import com.example.envobytenote.ui.theme.EnvoByteNoteTheme
import com.example.envobytenote.ui.viewmodel.NoteViewModel
import com.example.envobytenote.ui.viewmodel.ThemeViewModel
import com.example.envobytenote.util.AddDefaultNotesIfFirstLaunch
import com.example.envobytenote.util.getDefaultNotes

@Composable
fun EnvoByteApp() {
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val noteViewModel: NoteViewModel = hiltViewModel()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val context = LocalContext.current
    var isFirstLaunchChecked by remember { mutableStateOf(false) }


    // Adds default notes on first launch
    val defaultNotes = remember { getDefaultNotes(context) }
    AddDefaultNotesIfFirstLaunch(
        noteViewModel = noteViewModel,
        context = context,
        defaultNotes = defaultNotes,
        onDone = { isFirstLaunchChecked = true }
    )
    // -------------------------------------------------------------------


    if (isFirstLaunchChecked) {
        EnvoByteNoteTheme(isDarkTheme) {
            Scaffold(modifier = Modifier.fillMaxSize()) { _ ->

                val navController = rememberNavController()
                AppNavGraph(navController, { themeViewModel.toggleTheme() }, isDarkTheme)

            }
        }
    }
}


