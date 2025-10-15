package com.example.envobytenote.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.envobytenote.ui.screens.home.HomeTopBar
import com.example.envobytenote.ui.screens.home.NoteItem
import com.example.envobytenote.ui.screens.home.NotesFilterSegmentedButtons
import com.example.envobytenote.ui.viewmodel.NoteViewModel

@Composable
fun HomeScreen(
    noteViewModel: NoteViewModel = hiltViewModel(),
    onEditNote: (Long) -> Unit,
    onToggleTheme: () -> Unit,
    isDarkTheme: Boolean
) {
    val uiState by noteViewModel.uiState.collectAsState()

    // Decide which notes to display
    val displayNotes = when {
        uiState.searchQuery.isNotBlank() -> uiState.searchResults
        uiState.showFavorites -> uiState.favorites
        else -> uiState.notes
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                searchQuery = uiState.searchQuery,
                onSearchChange = noteViewModel::searchNotes,
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEditNote(-1L) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NotesFilterSegmentedButtons(
                onAllSelected = {
                    if (uiState.showFavorites) noteViewModel.toggleShowFavorites()
                },
                onFavoriteSelected = {
                    if (!uiState.showFavorites) noteViewModel.toggleShowFavorites()
                }
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(displayNotes) { note ->
                    NoteItem(
                        note = note,
                        onClick = { onEditNote(note.id) },
                        onToggleFavorite = { noteViewModel.updateFavorite(note.id, !note.isFavorite) }
                    )
                }
            }
        }
    }
}











