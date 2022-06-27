package uz.buxdu.notes.repository

import androidx.lifecycle.LiveData
import uz.buxdu.notes.data.NoteDao
import uz.buxdu.notes.model.Note

class NoteRepository(private val noteDao: NoteDao) {
    val readAllData: LiveData<List<Note>> = noteDao.readAllData()

    suspend fun addNote(note:Note){
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }
}