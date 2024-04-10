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
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.setPadding
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.GroceryListBinding
import com.example.phase12.ui.theme.AppBar
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException


class GroceryList : AppBar() {
    // Variables
    private lateinit var binding: ViewBinding
    private lateinit var fab: View
    private var grocArray: MutableList<View> = mutableListOf()
    private lateinit var fav1: ConstraintLayout
    private lateinit var fav2: ConstraintLayout
    private lateinit var fav3: ConstraintLayout
    private lateinit var fav4: ConstraintLayout
    private lateinit var spinner: Spinner
    private lateinit var spinnerItems: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private var tableTotal = HashMap<View, Int>()
    private var tableTotalID = HashMap<View, Int>()
    private lateinit var operations: JSONArray


    companion object {
        const val OPERATION_LIST = "OPERATION_LIST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grocery_list)

        binding = GroceryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBar()

        // init vars
        fab = findViewById<View>(R.id.fab_grocery_list)
        fav1 = findViewById<ConstraintLayout>(R.id.fav_1)
        fav2 = findViewById<ConstraintLayout>(R.id.fav_2)
        fav3 = findViewById<ConstraintLayout>(R.id.fav_3)
        fav4 = findViewById<ConstraintLayout>(R.id.fav_4)
        var item1 = findViewById<TextView>(R.id.fav_item_1)
        var item2 = findViewById<TextView>(R.id.fav_item_2)
        var item3 = findViewById<TextView>(R.id.fav_item_3)
        var item4 = findViewById<TextView>(R.id.fav_item_4)
        var editItem1 = findViewById<ImageButton>(R.id.overflowButton)
        var editItem2 = findViewById<ImageButton>(R.id.overflowButton2)
        var editItem3 = findViewById<ImageButton>(R.id.overflowButton3)
        var editItem4 = findViewById<ImageButton>(R.id.overflowButton4)

        operations = JSONArray()

        val favBuilder = AlertDialog.Builder(this)
        val favView = EditText(this).apply {
            layoutParams = TableLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_VERTICAL
            }
            hint = "New favorite item"
            id = View.generateViewId()
        }
        favBuilder.setView(favView)
        favBuilder.setTitle("What would you like to add?")
        favBuilder.setCancelable(true)

        favBuilder.setPositiveButton("Add") { dialog, _ ->
            dialog.dismiss()
        }
        favBuilder.setNegativeButton("Cancel") {
                dialog, _ -> dialog.dismiss()
        }
        val favDialog = favBuilder.create()

        // add item pop-up
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.add_item, null)
        builder.setView(view)
        builder.setTitle("What would you like to add?")
        builder.setCancelable(true)
        var itemName = view.findViewById<EditText>(R.id.item_name)
        var userName = view.findViewById<EditText>(R.id.user_name)
        var itemQuant = view.findViewById<EditText>(R.id.quantity)
        var itemprice = view.findViewById<EditText>(R.id.price)

        var tableName = view.findViewById<EditText>(R.id.list_name)

        var itemVisibility =  view.findViewById<RadioButton>(R.id.radio_item)
        var itemBody = view.findViewById<LinearLayout>(R.id.add_item_group)
        var listVisibility =  view.findViewById<RadioButton>(R.id.radio_list)
        var listBody = view.findViewById<LinearLayout>(R.id.add_list_group)


        itemVisibility.setOnClickListener {
            itemBody.visibility = View.VISIBLE
            listBody.visibility = View.GONE

        }

        listVisibility.setOnClickListener {
            listBody.visibility = View.VISIBLE
            itemBody.visibility = View.GONE

        }




        // Spinner
        spinner = view.findViewById(R.id.add_item_spinner)
        spinnerItems = arrayListOf<String>()
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter





        //  Wont take numerical values greater than 11 digits in either column, We will
        //  see overflow if the digits exceed 7
        builder.setPositiveButton("Add") { dialog, _ ->
            if (itemVisibility.isChecked) {
                Log.d("ADD LISTENER", "trying to add item")

                try {
                    addItem(itemName.text.toString(), itemQuant.text.toString().toInt(), userName.text.toString(), itemprice.text.toString().toInt(), false, grocArray[spinner.selectedItemPosition]
                    )
                    var name = itemName.text.toString()
                    var quantity = itemQuant.text.toString().toInt()
                    var owner = userName.getText().toString()
                    Log.d("DATACHECK","$owner")
                    var price = itemprice.text.toString().toInt()
                    var fav = false
                    var list = spinner.selectedItemPosition
                    var operation = "addItem"
                    val string = "[{\"name\":  \"$name\", \"quantity\": $quantity, \"owner\": \"$owner\", \"price\": $price,\"favorite\": $fav}]"

                    val agg = "{\"operation\": $operation, \"fields\": {\"list\": $list , \"json\": $string}}"
                    var json = JSONObject(agg)
                    operations.put(json)

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
            if (listVisibility.isChecked) {
                Log.d("ADD LISTENER", "trying to add list")
                try {
                    createList(tableName.text.toString(), JSONArray())
                    var str = "{\"operation\": \"addList\",  \"fields\": \"${tableName.text.toString()}\"}"
                    var json = JSONObject(str)
                    operations.put(json)
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
        }
        builder.setNegativeButton("Cancel") {
                dialog, _ -> dialog.dismiss()
        }
        val alertDialog = builder.create()


        // Button Handlers
        fav1.setOnClickListener {

            listBody.visibility = View.GONE
            itemBody.visibility = View.VISIBLE
            itemVisibility.isChecked = true
            itemVisibility.visibility = View.GONE
            listVisibility.visibility = View.GONE
            itemName.setText(item1.text)
            itemQuant.setText("")
            itemprice.setText("")
            userName.setText("")
            tableName.setText("")
            alertDialog.show()
            true
        }

        // Button Handlers
        fav2.setOnClickListener {
            listBody.visibility = View.GONE
            itemBody.visibility = View.VISIBLE
            itemVisibility.isChecked = true
            itemVisibility.visibility = View.GONE
            listVisibility.visibility = View.GONE
            itemName.setText(item2.text)
            itemQuant.setText("")
            itemprice.setText("")
            userName.setText("")
            tableName.setText("")
            alertDialog.show()
            true
        }
        // Button Handlers
        fav3.setOnClickListener {
            listBody.visibility = View.GONE
            itemBody.visibility = View.VISIBLE
            itemVisibility.isChecked = true
            itemVisibility.visibility = View.GONE
            listVisibility.visibility = View.GONE
            itemName.setText(item3.text)
            itemQuant.setText("")
            itemprice.setText("")
            userName.setText("")
            tableName.setText("")
            alertDialog.show()
            true
        }
        fav4.setOnClickListener {
            listBody.visibility = View.GONE
            itemBody.visibility = View.VISIBLE
            itemVisibility.isChecked = true
            itemVisibility.visibility = View.GONE
            listVisibility.visibility = View.GONE
            itemName.setText(item4.text)
            itemQuant.setText("")
            itemprice.setText("")
            userName.setText("")
            tableName.setText("")
            alertDialog.show()
            true
        }

        // FAB (lower right button)
        fab.setOnClickListener {
            listBody.visibility = View.GONE
            itemBody.visibility = View.GONE
            itemVisibility.visibility = View.VISIBLE
            listVisibility.visibility = View.VISIBLE
            itemVisibility.isChecked = false
            listVisibility.isChecked = false
            itemName.setText("")
            itemQuant.setText("")
            itemprice.setText("")
            userName.setText("")
            tableName.setText("")
            alertDialog.show()
            true
        }
        editItem1.setOnClickListener {
            favView.setText("")
            favDialog.show()
            favDialog.setOnDismissListener {
                // Update item1 text once the dialog is dismissed
                if (favView.text.toString().isNotEmpty()){
                    item1.text = favView.text.toString()
                    var str = "{\"operation\": \"updateFav\",  \"fields\":{\"name\":  \"${favView.text.toString()}\", \"button\": ${item1.id}}}"
                    var json = JSONObject(str)
                    operations.put(json)
                }
            }
        }
        editItem2.setOnClickListener{
            favView.setText("")
            favDialog.show()
            favDialog.setOnDismissListener {
                // Update item1 text once the dialog is dismissed
                if (favView.text.toString().isNotEmpty()){
                    item2.text = favView.text.toString()
                    var str = "{\"operation\": \"updateFav\",  \"fields\":{\"name\":  \"${favView.text.toString()}\", \"button\": ${item2.id}}}"
                    var json = JSONObject(str)
                    operations.put(json)}
            }
        }
        editItem3.setOnClickListener{
            favView.setText("")
            favDialog.show()
            favDialog.setOnDismissListener {
                // Update item1 text once the dialog is dismissed
                if (favView.text.toString().isNotEmpty()){
                    item3.text = favView.text.toString()
                    var str = "{\"operation\": \"updateFav\",  \"fields\":{\"name\":  \"${favView.text.toString()}\", \"button\": ${item3.id}}}"
                    var json = JSONObject(str)
                    operations.put(json)}

            }
        }
        editItem4.setOnClickListener{
            favView.setText("")
            favDialog.show()
            favDialog.setOnDismissListener {
                // Update item1 text once the dialog is dismissed
                if (favView.text.toString().isNotEmpty()){
                    item4.text = favView.text.toString()
                    var str = "{\"operation\": \"updateFav\",  \"fields\":{\"name\":  \"${favView.text.toString()}\", \"button\": ${item4.id}}}"
                    var json = JSONObject(str)
                    operations.put(json)
                }
            }

        }


        val dataList = readJson()
        if (dataList != null) {
            Log.d("READ", dataList[1].toString())
            parse(dataList)
        } else {
            Log.d("READ Failed", "Failed to read or parse data from JSON file.")
        }
        Log.i("StateLifecycle","trying to recover")
        if (savedInstanceState != null){
            Log.i("StateLifecycle","entered recovery block")
        }else{
            Log.i("StateLifecycle","failed to enter recovery block")
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
        val countID = View.generateViewId()


        val spacer = View(this).apply {
            id = spacerID
            layoutParams = LinearLayout.LayoutParams(
                0, 35)
        }
        val container = LinearLayout(this).apply {
            id = listId
            layoutParams = LinearLayout.LayoutParams(
                700,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER_HORIZONTAL
            orientation = LinearLayout.VERTICAL
            isClickable = true

        }
        var draw: Drawable? = ContextCompat.getDrawable(this, R.drawable.view_container)
        if (draw != null) {
            draw = DrawableCompat.wrap(draw)
            DrawableCompat.setTint(draw, Color.parseColor("#BAEDF9"))
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
            textSize = 30f
            gravity = TextView.TEXT_ALIGNMENT_VIEW_START
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            typeface = ResourcesCompat.getFont(this.context, R.font.hammersmith_one)
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }


        val table = TableLayout(this).apply {
            id = tableID
            layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )
            visibility = View.GONE

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
                typeface = ResourcesCompat.getFont(this.context, R.font.hammersmith_one)
                setTextColor(ContextCompat.getColor(context, R.color.black))
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
            textSize = 25f
            setPadding(0,30,0,0)
            typeface = ResourcesCompat.getFont(this.context, R.font.hammersmith_one)
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }
        container.addView(sum)
        linLay.addView(spacer)
        spinnerItems.add(title)
        adapter.notifyDataSetChanged()


        Log.d("ADDED LIST", grocArray.toString())

        container.setOnClickListener{
            if (table.visibility == View.GONE){
                table.visibility = View.VISIBLE

                Log.d("CARD", "Make table visible")
            }else {
                table.visibility = View.GONE
                Log.d("CARD", "Make table invisible")
            }
        }
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
                    textSize = 22f
                    setPadding(8, 8, 8, 8)
                    typeface = ResourcesCompat.getFont(this.context, R.font.hammersmith_one)
                    setTextColor(ContextCompat.getColor(context, R.color.black))

                }
                var quant = TextView(this).apply {
                    text = curr.getString("quantity")
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    gravity = Gravity.CENTER
                    textSize = 22f
                    setPadding(8, 8, 8, 8)
                    typeface = ResourcesCompat.getFont(this.context, R.font.hammersmith_one)
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                }
                var owner = TextView(this).apply {
                    text = curr.getString("owner")
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.5f
                    )
                    gravity = Gravity.CENTER
                    textSize = 22f
                    setPadding(8, 8, 8, 8)
                    typeface = ResourcesCompat.getFont(this.context, R.font.hammersmith_one)
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                }
                var price = TextView(this).apply {
                    text = curr.getString("price")
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    textSize = 22f
                    gravity = Gravity.CENTER
                    setPadding(8, 8, 8, 8)
                    typeface = ResourcesCompat.getFont(this.context, R.font.hammersmith_one)
                    setTextColor(ContextCompat.getColor(context, R.color.black))
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
                    typeface = ResourcesCompat.getFont(this.context, R.font.hammersmith_one)
                    setTextColor(ContextCompat.getColor(context, R.color.black))

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
                        fav: Boolean = true,
                        list: View = grocArray[0]){
        val string = "[{\"name\":  \"$name\", \"quantity\": $quantity, \"owner\": \"$owner\", \"price\": $price,\"favorite\": $fav}]"
        val json = JSONArray(string)
        populateList(json, list as TableLayout)
        var sumID = tableTotalID[list] ?: 0
        var text = findViewById<TextView>(sumID)
        text.setText("List Total: $"+ tableTotal.getValue(list).toString())

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("OPERATION_LIST", operations.toString());
        Log.i("StateLifecycle","${operations.toString()}")
        super.onSaveInstanceState(outState)


    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("StateLifecycle", "onRestoreInstanceState")
        try {
            val recovery = savedInstanceState.getString("OPERATION_LIST")
            val actions = JSONArray(recovery)
            for (i in 0 until actions.length()) {
                var action = actions.getJSONObject(i)
                when (action.getString("operation")) {
                    "updateFav" -> {
                        Log.d("RECOVERY","ROLLBACK, Fav to ${action.toString()}")
                        val fields = action.getJSONObject("fields")
                        var id = fields.getInt("button")
                        var name = fields.getString("name")

                        val item = findViewById<TextView>(id)
                        item.text = name
                        operations.put(action)

                    }
                    "addList" ->  {
                        Log.d("RECOVERY","ROLLBACK, NEW LIST to ${action.toString()}")
                        val name = action.getString("fields")
                        createList(name, JSONArray())
                        operations.put(action)



                    }
                    "addItem" ->  {
                        Log.d("RECOVERY","ROLLBACK, NEW ITEM to ${action.toString()}")
                        val fields = action.getJSONObject("fields")
                        var json = fields.getJSONArray("json").getJSONObject(0)
                        val list = grocArray[fields.getInt("list")]
                        val name = json.getString("name")
                        val quantity = json.getInt("quantity")
                        val owner = json.getString("owner")
                        val price = json.getInt("price")
                        val favorite = json.getBoolean("favorite")
                        addItem(name, quantity, owner, price, favorite, list)
                        operations.put(action)

                    }
                    else -> Log.d("RECOVERY", "Unknown operation")
                }

            }

            } catch (e: JSONException) { }
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

    }

}




// I need to track names of favorites, items in each list, lists added.
// If I track them they also need to be tracked as they are being added,
// since I don't want to recover a data value in a list that does not exist yet

// to achieve this I want to edit a JSON or text file that will append
// the most recent action, this is somewhat similar to a transaction,
// and a snapshot of the database. In which by iterating over it again
// I can regain a stable state. So as the operations come in I need to
// also add them to some file.

