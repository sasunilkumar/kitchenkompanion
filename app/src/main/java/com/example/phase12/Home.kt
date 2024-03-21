package com.example.phase12


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.phase12.databinding.HomeBinding
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.LoginBinding

class Home : toolbar() {
    private lateinit var binding: ViewBinding

    private lateinit var expenses: Button
    private lateinit var recipes: Button
    private lateinit var inventory: Button
    private lateinit var grocery: Button
    private lateinit var mealPrep: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        expenses = findViewById<Button>(R.id.Expenses)
        recipes = findViewById<Button>(R.id.Recipes)
        inventory = findViewById<Button>(R.id.Inventory)
        grocery = findViewById<Button>(R.id.Grocery)
        mealPrep = findViewById<Button>(R.id.MealPrep)

        expenses.setOnClickListener { startActivity(Intent(this, Expenses::class.java)) }
        recipes.setOnClickListener {startActivity( Intent(this, Recipes::class.java) )}
        inventory.setOnClickListener {startActivity( Intent(this, InventoryList::class.java)) }
        grocery.setOnClickListener { startActivity(Intent(this, GroceryList::class.java)) }
        mealPrep.setOnClickListener {startActivity(Intent(this, MealPrep::class.java)) }


    }

}
