package uz.buxdu.notes.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.buxdu.notes.databinding.NoteItemLayoutBinding
import uz.buxdu.notes.model.Note
import uz.buxdu.notes.ui.MainActivity
import java.util.*

class NoteAdapter(private val activity: MainActivity) :RecyclerView.Adapter<NoteAdapter.ItemHolder>() {

    private var items = emptyList<Note>()

    inner class ItemHolder(val binding: NoteItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(NoteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.title.text = item.title.trim()
        holder.binding.text.text = item.text.trim()
        holder.binding.updatedAtHour.text = item.updatedAt.substring(0,5)
        holder.binding.updatedAtDay.text = item.updatedAt.substring(6)

        holder.itemView.setOnClickListener {
            activity.noteItemClick(item)
        }

        holder.binding.btnDelete.setOnClickListener {
            activity.deleteRequestNote(item)
        }

        val rnd = Random()
        val currentColor = Color.argb(100, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        holder.binding.itemLayout.setBackgroundColor(currentColor)
    }

    override fun getItemCount(): Int = items.count()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(notes: List<Note>) {
        this.items = notes
        notifyDataSetChanged()
    }

}