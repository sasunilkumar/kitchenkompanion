package com.example.phase12


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.phase12.databinding.HomeBinding
import androidx.viewbinding.ViewBinding
import com.example.phase12.ui.theme.AppBar
import com.google.android.material.bottomappbar.BottomAppBar

class Home : AppBar() {
    private lateinit var binding: ViewBinding

    private lateinit var recipes: Button
    private lateinit var inventory: Button
    private lateinit var grocery: Button
    private lateinit var mealPrep: Button
    private lateinit var bottomAppBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        setupBar()
        bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        setSupportActionBar(bottomAppBar)

        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipes = findViewById<Button>(R.id.Recipes)
        inventory = findViewById<Button>(R.id.Inventory)
        grocery = findViewById<Button>(R.id.Grocery)
        mealPrep = findViewById<Button>(R.id.MealPrep)

        recipes.setOnClickListener {startActivity( Intent(this, Recipes::class.java) )}
        inventory.setOnClickListener {startActivity( Intent(this, InventoryList::class.java)) }
        grocery.setOnClickListener { startActivity(Intent(this, GroceryList::class.java)) }
        mealPrep.setOnClickListener {startActivity(Intent(this, MealPrep::class.java)) }
    }

}
