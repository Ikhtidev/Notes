package uz.buxdu.notes.ui.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.buxdu.notes.R
import uz.buxdu.notes.databinding.BottomSheetLayoutBinding
import uz.buxdu.notes.model.Note
import uz.buxdu.notes.viewModel.NoteViewModel

class BottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding:BottomSheetLayoutBinding
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetLayoutBinding.inflate(layoutInflater, container, false)

        val id =  arguments?.getInt(getString(R.string.id))?: 0
        val title =  arguments?.getString(getString(R.string.title))?: getString(R.string.title)
        val text =  arguments?.getString(getString(R.string.text))?: getString(R.string.text)
        val updatedAt =  arguments?.getString(getString(R.string.updatedAt))?: getString(R.string.updatedAt)

        val item = Note(id, title, text, updatedAt)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnDelete.setOnClickListener {
            mNoteViewModel.deleteNote(item)
            Toast.makeText(context, item.title + " " + getString(R.string.deleted), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        return binding.root
    }
}