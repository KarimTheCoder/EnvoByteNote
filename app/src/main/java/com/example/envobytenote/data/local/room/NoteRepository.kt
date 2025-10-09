package com.example.envobytenote.data.local.room

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun insertNote(note: Note) {
        noteDao.insert(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.update(note)
    }

    suspend fun updateFavorite(noteId: Long, isFavorite: Boolean) {
        noteDao.updateFavorite(noteId, isFavorite)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.delete(note)
    }

    fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    fun getFavoriteNotes(): Flow<List<Note>> {
        return noteDao.getFavoriteNotes()
    }

    suspend fun getNoteById(noteId: Long): Note? = noteDao.getNoteById(noteId)

    fun searchNotes(query: String): Flow<List<Note>> {
        return noteDao.searchNotes("%$query%") // wrap query with % for LIKE
    }
}