package com.example.envobytenote.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.envobytenote.data.local.room.AppDatabase
import com.example.envobytenote.data.local.room.NoteDao
import com.example.envobytenote.data.local.repo.NoteRepository
import com.example.envobytenote.util.ThemePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "notes_db"
        ).build()
    }

    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }

    @Provides
    @Singleton
    fun provideThemePreference(@ApplicationContext context: Context) = ThemePreference(context)
}