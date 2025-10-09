package com.example.envobytenote.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.datastore.preferences.core.edit
import com.example.envobytenote.data.local.room.Note
import com.example.envobytenote.ui.viewmodel.NoteViewModel
import kotlinx.coroutines.flow.first

@Composable
fun AddDefaultNotesIfFirstLaunch(
    noteViewModel: NoteViewModel,
    context: Context,
    defaultNotes: List<Note>,
    onDone: () -> Unit
) {
    LaunchedEffect(Unit) {
        val prefs = context.dataStore.data.first()
        val alreadyLaunched = prefs[PreferencesKeys.FIRST_LAUNCH_DONE] == true

        if (!alreadyLaunched) {
            defaultNotes.forEach(noteViewModel::insertNote)
            context.dataStore.edit { it[PreferencesKeys.FIRST_LAUNCH_DONE] = true }
        }
        onDone()
    }
}