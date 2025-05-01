package com.example.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recycler_view.data.Item

class ItemAdapter(
    private val onDeleteClick: (Item) -> Unit
) : ListAdapter<Item, ItemAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView   = itemView.findViewById(R.id.textViewName)
        private val textViewDetail: TextView = itemView.findViewById(R.id.textViewDetail)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDelete)

        fun bind(item: Item) {
            textViewName.text   = item.name
            textViewDetail.text = item.detail
            deleteButton.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(old: Item, new: Item): Boolean =
                old.id == new.id

            override fun areContentsTheSame(old: Item, new: Item): Boolean =
                old == new
        }
    }
}
