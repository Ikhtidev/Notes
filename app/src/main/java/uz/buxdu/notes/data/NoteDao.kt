package uz.buxdu.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.buxdu.notes.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note:Note)

    @Delete
    suspend fun deleteNote(note:Note)

    @Query("SELECT * from note_table")
    fun readAllData() : LiveData<List<Note>>
}