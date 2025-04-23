package com.example.recycler_view;

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextDetail: EditText
    private lateinit var buttonAdd: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private val items = ArrayList<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextDetail = findViewById(R.id.editTextDetail)
        buttonAdd = findViewById(R.id.buttonAdd)
        recyclerView = findViewById(R.id.recyclerView)

        adapter = ItemAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val detail = editTextDetail.text.toString().trim()
            if (name.isNotEmpty() && detail.isNotEmpty()) {
                val newItem = Item(name, detail)
                items.add(newItem)
                adapter.notifyItemInserted(items.size - 1)
                editTextName.text.clear()
                editTextDetail.text.clear()
                recyclerView.scrollToPosition(items.size - 1)
            }
        }
    }
}
