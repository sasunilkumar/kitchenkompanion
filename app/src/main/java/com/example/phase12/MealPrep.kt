package com.example.phase12;

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle;
import android.os.DeadObjectException
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter
import android.widget.EditText;
import android.widget.Spinner
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.setPadding
import com.example.phase12.databinding.MealPrepBinding;
import com.example.phase12.ui.theme.AppBar;
import org.json.JSONArray
import java.io.BufferedReader
import java.io.FileNotFoundException


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


    private var weekArray: MutableList<View> = mutableListOf();
    private var typeArray: MutableList<View> = mutableListOf();


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

        val addB = findViewById<View>(R.id.add_button)
        addB.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = LayoutInflater.from(this)
            val view = inflater.inflate(R.layout.add_meal, null)
            builder.setView(view)
            builder.setMessage("Plan your weekly meals.")
            builder.setTitle("Add Meal")

            builder.setPositiveButton("Add") { dialog, _ ->
                var recipeName = view.findViewById<EditText>(R.id.recipe)
                try {
                    add_Item(
                        recipeName.text.toString(),
                        weekArray[week_spinner.selectedItemPosition],
                        typeArray[meal_spinner.selectedItemPosition])

                } catch (e: NumberFormatException) {
                    Log.d(
                        "READ DIALOG ERROR",
                        "error when trying to read AlertDialog: NumberFormatException"
                    )
                } catch (e: DeadObjectException) {
                    Log.d(
                        "READ DIALOG ERROR",
                        "error when trying to read AlertDialog: DeadObjectException"
                    )
                } catch (e: NullPointerException) {
                    Log.d(
                        "READ DIALOG ERROR",
                        "error when trying to read AlertDialog: NullPointerException"
                    )
                }
//                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            builder.setCancelable(true)

            // Week Spinner
            week_spinner = view.findViewById(R.id.week_spinner)
            week_Items = arrayListOf<String>(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
            )
            week_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, week_Items)
            week_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            week_spinner.adapter = week_adapter

            // Meal Type Spinner
            meal_spinner = view.findViewById(R.id.meal_spinner)
            meal_Items = arrayListOf<String>("Breakfast", "Lunch", "Dinner")
            meal_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, meal_Items)
            meal_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            meal_spinner.adapter = meal_adapter

            builder.create().show()

        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val dataList = readJson()
        if (dataList != null) {
            Log.d("READ", dataList[1].toString())
            parse(dataList)
        } else {
            Log.d("READ Failed", "Failed to read or parse data from JSON file.")
        }
    }

    //READING IN JSON FILE
    private fun readJson(): JSONArray? {
        try {
            return JSONArray(
                // JSON reading stolen from this https://www.youtube.com/watch?v=B9jrhLyRwBs
                applicationContext.resources.openRawResource(R.raw.inventory_ex).bufferedReader()
                    .use<BufferedReader, String> { it.readText() })
        } catch (e: FileNotFoundException) {
            Log.d("Error", "File not found")
        }
        return null
    }


    // iterate over each JSON object
    private fun parse(item: JSONArray) {
        for (i in 0 until item.length()) {
            var curItem = item.getJSONObject(i)
            var name = curItem.getString("name")
            var curList = curItem.getJSONArray("items")
            var table = createList(name, curList)
        }
    }


    private fun populateList(items: JSONArray?, view: TableLayout) {
        if (items != null) {
            var tableView = view
            for (i in 0 until items.length()){
                var row = TableRow(this).apply {
                    layoutParams = TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                var curr = items.getJSONObject(i)


                var name = TextView(this).apply {
                    text = curr.getString("name")
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.4f
                    )
                    gravity = Gravity.CENTER
                    textSize = 24f
                    typeface = resources.getFont(R.font.hammersmith_one)
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                    setPadding(8, 8, 8, 8)
                }
                var quant = EditText(this).apply {
                    hint = curr.getString("quantity")
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.4f
                    )
                    gravity = Gravity.CENTER
                    textSize = 24f
                    typeface = resources.getFont(R.font.hammersmith_one)
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                    setPadding(8, 8, 8, 8)
                }

                //MAKING COUNT +/-
                quant.setText(curr.getString("quantity"))
                quant.inputType = InputType.TYPE_CLASS_NUMBER

                var owner = TextView(this).apply {
                    text = curr.getString("owner")
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.4f
                    )
                    gravity = Gravity.CENTER
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    textSize = 24f
                    typeface = resources.getFont(R.font.hammersmith_one)
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                    setPadding(8, 8, 8, 8)
                }

                row.addView(name)
                row.addView(owner)
                row.addView(quant)
                tableView.addView(row)
            }
        }
    }




    private fun add_Item(recipe: String = "test",
                         week_view: View = weekArray[0],
                         type_view: View = typeArray[0]) {
        val string = "[{\"recipe\":  \"$recipe\"}]"
        val json = JSONArray(string)
        populateList(json, week_view as TableLayout)

    }



}

