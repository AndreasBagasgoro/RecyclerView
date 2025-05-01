package com.example.recycler_view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycler_view.data.AppDatabase
import com.example.recycler_view.data.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextDetail: EditText
    private lateinit var buttonAdd: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi UI
        editTextName   = findViewById(R.id.editTextName)
        editTextDetail = findViewById(R.id.editTextDetail)
        buttonAdd      = findViewById(R.id.buttonAdd)
        recyclerView   = findViewById(R.id.recyclerView)

        // Inisialisasi DB
        db = AppDatabase.getInstance(this)

        // Inisialisasi adapter dengan callback delete
        adapter = ItemAdapter { itemToDelete ->
            lifecycleScope.launch(Dispatchers.IO) {
                db.itemDao().delete(itemToDelete)
                // refresh daftar
                loadItemsFromDb()
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Load data awal
        loadItemsFromDb()

        // Tombol Add
        buttonAdd.setOnClickListener {
            val name   = editTextName.text.toString().trim()
            val detail = editTextDetail.text.toString().trim()
            if (name.isNotEmpty() && detail.isNotEmpty()) {
                val newItem = Item(name = name, detail = detail)
                lifecycleScope.launch(Dispatchers.IO) {
                    db.itemDao().insert(newItem)
                    loadItemsFromDb()
                }
                editTextName.text.clear()
                editTextDetail.text.clear()
            }
        }
    }

    private fun loadItemsFromDb() {
        lifecycleScope.launch {
            val list = withContext(Dispatchers.IO) {
                db.itemDao().getAll()
            }
            // Submit ke ListAdapter di main thread
            adapter.submitList(list)
            if (list.isNotEmpty()) {
                recyclerView.scrollToPosition(list.size - 1)
            }
        }
    }
}
