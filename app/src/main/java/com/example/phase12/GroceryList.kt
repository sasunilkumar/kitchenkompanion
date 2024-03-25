package com.example.phase12

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.core.view.setPadding
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.GroceryListBinding
import kotlinx.serialization.Serializable
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.lang.reflect.Array


@Serializable
data class Item(val name: String, val quantity: Int, val owner: List<String>, val price: Int, val favorite: Boolean )

@Serializable
data class Lists(val name: Int, val authors: String, val item: List<Item>)


annotation class Composable

class GroceryList : toolbar() {
    private lateinit var binding: ViewBinding
    private lateinit var fab: View
    private var listCount: Int = 0
    private var grocArray: MutableList<View> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grocery_list)



        binding = GroceryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fab = findViewById<View>(R.id.fab_grocery_list)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val dataList = readJson()
        if (dataList != null) {
            Log.d("READ", dataList[1].toString())
            parse(dataList)
        } else {
            Log.d("READ Failed", "Failed to read or parse data from JSON file.")
        }


        fab.setOnClickListener {
            addItem()
            true
        }

    }

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
    private fun parse(item: JSONArray) {
        for (i in 0 until item.length()) {
            var curItem = item.getJSONObject(i)
            var name = curItem.getString("name")
            var author = curItem.getJSONArray("authors")
            var curList = curItem.getJSONArray("items")
            var table = createList(name, curList)
        }
    }


//    The viewGroup groc_body will act as the layout's main body where all the data is displayed
//    we will want to put each of the lists into another linear viewGroup. between each views
//    we will have another view which will act as a spacer. So whenever adding a new list make
//    sure to also add a spacer. Each list should have the title on top, with a table of items.

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
                TableLayout.LayoutParams.WRAP_CONTENT
            )
        }
        val cols = arrayOf("Name", "Quantity", "Owner", "Price")
        for (title in cols) {
            val col = TextView(this).apply {
                layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                )
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                text = title
                textSize = 18f
                setBackgroundColor(0xFF0000FF.toInt())
                setPadding(18, 18, 18, 18)
            }
            row.addView(col)
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
    private fun populateFavorites(curr: JSONObject) {
        var container = findViewById<LinearLayout>(R.id.favorite_tab)
        var row = TableRow(this).apply {
            layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )
        }

        var name = TextView(this).apply {
            text = curr.getString("name")
            layoutParams = TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
            )
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            textSize = 13f
            setPadding(8, 8, 8, 8)
        }
        var quant = TextView(this).apply {
            text = curr.getString("quantity")
            layoutParams = TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
            )
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            textSize = 13f
            setPadding(8, 8, 8, 8)
        }
        var price = TextView(this).apply {
            text = curr.getString("owner")
            layoutParams = TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
            )
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            textSize = 13f
            setPadding(8, 8, 8, 8)
        }
        var button = Button(this).apply {
            text = "Add"
            layoutParams = LinearLayout.LayoutParams(
                50,100
            )
        }

        row.addView(name)
        row.addView(quant)
        row.addView(price)
        row.addView(button)

        container.addView(row)
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
                        1f
                    )
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    textSize = 13f
                    setPadding(8, 8, 8, 8)
                }
                var quant = TextView(this).apply {
                    text = curr.getString("quantity")
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    textSize = 13f
                    setPadding(8, 8, 8, 8)
                }
                var price = TextView(this).apply {
                    text = curr.getString("owner")
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    textSize = 13f
                    setPadding(8, 8, 8, 8)
                }
                var owner = TextView(this).apply {
                    text = curr.getString("price")
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    textSize = 13f
                    setPadding(8, 8, 8, 8)
                }
                if (curr.getBoolean("favorite")){
                    populateFavorites(curr)
                }
                row.addView(name)
                row.addView(quant)
                row.addView(price)
                row.addView(owner)
                tableView.addView(row)

            }
        }
    }
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