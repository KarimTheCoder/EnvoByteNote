package com.example.envobytenote.ui.screens.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.envobytenote.data.local.room.Note
import com.example.envobytenote.ui.viewmodel.NoteViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    noteId: Long,
    noteViewModel: NoteViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by noteViewModel.editNoteUiState.collectAsState()

    LaunchedEffect(noteId) {
        noteViewModel.initNote(noteId)
    }

    Scaffold(
        topBar = {
            EditNoteTopBar(
                noteId = noteId,
                onBack = onBack,
                onDelete = { noteViewModel.deleteCurrentNote(onBack) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    noteViewModel.saveNote(noteId, onBack)
                },
                modifier = Modifier
                    .imePadding()
            ) {
                Icon(Icons.Default.Done, contentDescription = "Save")
            }
        }
    ) { padding ->
        EditNoteContent(
            title = uiState.title,
            description = uiState.description,
            onTitleChange = noteViewModel::onTitleChange,
            onDescriptionChange = noteViewModel::onDescriptionChange,
            modifier = Modifier.padding(padding)
        )
    }
}
