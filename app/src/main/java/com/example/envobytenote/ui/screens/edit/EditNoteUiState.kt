package com.example.envobytenote.ui.screens.edit

import com.example.envobytenote.data.local.room.Note

data class EditNoteUiState(
    val title: String = "",
    val description: String = "",
    val currentNote: Note? = null)


