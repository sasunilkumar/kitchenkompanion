package com.example.phase12

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.ActivityMainBinding
import com.example.phase12.databinding.InventoryListBinding

class InventoryList : BaseLayout() {
    private lateinit var binding: ViewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inventory_list)

        binding = InventoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))


    }
}