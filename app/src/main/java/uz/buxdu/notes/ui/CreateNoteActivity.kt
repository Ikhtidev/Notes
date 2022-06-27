package uz.buxdu.notes.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import uz.buxdu.notes.R
import uz.buxdu.notes.databinding.ActivityCreateNoteBinding
import uz.buxdu.notes.model.Note
import uz.buxdu.notes.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        binding.btnCreate.setOnClickListener {
            if (isCheck()) {
                insertDataToDatabase()
            } else Toast.makeText(
                application,
                getString(R.string.exception_empty_field_create),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnBack.setOnClickListener { finish() }
    }

    private fun isCheck(): Boolean {
        return binding.title.text.isNotEmpty() && binding.text.text.isNotEmpty()
    }

    private fun insertDataToDatabase() {

        val title = binding.title.text.toString()
        val text = binding.text.text.toString()
        val updatedAt = SimpleDateFormat(getString(R.string.date_format), Locale.getDefault()).format(Date())

        val note = Note(0, title, text, updatedAt)

        mNoteViewModel.addNote(note)
        Toast.makeText(this, getString(R.string.note_successfully_create), Toast.LENGTH_LONG).show()
        finish()
    }
}