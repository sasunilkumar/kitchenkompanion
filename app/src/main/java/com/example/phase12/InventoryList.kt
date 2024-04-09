package com.example.phase12

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.DeadObjectException
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.setPadding
import com.example.phase12.databinding.InventoryListBinding
import org.json.JSONArray
import java.io.BufferedReader
import java.io.FileNotFoundException

class InventoryList : AppBar() {

    //BASE VAR
    private var grocArray: MutableList<View> = mutableListOf()
    private lateinit var spinner: Spinner
    private lateinit var spinnerItems: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var addB: View
    private lateinit var binding: InventoryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inventory_list)

        binding = InventoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBar()


        //PLUS ADD BUTTON VARs
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.add_inventory, null)
        builder.setView(view)
        builder.setMessage("What would you like to add?")
        builder.setTitle("Add Item")
        builder.setCancelable(true)
        var itemName = view.findViewById<EditText>(R.id.item_name)
        var itemQuant = view.findViewById<EditText>(R.id.quantity)
        addB= findViewById<View>(R.id.add_button)

        // Spinner
        spinner = view.findViewById(R.id.add_item_spinner)
        spinnerItems = arrayListOf<String>()
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        // ADD CANCEL PART OF PLUS POPUP BUTTON AND DATA VALIDATION
        //  Wont take numerical values greater than 11 digits in either column, We will
        //  see overflow if the digits exceed 7
        builder.setPositiveButton("Add") { dialog, _ ->
            try {
                addItem(
                    itemName.text.toString(),
                    itemQuant.text.toString().toInt(),
                    "me",
                    grocArray[spinner.selectedItemPosition]
                )
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
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = builder.create()

        //PLUS BUTTON LISTENER
        addB.setOnClickListener {
            itemName.setText("")
            itemQuant.setText("")
            alertDialog.show()
            true
        }

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


    // Creates the List container, table inside, and spacer
    private fun createList(title: String, items: JSONArray): LinearLayout {
        val linLay = findViewById<LinearLayout>(R.id.invent_body)
        val listId = View.generateViewId()
        val spacerID = View.generateViewId()
        val tableID = View.generateViewId()
        val titleID = View.generateViewId()

        val spacer = View(this).apply {
            id = spacerID
            layoutParams = LinearLayout.LayoutParams(
                0, 35)
        }
        val container = LinearLayout(this).apply {
            id = listId  // Set the ID here
            layoutParams = LinearLayout.LayoutParams(
                700,  // width in pixels
                LinearLayout.LayoutParams.WRAP_CONTENT   // height in pixels
            )
            gravity = Gravity.CENTER_HORIZONTAL
            orientation = LinearLayout.VERTICAL

        }
        var draw: Drawable? = ContextCompat.getDrawable(this, R.drawable.view_container)
        if (draw != null) {
            draw = DrawableCompat.wrap(draw)
            DrawableCompat.setTint(draw, Color.parseColor("#FFADAD"))
        }
        container.background = draw
        container.setPadding(40)


        //REFRIGERATOR AND PANTRY
        val titleText = TextView(this).apply {
            id = titleID
            layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )
            text = title
            textSize = 33f
            gravity = TextView.TEXT_ALIGNMENT_VIEW_START
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            typeface = resources.getFont(R.font.hammersmith_one)

            setTypeface(Typeface.DEFAULT_BOLD)
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }
        val table = TableLayout(this).apply {
            id = tableID
            layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )
        }
        val row = TableRow(this).apply {
            layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
            )
        }

        //TITLES OF COLS AKA NAMES OWNERS COUNT
        val cols = arrayOf("Name", "Owner", "Count +/-")
        val weights = arrayOf(1.5f, 1f, 1.5f)
        var i = 0
        for (title in cols) {
            val col = TextView(this).apply {
                layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    weights[i]
                )
                text = title
                textSize = 29f
                setTypeface(Typeface.DEFAULT_BOLD)
                setPadding(18, 18, 18, 18)
                gravity = Gravity.CENTER_HORIZONTAL
                typeface = resources.getFont(R.font.hammersmith_one)
                setTextColor(ContextCompat.getColor(context, R.color.black))
            }
            row.addView(col)
            i += 1
        }

        //PUTTING EVERYTHING TOGETHER TO CREATE THE TWO CARDS
        table.addView(row)
        populateList(items, table)
        container.addView(titleText)
        container.addView(table)
        linLay.addView(container)
        grocArray.add(table)
        linLay.addView(spacer)
        spinnerItems.add(title)
        adapter.notifyDataSetChanged()
        Log.d("ADDED LIST", grocArray.toString())
        return table
    }


    //GETTING FROM JSON THE SMALLER LIST VALS
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

    // converts intputs into a JSONArray for populate list function
    private fun addItem(name: String = "test",
                        quantity: Int = 10000,
                        owner: String = "BobDiddleBob",
                        list: View = grocArray[0]){
        val string = "[{\"name\":  \"$name\", \"quantity\": $quantity, \"owner\": $owner}]"
        val json = JSONArray(string)
        populateList(json, list as TableLayout)
    }


}

