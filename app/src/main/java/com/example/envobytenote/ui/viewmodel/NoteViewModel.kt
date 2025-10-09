package com.example.envobytenote.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envobytenote.data.local.room.Note
import com.example.envobytenote.data.local.room.NoteRepository
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

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notesState = MutableStateFlow<List<Note>>(emptyList())
    val notesState: StateFlow<List<Note>> = _notesState

    private val _favoriteNotesState = MutableStateFlow<List<Note>>(emptyList())
    val favoriteNotesState: StateFlow<List<Note>> = _favoriteNotesState

    private val _searchResultsState = MutableStateFlow<List<Note>>(emptyList())
    val searchResultsState: StateFlow<List<Note>> = _searchResultsState


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

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
        loadAllNotes()
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
        loadAllNotes()
    }





//
//    // Insert note
//    fun insertNote(note: Note) {
//        viewModelScope.launch {
//            repository.insertNote(note)
//            loadAllNotes() // refresh list
//        }
//    }
//
//    // Update note
//    fun updateNote(note: Note) {
//        viewModelScope.launch {
//            repository.updateNote(note)
//            loadAllNotes()
//        }
//    }
//
//    // Update favorite
//    fun updateFavorite(id: Long, isFav: Boolean) {
//        viewModelScope.launch {
//            repository.updateFavorite(id, isFav)
//            loadFavoriteNotes()
//        }
//    }
//
//    // Delete note
//    fun deleteNote(note: Note) {
//        viewModelScope.launch {
//            repository.deleteNote(note)
//            loadAllNotes()
//        }
//    }
//
//    // Load all notes
//    fun loadAllNotes() {
//        viewModelScope.launch {
//            repository.getAllNotes().collect { notes ->
//                _notesState.value = notes
//            }
//        }
//    }
//
//    // Load favorite notes
//    fun loadFavoriteNotes() {
//        viewModelScope.launch {
//            repository.getFavoriteNotes().collect { favorites ->
//                _favoriteNotesState.value = favorites
//            }
//        }
//    }


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

    // Get note by id
    suspend fun getNoteById(noteId: Long): Note? {
        return repository.getNoteById(noteId)
    }

    // Search notes
//    fun searchNotes(query: String) {
//        viewModelScope.launch {
//            repository.searchNotes(query).collect { results ->
//                _searchResultsState.value = results
//            }
//        }
//    }


    private val _currentNote = MutableStateFlow<Note?>(null)
    val currentNote: StateFlow<Note?> = _currentNote

    fun loadNoteById(noteId: Long) {
        if (noteId == -1L) {
            _currentNote.value = null // new note
            return
        }

        viewModelScope.launch {
            _currentNote.value = repository.getNoteById(noteId)
        }
    }





}
