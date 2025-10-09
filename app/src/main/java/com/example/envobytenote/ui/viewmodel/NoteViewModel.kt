package com.example.envobytenote.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envobytenote.data.local.room.Note
import com.example.envobytenote.data.local.repo.NoteRepository
import com.example.envobytenote.ui.screens.edit.EditNoteUiState
import com.example.envobytenote.ui.screens.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

/* Developers note
 * Using UI state data classes instead of MutableState keeps all screen states in one immutable object,
 * ensuring a single source of truth, easier maintenance, and better Compose reactivity.
 */

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadAllNotes()
        loadFavoriteNotes()
    }

    // Load all notes
    fun loadAllNotes() {
        viewModelScope.launch {
            repository.getAllNotes().collect { notes ->
                _uiState.update { it.copy(notes = notes) }
            }
        }
    }

    // Load favorite notes
    fun loadFavoriteNotes() {
        viewModelScope.launch {
            repository.getFavoriteNotes().collect { favorites ->
                _uiState.update { it.copy(favorites = favorites) }
            }
        }
    }

    // Search notes
    fun searchNotes(query: String) {
        viewModelScope.launch {
            repository.searchNotes(query).collect { results ->
                _uiState.update { it.copy(searchQuery = query, searchResults = results) }
            }
        }
    }

    // Toggle between all/favorite notes
    fun toggleShowFavorites() {
        _uiState.update { it.copy(showFavorites = !it.showFavorites) }
    }

    // Update favorite
    fun updateFavorite(id: Long, isFav: Boolean) {
        viewModelScope.launch {
            repository.updateFavorite(id, isFav)
            loadFavoriteNotes()
        }
    }

    // Insert / Update / Delete note (unchanged)
    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
        loadAllNotes()
    }




    private val _editNoteUiState = MutableStateFlow(EditNoteUiState())
    val editNoteUiState: StateFlow<EditNoteUiState> = _editNoteUiState.asStateFlow()

    fun initNote(noteId: Long) {
        viewModelScope.launch {
            if (noteId != -1L) {
                repository.getNoteById(noteId)?.let { note ->
                    _editNoteUiState.value = _editNoteUiState.value.copy(
                        title = note.title,
                        description = note.description,
                        currentNote = note
                    )
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _editNoteUiState.value = _editNoteUiState.value.copy(title = newTitle)
    }

    fun onDescriptionChange(newDesc: String) {
        _editNoteUiState.value = _editNoteUiState.value.copy(description = newDesc)
    }

    fun saveNote(noteId: Long, onSaved: () -> Unit) {
        viewModelScope.launch {
            val ui = _editNoteUiState.value
            if (ui.title.isNotBlank() || ui.description.isNotBlank()) {
                val note = ui.currentNote?.copy(
                    title = ui.title,
                    description = ui.description,
                    date = Date()
                ) ?: Note(
                    title = ui.title,
                    description = ui.description,
                    date = Date(),
                    isFavorite = false
                )

                if (noteId == -1L) repository.insertNote(note)
                else repository.updateNote(note)
            }
            onSaved()
        }
    }

    fun deleteCurrentNote(onBack: () -> Unit) {
        viewModelScope.launch {
            _editNoteUiState.value.currentNote?.let {
                repository.deleteNote(it)
            }
            onBack()
        }
    }


}
