package com.example.phase12

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.ExpensesBinding



class Expenses : toolbar() {
    private lateinit var binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expenses)

        binding = ExpensesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}