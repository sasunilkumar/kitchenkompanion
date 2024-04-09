package com.example.phase12

import android.animation.LayoutTransition
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.RecipesBinding
import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.example.phase12.ui.theme.AppBar
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class Recipes : AppBar() {
    private lateinit var binding: RecipesBinding
    private lateinit var detailsText: LinearLayout
    private lateinit var detailsText2: LinearLayout
    private lateinit var recipe1: CardView
    private lateinit var recipe2: CardView
    private lateinit var arrowDown: ImageView
    private lateinit var arrowUp: ImageView
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipes)
        binding = RecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBar()

        binding.fabRecipeList.setOnClickListener {
            val options = arrayOf("Add New Recipe", "Change Recipe")
            val favBuilder = AlertDialog.Builder(this)
            val onClickListener = DialogInterface.OnClickListener { dialog, which ->
                if (which == 0) {
                    addNewRecipe()
                } else if (which == 1) {
                    changeRecipe()
                }
            }
            favBuilder.setTitle("Add or Change Recipe?")
            favBuilder.setItems(options, onClickListener)
            favBuilder.setCancelable(true)
            favBuilder.create().show()
        }

        detailsText = findViewById(R.id.recipe_1_layout)
        detailsText2 = findViewById(R.id.recipe_2_layout)

        recipe1 = findViewById(R.id.recipe_1)
        recipe2 = findViewById(R.id.recipe_2)

        arrowDown = findViewById(R.id.arrow_down)
        arrowUp = findViewById(R.id.arrow_up)

        //layout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        recipe1.setOnClickListener {
            if (detailsText.visibility == View.GONE) {
                detailsText.visibility = View.VISIBLE
            } else {
                detailsText.visibility = View.GONE
            }
            if (arrowDown.visibility == View.VISIBLE) {
                arrowDown.visibility = View.GONE
                arrowUp.visibility = View.VISIBLE
            } else {
                arrowUp.visibility = View.GONE
                arrowDown.visibility = View.VISIBLE
            }
        }

        recipe2.setOnClickListener {
            if (detailsText2.visibility == View.GONE) {
                detailsText2.visibility = View.VISIBLE
            } else {
                detailsText2.visibility = View.GONE
            }
            if (arrowDown.visibility == View.VISIBLE) {
                arrowDown.visibility = View.GONE
                arrowUp.visibility = View.VISIBLE
            } else {
                arrowUp.visibility = View.GONE
                arrowDown.visibility = View.VISIBLE
            }
        }

        val parentLayout = findViewById<LinearLayout>(R.id.recipes)
        val newCard = createRecipeCard(this)
        parentLayout.addView(newCard)
        //call createRecipeCard()
        //onclickListener for plus button

//        val adapter = ViewPagerAdapter(supportFragmentManager)
//        adapter.addFragment(FragmentRecipeList(), "Recipe List")
//        adapter.addFragment(FragmentAddRecipe(), "Add Recipe")
//
//        viewPager.adapter = adapter
//        tabLayout.setupWithViewPager(viewPager)

    }

    private fun addNewRecipe() {

    }

    private fun changeRecipe() {

    }
}
    private fun createRecipeCard(context: Context) : CardView {
        val cardView = CardView(context, null, R.style.CardViewStyle)
        cardView.id = View.generateViewId()

        val layoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
        cardView.layoutParams = layoutParams

        cardView.setBackgroundResource(R.drawable.cardview_container)

        val pad = context.resources.getDimensionPixelSize(R.dimen.cardPadding)
        cardView.setContentPadding(pad, pad, pad, pad)

        val margin = context.resources.getDimensionPixelSize(R.dimen.cardMargin)
        layoutParams.setMargins(pad, margin, pad, margin)
        return cardView
    }
    //change call to createList to do in for loop for multiple ingredients
//    private fun createIngredient(title: String, items: JSONArray): LinearLayout {
//        //val linLay = findViewById<RelativeLayout>(R.id.recipe_1)
//        val ingredientId = View.generateViewId()
//        val spacerID = View.generateViewId()
//        val tableID = View.generateViewId()
//        val titleID = View.generateViewId()
//
//        val spacer = View(this).apply {
//            id = spacerID
//            layoutParams = LinearLayout.LayoutParams(
//                0, 35)
//        }
//
//        val container = RelativeLayout(this).apply {
//            id = ingredientId  // Set the ID number for this ingredient
//            layoutParams = RelativeLayout.LayoutParams(
//                // Build XML tag here
//                700,  // width in pixels
//                RelativeLayout.LayoutParams.WRAP_CONTENT   // height in pixels
//            )
//        }
//        container.setPadding(40)
//
//        val title = TextView(this).apply {
//            id = titleID
//            layoutParams = TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT
//            )
//            text = title
//            textSize = 24f
//            gravity = TextView.TEXT_ALIGNMENT_VIEW_START
//            textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
//        }
//
//        val table = TableLayout(this).apply {
//            id = tableID
//            layoutParams = TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT
//            )
//        }
//        val row = TableRow(this).apply {
//            layoutParams = TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT,
//            )
//        }
//        val cols = arrayOf("Name", "Quantity", "Owner", "Price","Bought")
//        val weights = arrayOf(2, 1, 1, 1,1)
//
//        table.addView(row)
//        container.addView(title)
//        container.addView(table)
//        //linLay.addView(container)
//        //linLay.addView(spacer)
//
//        return table
//    }
//}