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
    private lateinit var arrowDownM: ImageView
    private lateinit var arrowUpM: ImageView
    private lateinit var arrowDownT: ImageView
    private lateinit var arrowUpT: ImageView
    private lateinit var arrowDownW: ImageView
    private lateinit var arrowUpW: ImageView
    private lateinit var arrowDownTh: ImageView
    private lateinit var arrowUpTh: ImageView
    private lateinit var arrowDownF: ImageView
    private lateinit var arrowUpF: ImageView
    private lateinit var arrowDownSa: ImageView
    private lateinit var arrowUpSa: ImageView
    private lateinit var arrowDownSu: ImageView
    private lateinit var arrowUpSu: ImageView

    private var recipeArray: MutableList<View> = mutableListOf();
    lateinit var addButton: FloatingActionButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MealPrepBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBar()

        monday_meals = findViewById(R.id.monday_layouts_horo)
        tuesday_meals = findViewById(R.id.tuesday_layouts_horo)
        wednesday_meals = findViewById(R.id.wednesday_layouts_horo)
        thursday_meals = findViewById(R.id.thursday_layouts_horo)
        friday_meals = findViewById(R.id.friday_layouts_horo)
        saturday_meals = findViewById(R.id.saturday_layouts_horo)
        sunday_meals = findViewById(R.id.sunday_layouts_horo)

        monday = findViewById(R.id.monday_card)
        tuesday = findViewById(R.id.tuesday_card)
        wednesday = findViewById(R.id.wednesday_card)
        thursday = findViewById(R.id.thursday_card)
        friday = findViewById(R.id.friday_card)
        saturday = findViewById(R.id.saturday_card)
        sunday = findViewById(R.id.sunday_card)

        arrowDownM = findViewById(R.id.arrow_down_m)
        arrowUpM = findViewById(R.id.arrow_up_m)
        arrowDownT = findViewById(R.id.arrow_down_t)
        arrowUpT = findViewById(R.id.arrow_up_t)
        arrowDownW = findViewById(R.id.arrow_down_w)
        arrowUpW = findViewById(R.id.arrow_up_w)
        arrowDownTh = findViewById(R.id.arrow_down_th)
        arrowUpTh = findViewById(R.id.arrow_up_th)
        arrowDownF = findViewById(R.id.arrow_down_f)
        arrowUpF = findViewById(R.id.arrow_up_f)
        arrowDownSa = findViewById(R.id.arrow_down_sat)
        arrowUpSa = findViewById(R.id.arrow_up_sat)
        arrowDownSu = findViewById(R.id.arrow_down_sun)
        arrowUpSu = findViewById(R.id.arrow_up_sun)


        monday.setOnClickListener {
            if (monday_meals.visibility == View.GONE) {
                monday_meals.visibility = View.VISIBLE
            } else {
                monday_meals.visibility = View.GONE
            }
            if (arrowDownM.visibility == View.VISIBLE) {
                arrowDownM.visibility = View.GONE
                arrowUpM.visibility = View.VISIBLE
            } else {
                arrowUpM.visibility = View.GONE
                arrowDownM.visibility = View.VISIBLE
            }
        }



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
