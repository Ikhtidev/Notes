package uz.buxdu.notes.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import uz.buxdu.notes.R
import uz.buxdu.notes.adapter.NoteAdapter
import uz.buxdu.notes.databinding.ActivityMainBinding
import uz.buxdu.notes.model.Note
import uz.buxdu.notes.ui.util.BottomSheetDialog
import uz.buxdu.notes.viewModel.NoteViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mNoteViewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter
    private var noteList: List<Note> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NoteAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        mNoteViewModel.readAllData.observe(this) { notes ->
            if (notes.isEmpty()) {
                binding.recyclerView.visibility = View.INVISIBLE
                binding.emptyList.visibility = View.VISIBLE
            } else {
                adapter.setData(notes)
                noteList = notes
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyList.visibility = View.INVISIBLE
            }
            if (notes.count() > 0) {
                binding.searchCardView.visibility = View.VISIBLE
            } else {
                binding.searchCardView.visibility = View.GONE
            }

        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, CreateNoteActivity::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

    }

    private fun filterList(text: String?) {
        val filteredList: ArrayList<Note> = ArrayList()
        for (note: Note in noteList) {
            if (note.title.lowercase(Locale.getDefault())
                    .contains(text!!.lowercase(Locale.getDefault())) ||
                note.text.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                filteredList.add(note)
            }
        }
        adapter.setData(filteredList)
    }

    fun noteItemClick(item: Note) {
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra(getString(R.string.id), item.id)
        intent.putExtra(getString(R.string.title), item.title)
        intent.putExtra(getString(R.string.text), item.text)
        startActivity(intent)
    }

    fun deleteRequestNote(item: Note) {

        val bottomSheet = BottomSheetDialog()

        val bundle = Bundle()
        bundle.putInt(getString(R.string.id), item.id)
        bundle.putString(getString(R.string.title), item.title)
        bundle.putString(getString(R.string.text), item.text)
        bundle.putString(getString(R.string.updatedAt), item.updatedAt)
        bottomSheet.arguments = bundle

        bottomSheet.show(supportFragmentManager, getString(R.string.tag))
    }
}