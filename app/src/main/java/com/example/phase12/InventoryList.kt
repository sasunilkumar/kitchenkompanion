package com.example.phase12

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.setPadding
import com.example.phase12.databinding.InventoryListBinding
import com.example.phase12.ui.theme.AppBar
import org.json.JSONArray
import java.io.BufferedReader
import java.io.FileNotFoundException

class InventoryList : AppBar()  {
    private lateinit var binding: InventoryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InventoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBar()

//        val toolbar = binding.root.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dataList = readJson()
        if (dataList != null) {
            parse(dataList)
        }
    }

    private fun readJson(): JSONArray? {
        try {
            return JSONArray(
                applicationContext.resources.openRawResource(R.raw.example).bufferedReader().use(BufferedReader::readText)
            )
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    private fun parse(item: JSONArray) {
        for (i in 0 until item.length()) {
            val curItem = item.getJSONObject(i)
            val name = curItem.getString("name")
            val curList = curItem.getJSONArray("items")
            createList(name, curList)
        }
    }

    private fun createList(title: String, items: JSONArray): TableLayout {
        val linLay: LinearLayout = findViewById(R.id.inven_body)
        var listId = View.generateViewId()


        val spacer = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                0, 35)
        }

        val container = LinearLayout(this).apply {
            id = listId
            layoutParams = LinearLayout.LayoutParams(
                700,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            isClickable = true
        }
        linLay.gravity = Gravity.CENTER_HORIZONTAL
        container.gravity = Gravity.CENTER_HORIZONTAL
        var draw: Drawable? = ContextCompat.getDrawable(this, R.drawable.view_container)
        if (draw != null) {
            draw = DrawableCompat.wrap(draw)
            DrawableCompat.setTint(draw, Color.parseColor("#FDFFB6"))
        }
        container.background = draw

        container.setPadding(40)

        val table = TableLayout(this).apply {
            layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                 TableLayout.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(16)
        }

        val row = TableRow(this)
        val cols = arrayOf("Name", "Quantity", "Location")
        for (colTitle in cols) {
            val tv = TextView(this).apply {
                text = colTitle
                textSize = 20f
                setPadding(8)
                gravity = Gravity.CENTER
            }
            row.addView(tv)
        }
        table.addView(row)

        for (i in 0 until items.length()) {
            val itemRow = TableRow(this)
            val item = items.getJSONObject(i)
            val name = item.getString("name")
            val quantity = item.getInt("quantity")
            val location = item.getString("price")
            val nameView = TextView(this).apply {
                text = name
                setPadding(8)
            }
            val quantityView = TextView(this).apply {
                text = quantity.toString()
                setPadding(8)
            }
            val locationView = TextView(this).apply {
                text = location
                setPadding(8)
            }
            itemRow.addView(nameView)
            itemRow.addView(quantityView)
            itemRow.addView(locationView)
            table.addView(itemRow)
        }
        container.addView(table)
        linLay.addView(container)
        linLay.addView(spacer)
        return table
    }
}
