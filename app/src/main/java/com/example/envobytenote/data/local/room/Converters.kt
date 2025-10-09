package com.example.envobytenote.data.local.room

import androidx.room.TypeConverter
import java.util.Date

//This class converts between Date and Long for Room database storage.

class Converters{
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
