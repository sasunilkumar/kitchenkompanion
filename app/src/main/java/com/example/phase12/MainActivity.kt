package com.example.phase12

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent




class MainActivity : AppCompatActivity() {
    private lateinit var home: Button
    private lateinit var groceries: Button
    private lateinit var inventory: Button
    private lateinit var recipe: Button
    private lateinit var expenses: Button
    private lateinit var mealPrep: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        home = findViewById<Button>(R.id.button)
        groceries = findViewById<Button>(R.id.button2)
        inventory = findViewById<Button>(R.id.button3)
        recipe = findViewById<Button>(R.id.button4)
        expenses = findViewById<Button>(R.id.button5)
        mealPrep = findViewById<Button>(R.id.button6)


        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        groceries.setOnClickListener {
            val intent = Intent(this, GroceryList::class.java)
            startActivity(intent)
        }
        inventory.setOnClickListener {
            val intent = Intent(this, InventoryList::class.java)
            startActivity(intent)
        }
        recipe.setOnClickListener {
            val intent = Intent(this, Recipes::class.java)
            startActivity(intent)
        }
        expenses.setOnClickListener {
            val intent = Intent(this, Expenses::class.java)
            startActivity(intent)        }
        mealPrep.setOnClickListener {
            val intent = Intent(this, MealPrep::class.java)
            startActivity(intent)        }
    }

}
