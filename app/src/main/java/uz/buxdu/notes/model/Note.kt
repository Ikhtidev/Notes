package uz.buxdu.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title:String,
    val text:String,
    val updatedAt:String
)
