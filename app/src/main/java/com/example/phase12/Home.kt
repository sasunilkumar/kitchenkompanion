package com.example.phase12


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.phase12.databinding.HomeBinding
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.example.phase12.ui.theme.AppBar

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

        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //expenses = findViewById<Button>(R.id.Expenses)
        recipes = findViewById<Button>(R.id.Recipes)
        inventory = findViewById<Button>(R.id.Inventory)
        grocery = findViewById<Button>(R.id.Grocery)
        mealPrep = findViewById<Button>(R.id.MealPrep)

        //expenses.setOnClickListener { startActivity(Intent(this, Expenses::class.java)) }
        recipes.setOnClickListener {startActivity( Intent(this, Recipes::class.java) )}
        inventory.setOnClickListener {startActivity( Intent(this, InventoryList::class.java)) }
        grocery.setOnClickListener { startActivity(Intent(this, GroceryList::class.java)) }
        mealPrep.setOnClickListener {startActivity(Intent(this, MealPrep::class.java)) }

        val appBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
    }

}
