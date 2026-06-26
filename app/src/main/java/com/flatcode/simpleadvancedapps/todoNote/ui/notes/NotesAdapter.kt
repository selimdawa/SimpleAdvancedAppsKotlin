package com.flatcode.simpleadvancedapps.todoNote.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.databinding.ItemNoteBinding
import com.flatcode.simpleadvancedapps.todoNote.data.Notes

class NotesAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Notes>() {
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    class NotesViewHolder(
        private val binding: ItemNoteBinding,
        private val listener: OnItemClickListener,
        private val differ: AsyncListDiffer<Notes>
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(differ.currentList[position])
                }
            }

            binding.deleteNoteButton.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteNoteClick(differ.currentList[position])
                }
            }
        }

        fun bind(note: Notes) {
            binding.titleTextView.text = note.title
            binding.contentTextView.text = note.content
            binding.dateTextView.text = note.createdDateFormatted
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding, listener, differ)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener {
        fun onItemClick(note: Notes)
        fun onDeleteNoteClick(note: Notes)
    }
}