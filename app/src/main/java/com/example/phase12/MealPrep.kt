package com.example.phase12;

import android.os.Bundle;
import android.util.Log
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter
import android.widget.EditText;
import android.widget.Spinner
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView
import com.example.phase12.databinding.MealPrepBinding;
import com.example.phase12.ui.theme.AppBar;
import android.widget.AdapterView
import android.widget.Toast


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

    private lateinit var week_spinner: Spinner
    private lateinit var week_Items: ArrayList<String>
    private lateinit var week_adapter: ArrayAdapter<String>

    private lateinit var meal_spinner: Spinner
    private lateinit var meal_Items: ArrayList<String>
    private lateinit var meal_adapter: ArrayAdapter<String>


    private lateinit var addB: View

    private var recipeArray: MutableList<View> = mutableListOf();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MealPrepBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBar()



        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.add_meal, null)
        builder.setView(view)
        builder.setMessage("Plan your weekly meals.")
        builder.setTitle("Add Meal")
        builder.setCancelable(true)
        addB= findViewById<View>(R.id.add_button)

        // Week Spinner
        week_spinner = view.findViewById(R.id.week_spinner)
        week_Items = arrayListOf<String>("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        week_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, week_Items)
        week_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        week_spinner.adapter = week_adapter

        // Meal Type Spinner
        meal_spinner = view.findViewById(R.id.meal_spinner)
        meal_Items = arrayListOf<String>("Breakfast", "Lunch", "Dinner")
        meal_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, meal_Items)
        meal_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        meal_spinner.adapter = meal_adapter



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

        tuesday.setOnClickListener {
            if (tuesday_meals.visibility == View.GONE) {
                tuesday_meals.visibility = View.VISIBLE
            } else {
                tuesday_meals.visibility = View.GONE
            }
            if (arrowDownT.visibility == View.VISIBLE) {
                arrowDownT.visibility = View.GONE
                arrowUpT.visibility = View.VISIBLE
            } else {
                arrowUpT.visibility = View.GONE
                arrowDownT.visibility = View.VISIBLE
            }
        }

        wednesday.setOnClickListener {
            if (wednesday_meals.visibility == View.GONE) {
                wednesday_meals.visibility = View.VISIBLE
            } else {
                wednesday_meals.visibility = View.GONE
            }
            if (arrowDownW.visibility == View.VISIBLE) {
                arrowDownW.visibility = View.GONE
                arrowUpW.visibility = View.VISIBLE
            } else {
                arrowUpW.visibility = View.GONE
                arrowDownW.visibility = View.VISIBLE
            }
        }

        thursday.setOnClickListener {
            if (thursday_meals.visibility == View.GONE) {
                thursday_meals.visibility = View.VISIBLE
            } else {
                thursday_meals.visibility = View.GONE
            }
            if (arrowDownTh.visibility == View.VISIBLE) {
                arrowDownTh.visibility = View.GONE
                arrowUpTh.visibility = View.VISIBLE
            } else {
                arrowUpTh.visibility = View.GONE
                arrowDownTh.visibility = View.VISIBLE
            }
        }

        friday.setOnClickListener {
            if (friday_meals.visibility == View.GONE) {
                friday_meals.visibility = View.VISIBLE
            } else {
                friday_meals.visibility = View.GONE
            }
            if (arrowDownF.visibility == View.VISIBLE) {
                arrowDownF.visibility = View.GONE
                arrowUpF.visibility = View.VISIBLE
            } else {
                arrowUpF.visibility = View.GONE
                arrowDownF.visibility = View.VISIBLE
            }
        }

        saturday.setOnClickListener {
            if (saturday_meals.visibility == View.GONE) {
                saturday_meals.visibility = View.VISIBLE
            } else {
                saturday_meals.visibility = View.GONE
            }
            if (arrowDownSa.visibility == View.VISIBLE) {
                arrowDownSa.visibility = View.GONE
                arrowUpSa.visibility = View.VISIBLE
            } else {
                arrowUpSa.visibility = View.GONE
                arrowDownSa.visibility = View.VISIBLE
            }
        }

        sunday.setOnClickListener {
            if (sunday_meals.visibility == View.GONE) {
                sunday_meals.visibility = View.VISIBLE
            } else {
                sunday_meals.visibility = View.GONE
            }
            if (arrowDownSu.visibility == View.VISIBLE) {
                arrowDownSu.visibility = View.GONE
                arrowUpSu.visibility = View.VISIBLE
            } else {
                arrowUpSu.visibility = View.GONE
                arrowDownSu.visibility = View.VISIBLE
            }
        }





        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        addB.setOnClickListener {
            showAddMealDialog()

        }
    }

    private fun showAddMealDialog() {
        val addMealLayout = LayoutInflater.from(this).inflate(R.layout.add_meal, null)
        val mealNameEditText = addMealLayout.findViewById<EditText>(R.id.meal_name_edittext)

        AlertDialog.Builder(this)
            .setView(addMealLayout)
            .setPositiveButton("Add") { dialog, id ->
                val mealName = mealNameEditText.text.toString()

                Log.d("MealPrep", "Meal added: $mealName")
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

}

