package com.example.phase12

import android.app.PendingIntent.getActivity
import android.content.DialogInterface
import android.os.Bundle
import android.os.DeadObjectException
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.GroceryListBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException


class GroceryList : toolbar() {
    // Variables
    private lateinit var binding: ViewBinding
    private lateinit var fab: View
    private var grocArray: MutableList<View> = mutableListOf()
    private lateinit var fav_1: Button
    private lateinit var fav_2: Button
    private lateinit var fav_3: Button
    private lateinit var fav_4: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grocery_list)

        binding = GroceryListBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // init vars
        fab = findViewById<View>(R.id.fab_grocery_list)
        fav_1 = findViewById<Button>(R.id.fav_1)
        fav_2 = findViewById<Button>(R.id.fav_2)
        fav_3 = findViewById<Button>(R.id.fav_3)
        fav_4 = findViewById<Button>(R.id.fav_4)



        // add item pop-up
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.add_item, null)
        builder.setView(view)
        builder.setMessage("What would you like to add?")
        builder.setTitle("Add Item")
        builder.setCancelable(true)
        var itemName = view.findViewById<EditText>(R.id.item_name)
        var itemQuant = view.findViewById<EditText>(R.id.quantity)
        var itemprice = view.findViewById<EditText>(R.id.price)
        var isFav = view.findViewById<CheckBox>(R.id.favorite)


        builder.setPositiveButton("Add") {
                dialog, _ ->


                    try{ addItem(itemName.text.toString(), itemQuant.text.toString().toInt(), "me", itemprice.text.toString().toInt(),isFav.isChecked)}
                    catch (e: NumberFormatException){
                        Log.d("READ DIALOG ERROR","error when trying to read AlertDialog: NumberFormatException")
                    }
                    catch (e: DeadObjectException){
                        Log.d("READ DIALOG ERROR","error when trying to read AlertDialog: DeadObjectException")
                    }
                    catch (e: NullPointerException){
                        Log.d("READ DIALOG ERROR","error when trying to read AlertDialog: NullPointerException")
                    }
                    dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") {
                dialog, _ -> dialog.dismiss()
        }
        val alertDialog = builder.create()





        // Button Handlers
        fav_1.setOnClickListener {
            itemName.setText(fav_1.text)
            itemQuant.setText("")
            itemprice.setText("")
            isFav.isChecked = false
            alertDialog.show()
            true
        }

        // Button Handlers
        fav_2.setOnClickListener {
            itemName.setText(fav_2.text)
            itemQuant.setText("")
            itemprice.setText("")
            isFav.isChecked = false
            alertDialog.show()
            true
        }
        // Button Handlers
        fav_3.setOnClickListener {
            itemName.setText(fav_3.text)
            itemQuant.setText("")
            itemprice.setText("")
            isFav.isChecked = false
            alertDialog.show()
            true
        }
        fav_4.setOnClickListener {
            itemName.setText(fav_4.text)
            itemQuant.setText("")
            itemprice.setText("")
            isFav.isChecked = false
            alertDialog.show()
            true
        }

        // FAB (lower right button)
        fab.setOnClickListener {
            itemName.setText("")
            itemQuant.setText("")
            itemprice.setText("")
            isFav.isChecked = false
            alertDialog.show()
            true
        }

        // Header toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val dataList = readJson()
        if (dataList != null) {
            Log.d("READ", dataList[1].toString())
            parse(dataList)
        } else {
            Log.d("READ Failed", "Failed to read or parse data from JSON file.")
        }


    }







    // Filling Lists

    // Read data
    private fun readJson(): JSONArray? {
        try {
            return JSONArray(
                // JSON reading stolen from this https://www.youtube.com/watch?v=B9jrhLyRwBs
                applicationContext.resources.openRawResource(R.raw.example).bufferedReader()
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
            var author = curItem.getJSONArray("authors")
            var curList = curItem.getJSONArray("items")
            var table = createList(name, curList)
        }
    }


    // Creates the List container, table inside, and spacer
    private fun createList(title: String, items: JSONArray): LinearLayout {
        val linLay = findViewById<LinearLayout>(R.id.groc_body)
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
            background = ContextCompat.getDrawable(this@GroceryList, R.drawable.view_container)
            orientation = LinearLayout.VERTICAL

        }
        container.setPadding(40)

        val title = TextView(this).apply {
            id = titleID
            layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )
            text = title
            textSize = 24f
            gravity = TextView.TEXT_ALIGNMENT_VIEW_START
            textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
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
        val cols = arrayOf("Name", "Owner", "Count", "Price","Bought?")
        val weights = arrayOf(1.5f, 1.5f, 1f, 1f,1.3f)
        var i = 0

        for (title in cols) {
            val col = TextView(this).apply {
                layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    weights[i]
                )
                text = title
                textSize = 23f
                setPadding(18, 18, 18, 18)
                gravity = Gravity.CENTER_HORIZONTAL
            }
            row.addView(col)
            i += 1
        }
        table.addView(row)
        populateList(items, table)
        container.addView(title)
        container.addView(table)
        linLay.addView(container)
        grocArray.add(table)
        linLay.addView(spacer)


        Log.d("ADDED LIST", grocArray.toString())
        return table
    }

    // Should change the specific button values
    private fun populateFavorites(curr: JSONObject) {
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
                        1.5f
                    )
                    gravity = Gravity.CENTER
                    textSize = 20f
                    setPadding(8, 8, 8, 8)
                }
                var quant = TextView(this).apply {
                    text = curr.getString("quantity")
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    gravity = Gravity.CENTER
                    textSize = 20f
                    setPadding(8, 8, 8, 8)
                }
                var owner = TextView(this).apply {
                    text = curr.getString("owner")
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.5f
                    )
                    gravity = Gravity.CENTER
                    textSize = 20f
                    setPadding(8, 8, 8, 8)
                }
                var price = TextView(this).apply {
                    text = curr.getString("price")
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    textSize = 20f
                    gravity = Gravity.CENTER
                    setPadding(8, 8, 8, 8)
                }
                var check = LinearLayout(this).apply {
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.3f,

                    )

                }
                check.gravity = Gravity.CENTER
                var checkItem = CheckBox(this).apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                }
                check.addView(checkItem)
                if (curr.getBoolean("favorite")){
                    populateFavorites(curr)

                }
                row.addView(name)
                row.addView(owner)
                row.addView(quant)
                row.addView(price)
                row.addView(check)
                tableView.addView(row)

            }
        }
    }

    // converts intputs into a JSONArray for populate list function
    private fun addItem(name: String = "test",
                        quantity: Int = 10000,
                        owner: String = "BobDiddleBob",
                        price: Int = 10000,
                        fav: Boolean = true,
                        list: View = grocArray[0]){
        val string = "[{\"name\": $name, \"quantity\": $quantity, \"owner\": $owner, \"price\": $price,\"favorite\": $fav}]"
        val json = JSONArray(string)
        populateList(json, list as TableLayout)
    }
}