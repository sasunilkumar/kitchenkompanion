package com.example.phase12


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import com.example.phase12.databinding.ActivityMainBinding
import android.view.MenuItem
import android.widget.Toast
import android.view.View
import android.widget.PopupMenu
import androidx.viewbinding.ViewBinding


open class BaseLayout : AppCompatActivity() {
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
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                println("home")
                true
            }
            R.id.GroceryList -> {
                val intent = Intent(this, GroceryList::class.java)
                startActivity(intent)
                println("GroceryList")

                true
            }
            R.id.InventoryList -> {
                val intent = Intent(this, InventoryList::class.java)
                startActivity(intent)
                println("InventoryList")

                true
            }
            R.id.Recipes -> {
                val intent = Intent(this, Recipes::class.java)
                startActivity(intent)
                println("Recipes")

                true
            }
            R.id.Expenses -> {
                val intent = Intent(this, Expenses::class.java)
                startActivity(intent)
                println("Expenses")

                true
            }
            R.id.MealPrep -> {
                val intent = Intent(this, MealPrep::class.java)
                startActivity(intent)
                println("MealPrep")

                true
            }
            R.id.Login -> {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                println("Login")

                true
            }
            R.id.Profile -> {
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
                println("Profile")

                true
            }
            R.id.Settings -> {
                val intent = Intent(this, Settings::class.java)
                startActivity(intent)
                println("Settings")

                true
            }
            R.id.SignUp -> {
                val intent = Intent(this, SignUp::class.java)
                startActivity(intent)
                println("SignUp")

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
