package com.example.phase12

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.GroceryListBinding


class GroceryList : toolbar() {
    private lateinit var binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grocery_list)

        binding = GroceryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}