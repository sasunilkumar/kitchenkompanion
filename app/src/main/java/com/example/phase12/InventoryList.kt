package com.example.phase12

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.DeadObjectException
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.setPadding
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.InventoryListBinding
import com.example.phase12.ui.theme.AppBar
import com.google.android.material.bottomappbar.BottomAppBar
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException

class InventoryList : AppBar() {

    //ALEX VAR
    private var grocArray: MutableList<View> = mutableListOf()
    private lateinit var spinner: Spinner
    private lateinit var spinnerItems: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private var tableTotal = HashMap<View, Int>()
    private var tableTotalID = HashMap<View, Int>()
    private lateinit var fab: View


    private lateinit var binding: InventoryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InventoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBar()

//        val toolbar = binding.root.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //ALEX ADD BUTTON VARs
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.add_inventory, null)
        builder.setView(view)
        builder.setMessage("What would you like to add?")
        builder.setTitle("Add Item")
        builder.setCancelable(true)
        var itemName = view.findViewById<EditText>(R.id.item_name)
        var itemQuant = view.findViewById<EditText>(R.id.quantity)
        var itemprice = view.findViewById<EditText>(R.id.price)

        fab = findViewById<View>(R.id.fab_grocery_list)

        // ALEX Spinner
        spinner = view.findViewById(R.id.add_item_spinner)
        spinnerItems = arrayListOf<String>()
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        //ALEX ADD CANCEL PART OF POPUP
        //  Wont take numerical values greater than 11 digits in either column, We will
        //  see overflow if the digits exceed 7
        builder.setPositiveButton("Add") { dialog, _ ->
            try {
                addItem(
                    itemName.text.toString(),
                    itemQuant.text.toString().toInt(),
                    "me",
                    itemprice.text.toString().toInt(),
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

        fab.setOnClickListener {
            itemName.setText("")
            itemQuant.setText("")
            itemprice.setText("")
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
        val countID = View.generateViewId()

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
//            background = ContextCompat.getDrawable(this@GroceryList, R.drawable.view_container)
            orientation = LinearLayout.VERTICAL

        }
        var draw: Drawable? = ContextCompat.getDrawable(this, R.drawable.view_container)
        if (draw != null) {
            draw = DrawableCompat.wrap(draw)
            DrawableCompat.setTint(draw, Color.parseColor("#FFADAD"))
        }
        container.background = draw

        container.setPadding(40)

        val titleText = TextView(this).apply {
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
        container.addView(titleText)
        container.addView(table)
        linLay.addView(container)
        grocArray.add(table)
        tableTotalID[table] = countID
        var sum = TextView(this).apply {
            id = countID
            text = "List Total: $"+ tableTotal.getValue(table).toString()
            textSize = 24f
            setPadding(0,30,0,0)
        }
        container.addView(sum)
        linLay.addView(spacer)
        spinnerItems.add(title)
        adapter.notifyDataSetChanged()


        Log.d("ADDED LIST", grocArray.toString())
        return table
    }

    // Should change the specific button values
    private fun populateFavorites(curr: JSONObject) {
    }
    private fun populateList(items: JSONArray?, view: TableLayout) {
        try {
            val count = tableTotal.getValue(view)
        } catch (e: NoSuchElementException) {
            Log.d("KEY NOT FOUND", "The current table did not have a count mapping")
            tableTotal[view] = 0
        }

        var curTotal = tableTotal[view] ?: 0
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
                if (curTotal != null) {
                    curTotal += curr.getString("price").toInt()
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
                row.addView(name)
                row.addView(owner)
                row.addView(quant)
                row.addView(price)
                row.addView(check)
                tableView.addView(row)

            }
            tableTotal[view] = curTotal
        }
    }

    // converts intputs into a JSONArray for populate list function
    private fun addItem(name: String = "test",
                        quantity: Int = 10000,
                        owner: String = "BobDiddleBob",
                        price: Int = 10000,
                        list: View = grocArray[0]){
        val string = "[{\"name\":  \"$name\", \"quantity\": $quantity, \"owner\": $owner, \"price\": $price}]"
        val json = JSONArray(string)
        populateList(json, list as TableLayout)
        var sumID = tableTotalID[list] ?: 0
        var text = findViewById<TextView>(sumID)
        text.setText("List Total: $"+ tableTotal.getValue(list).toString())

    }


}

//    private fun readJson(): JSONArray? {
//        try {
//            return JSONArray(
//                applicationContext.resources.openRawResource(R.raw.example).bufferedReader().use(BufferedReader::readText)
//            )
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        }
//        return null
//    }
//
//    private fun parse(item: JSONArray) {
//        for (i in 0 until item.length()) {
//            val curItem = item.getJSONObject(i)
//            val name = curItem.getString("name")
//            val curList = curItem.getJSONArray("items")
//            createList(name, curList)
//        }
//    }
//
//    private fun createList(title: String, items: JSONArray): TableLayout {
//        val linLay: LinearLayout = findViewById(R.id.inven_body)
//        val table = TableLayout(this).apply {
//            layoutParams = TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                 TableLayout.LayoutParams.WRAP_CONTENT
//            )
//            gravity = Gravity.CENTER_HORIZONTAL
//            setPadding(16)
//        }
//        val row = TableRow(this)
//        val cols = arrayOf("Name", "Quantity", "Location")
//        for (colTitle in cols) {
//            val tv = TextView(this).apply {
//                text = colTitle
//                textSize = 20f
//                setPadding(8)
//                gravity = Gravity.CENTER
//            }
//            row.addView(tv)
//        }
//        table.addView(row)
//
//        for (i in 0 until items.length()) {
//            val itemRow = TableRow(this)
//            val item = items.getJSONObject(i)
//            val name = item.getString("name")
//            val quantity = item.getInt("quantity")
//            val location = item.getString("price")
//            val nameView = TextView(this).apply {
//                text = name
//                setPadding(8)
//            }
//            val quantityView = TextView(this).apply {
//                text = quantity.toString()
//                setPadding(8)
//            }
//            val locationView = TextView(this).apply {
//                text = location
//                setPadding(8)
//            }
//            itemRow.addView(nameView)
//            itemRow.addView(quantityView)
//            itemRow.addView(locationView)
//            table.addView(itemRow)
//        }
//        linLay.addView(table)
//        return table
//    }
//}
