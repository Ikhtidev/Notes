package uz.buxdu.notes.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import uz.buxdu.notes.R
import uz.buxdu.notes.databinding.ActivityEditNoteBinding
import uz.buxdu.notes.model.Note
import uz.buxdu.notes.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        val intent = intent

        val noteId = intent.getIntExtra(getString(R.string.id), 0)
        val noteTitleOld = intent.getStringExtra(getString(R.string.title))!!
        val noteTextOld = intent.getStringExtra(getString(R.string.text))!!

        binding.title.setText(noteTitleOld)
        binding.text.setText(noteTextOld)

        binding.btnSave.setOnClickListener {
            val updatedNoteTitle = binding.title.text.toString()
            val updatedNoteText = binding.text.text.toString()
            if (noteTitleOld != updatedNoteTitle || noteTextOld != updatedNoteText) {
                if (isNotEmptyFields()) {
                    updateDataInDatabase(noteId, updatedNoteTitle, updatedNoteText)
                } else Toast.makeText(
                    application,
                    getString(R.string.exception_empty_field_save),
                    Toast.LENGTH_SHORT
                ).show()
            } else Toast.makeText(
                application,
                getString(R.string.note_unchanged),
                Toast.LENGTH_SHORT
            ).show()

        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun updateDataInDatabase(noteId: Int, updatedNoteTitle: String, updatedNoteText: String) {

        val updatedAt = SimpleDateFormat(getString(R.string.date_format), Locale.getDefault()).format(Date())
        val updatedNote = Note(noteId, updatedNoteTitle, updatedNoteText, updatedAt)

        mNoteViewModel.updateNote(updatedNote)
        Toast.makeText(application, getString(R.string.note_succesfully_changed), Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun isNotEmptyFields(): Boolean {
        return binding.title.text.trim().isNotEmpty() && binding.text.text.trim().isNotEmpty()
    }

}