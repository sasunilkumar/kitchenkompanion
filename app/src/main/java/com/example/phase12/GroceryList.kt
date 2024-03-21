package com.example.phase12

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.GroceryListBinding


class GroceryList : toolbar() {
    private lateinit var binding: ViewBinding
    private lateinit var fab: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grocery_list)

        binding = GroceryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fab = findViewById<View>(R.id.fab_grocery_list)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab.setOnClickListener {
            // Respond to FAB click
            true
        }
    }
}