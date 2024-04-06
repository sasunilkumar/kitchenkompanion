package com.example.phase12

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewbinding.ViewBinding
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.phase12.databinding.MealPrepBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException


class MealPrep : AppCompatActivity() {
    private lateinit var binding: ViewBinding
    lateinit var dateTV: TextView
    private var recipeArray: MutableList<View> = mutableListOf()
    lateinit var calendarView: CalendarView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_prep)

        binding = MealPrepBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Calender View Implementation from GeeksforGeeks

        dateTV = findViewById(R.id.textView)
        calendarView = findViewById(R.id.calendarView)


        calendarView
            .setOnDateChangeListener(

                OnDateChangeListener { view, year, month, dayOfMonth ->
                    val Date = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)
                    dateTV.setText(Date)

                })
    }
}