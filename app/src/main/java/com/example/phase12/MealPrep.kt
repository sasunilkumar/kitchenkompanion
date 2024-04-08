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
import androidx.appcompat.app.AlertDialog;
import com.example.phase12.databinding.MealPrepBinding;
import com.example.phase12.ui.theme.AppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

class MealPrep : AppBar() {
    private lateinit var binding: MealPrepBinding;
    lateinit var dateTV: TextView;
    private var recipeArray: MutableList<View> = mutableListOf();
//    lateinit var calendarView: CalendarView;
    lateinit var addButton: FloatingActionButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = MealPrepBinding.inflate(layoutInflater);
        setContentView(binding.root);
        addButton = findViewById(R.id.fab_meal_prep_list)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        dateTV = binding.textView;
//        calendarView = binding.calendarView;
//        addButton = binding.addMealButton;

//        calendarView.setOnDateChangeListener(
//            OnDateChangeListener { view, year, month, dayOfMonth ->
//                val date = dayOfMonth.toString() + "-" + (month + 1) + "-" + year;
//                dateTV.setText(date);
//            }
//        );

        addButton.setOnClickListener {
            showAddMealDialog();
        }
    }

    private fun showAddMealDialog() {
        val addMealLayout = LayoutInflater.from(this).inflate(R.layout.add_meal_dialog, null);
        val mealNameEditText = addMealLayout.findViewById<EditText>(R.id.meal_name_edittext);
        val caloriesEditText = addMealLayout.findViewById<EditText>(R.id.calories_edittext);
        val sugarEditText = addMealLayout.findViewById<EditText>(R.id.sugar_edittext);

        AlertDialog.Builder(this)
            .setView(addMealLayout)
            .setTitle("Add Meal Entry")
            .setPositiveButton("Add") { dialog, id ->
                val mealName = mealNameEditText.text.toString();
                val calories = caloriesEditText.text.toString();
                val sugar = sugarEditText.text.toString();
                Log.d("MealPrep", "Meal added: $mealName, Calories: $calories, Sugar: $sugar");
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show();
    }
}
