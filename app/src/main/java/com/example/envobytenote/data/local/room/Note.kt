package com.example.envobytenote.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val title: String,
    val description: String,
    val isFavorite: Boolean = false,
    val date: Date = Date(System.currentTimeMillis())
)