package com.example.phase12


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem



open class toolbar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.ham_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.Home -> {
                startActivity(Intent(this, Home::class.java))
                true
            }
            R.id.GroceryList -> {
                startActivity(Intent(this, GroceryList::class.java))
                true
            }
            R.id.InventoryList -> {
                startActivity(Intent(this, InventoryList::class.java))
                true
            }
            R.id.Recipes -> {
                startActivity(Intent(this, Recipes::class.java))
                true
            }
            R.id.Expenses -> {
                startActivity(Intent(this, Expenses::class.java))
                true
            }
            R.id.MealPrep -> {
                startActivity(Intent(this, MealPrep::class.java))
                true
            }
            R.id.Login -> {
                startActivity(Intent(this, Login::class.java))
                true
            }
            R.id.Profile -> {
                startActivity(Intent(this, Profile::class.java))
                true
            }
            R.id.Settings -> {
                startActivity(Intent(this, Settings::class.java))
                true
            }
            R.id.SignUp -> {
                startActivity(Intent(this, SignUp::class.java))
                true
            }
            else -> false
        }
    }
}
