package com.example.retrofitvidos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitvidos.databinding.ItemTodoBinding
import java.text.ParsePosition

class ToDoApapter : RecyclerView.Adapter<ToDoApapter.ToDoViewHolder>() {


    inner class ToDoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<ToDo>(){
        override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem.id == newItem.id
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var todos: List<ToDo>
        get() = differ.currentList
        set(value) {differ.submitList(value)}


    override fun getItemCount() = todos.size



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.binding.apply {
            val todo = todos[position]
            tvTitles.text = todo.title
            cbDone.isChecked = todo.completed
        }
    }

}