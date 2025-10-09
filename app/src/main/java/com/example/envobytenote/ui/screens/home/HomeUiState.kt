package com.example.envobytenote.ui.screens.home

import com.example.envobytenote.data.local.room.Note

data class HomeUiState(
    val notes: List<Note> = emptyList(),
    val favorites: List<Note> = emptyList(),
    val searchResults: List<Note> = emptyList(),
    val searchQuery: String = "",
    val showFavorites: Boolean = false

)