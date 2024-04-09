package com.example.phase12;

import android.os.Bundle;
import android.util.Log
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView
import com.example.phase12.databinding.MealPrepBinding;
import com.example.phase12.ui.theme.AppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

class MealPrep : AppBar() {
    private lateinit var binding: MealPrepBinding;
    private lateinit var monday_meals: LinearLayout
    private lateinit var tuesday_meals: LinearLayout
    private lateinit var wednesday_meals: LinearLayout
    private lateinit var thursday_meals: LinearLayout
    private lateinit var friday_meals: LinearLayout
    private lateinit var saturday_meals: LinearLayout
    private lateinit var sunday_meals: LinearLayout
    private lateinit var monday: CardView
    private lateinit var tuesday: CardView
    private lateinit var wednesday: CardView
    private lateinit var thursday: CardView
    private lateinit var friday: CardView
    private lateinit var saturday: CardView
    private lateinit var sunday: CardView
    private lateinit var arrowDown: ImageView
    private lateinit var arrowUp: ImageView
    private var recipeArray: MutableList<View> = mutableListOf();
    lateinit var addButton: FloatingActionButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MealPrepBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBar()

        monday_meals = findViewById(R.id.monday_layouts_vertical)
        tuesday_meals = findViewById(R.id.tuesday_layouts_vertical)
        wednesday_meals = findViewById(R.id.wednesday_layouts_vertical)
        thursday_meals = findViewById(R.id.thursday_layouts_vertical)
        friday_meals = findViewById(R.id.friday_layouts_vertical)
        saturday_meals = findViewById(R.id.saturday_layouts_vertical)
        sunday_meals = findViewById(R.id.sunday_layouts_vertical)

        monday = findViewById(R.id.monday_card)
        tuesday = findViewById(R.id.tuesday_card)
        wednesday = findViewById(R.id.wednesday_card)
        thursday = findViewById(R.id.thursday_card)
        friday = findViewById(R.id.friday_card)
        saturday = findViewById(R.id.saturday_card)
        sunday = findViewById(R.id.sunday_card)



        addButton = findViewById(R.id.add_button)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        addButton.setOnClickListener {
            showAddMealDialog()
        }
    }

    private fun showAddMealDialog() {
        val addMealLayout = LayoutInflater.from(this).inflate(R.layout.add_meal_dialog, null)
        val mealNameEditText = addMealLayout.findViewById<EditText>(R.id.meal_name_edittext)
        val caloriesEditText = addMealLayout.findViewById<EditText>(R.id.calories_edittext)
        val sugarEditText = addMealLayout.findViewById<EditText>(R.id.sugar_edittext)

        AlertDialog.Builder(this)
            .setView(addMealLayout)
            .setTitle("Add Meal Entry")
            .setPositiveButton("Add") { dialog, id ->
                val mealName = mealNameEditText.text.toString()
                val calories = caloriesEditText.text.toString()
                val sugar = sugarEditText.text.toString()
                Log.d("MealPrep", "Meal added: $mealName, Calories: $calories, Sugar: $sugar")
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}
