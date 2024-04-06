package com.example.phase12.ui.theme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.phase12.GroceryList
import com.example.phase12.Home
import com.example.phase12.InventoryList
import com.example.phase12.MealPrep
import com.example.phase12.Profile
import com.example.phase12.R
import com.example.phase12.Recipes
import com.google.android.material.bottomappbar.BottomAppBar

open class AppBar: AppCompatActivity() {
    private lateinit var appBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    fun setupBar(){
        appBar = findViewById(R.id.bottomAppBar)
        appBar.setNavigationOnClickListener {
        }
        appBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bot_Recipes -> {
                    Log.d("ACTION MENU", "bot_Recipes")
                    startActivity(Intent(this, Recipes::class.java))
                    true
                }
                R.id.bot_GroceryList -> {
                    Log.d("ACTION MENU", "bot_GroceryList")
                    startActivity(Intent(this, GroceryList::class.java))
                    true
                }
                R.id.bot_InventoryList -> {
                    Log.d("ACTION MENU", "bot_InventoryList")
                    startActivity(Intent(this, InventoryList::class.java))
                    true
                }
                R.id.bot_Profile -> {
                    Log.d("ACTION MENU", "bot_Profile")
                    startActivity(Intent(this, Profile::class.java))
                    true
                }
                R.id.bot_MealPrep -> {
                    Log.d("ACTION MENU", "bot_MealPrep")
                    startActivity(Intent(this, MealPrep::class.java))
                    true
                }
                R.id.bot_Home -> {
                    Log.d("ACTION MENU", "bot_Home")
                    startActivity(Intent(this, Home::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
