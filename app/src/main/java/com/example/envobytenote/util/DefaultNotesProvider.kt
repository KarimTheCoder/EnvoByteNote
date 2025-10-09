package com.example.envobytenote.util

import android.content.Context
import com.example.envobytenote.R
import com.example.envobytenote.data.local.room.Note


fun getDefaultNotes(context: Context): List<Note> {
    return listOf(
        Note(
            title = context.getString(R.string.note_welcome_title),
            description = context.getString(R.string.note_welcome_desc),
            isFavorite = true
        ),
        Note(
            title = context.getString(R.string.note_tips_title),
            description = context.getString(R.string.note_tips_desc)
        ),
        Note(
            title = context.getString(R.string.about_envobyte_title),
            description = context.getString(R.string.about_envobyte_desc)
        )
    )
}