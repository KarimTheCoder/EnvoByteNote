package com.example.envobytenote

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.envobytenote.data.local.room.AppDatabase
import com.example.envobytenote.data.local.room.Note
import com.example.envobytenote.data.local.room.NoteDao

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {


    private lateinit var db: AppDatabase
    private lateinit var dao: NoteDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        )
            .allowMainThreadQueries() // Safe for testing
            .build()

        dao = db.noteDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertAndGetNote() = runBlocking {
        val note = Note(title = "Test", description = "Desc")
        val id = dao.insert(note)
        val fetched = dao.getNoteById(id)
        Assert.assertEquals("Test", fetched?.title)
    }

    @Test
    fun insertNote_andGetById_returnsCorrectNote() = runBlocking {
        val note = Note(title = "Test Note", description = "Test description")
        val id = dao.insert(note)

        val fetched = dao.getNoteById(id)

        Assert.assertNotNull(fetched)
        Assert.assertEquals("Test Note", fetched?.title)
        Assert.assertEquals("Test description", fetched?.description)
    }

    @Test
    fun updateNote_updatesDataCorrectly() = runBlocking {
        val note = Note(title = "Old", description = "Old desc")
        val id = dao.insert(note)

        val updatedNote = Note(id = id, title = "New", description = "New desc")
        dao.update(updatedNote)

        val fetched = dao.getNoteById(id)

        Assert.assertEquals("New", fetched?.title)
        Assert.assertEquals("New desc", fetched?.description)
    }

    @Test
    fun deleteNote_removesFromDatabase() = runBlocking {
        val note = Note(title = "Delete me", description = "Soon gone")
        val id = dao.insert(note)

        val inserted = dao.getNoteById(id)
        Assert.assertNotNull(inserted)

        dao.delete(inserted!!)

        val deleted = dao.getNoteById(id)
        Assert.assertNull(deleted)
    }

    @Test
    fun getAllNotes_returnsAllInsertedNotes() = runBlocking {
        dao.insert(Note(title = "Note 1", description = "Desc 1"))
        dao.insert(Note(title = "Note 2", description = "Desc 2"))

        val notes = dao.getAllNotes().first()

        Assert.assertEquals(2, notes.size)
        Assert.assertTrue(notes.any { it.title == "Note 1" })
        Assert.assertTrue(notes.any { it.title == "Note 2" })
    }
}