package com.example.phase12

import android.animation.LayoutTransition
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import com.example.phase12.databinding.RecipesBinding
import androidx.cardview.widget.CardView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import com.example.phase12.ui.theme.AppBar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class Recipes : AppBar() {
    private lateinit var binding: RecipesBinding
    private lateinit var detailsText: LinearLayout
    private lateinit var detailsText2: LinearLayout
    private lateinit var recipe1: CardView
    private lateinit var recipe2: CardView
    private lateinit var arrowDown1: ImageView
    private lateinit var arrowUp1: ImageView
    private lateinit var arrowDown2: ImageView
    private lateinit var arrowUp2: ImageView
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

        arrowDown1 = findViewById(R.id.arrow_down_1)
        arrowUp1 = findViewById(R.id.arrow_up_1)

        arrowDown2 = findViewById(R.id.arrow_down_2)
        arrowUp2 = findViewById(R.id.arrow_up_2)

        //layout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        recipe1.setOnClickListener {
            if (detailsText.visibility == View.GONE) {
                detailsText.visibility = View.VISIBLE
            } else {
                detailsText.visibility = View.GONE
            }
            if (arrowDown1.visibility == View.VISIBLE) {
                arrowDown1.visibility = View.GONE
                arrowUp1.visibility = View.VISIBLE
            } else {
                arrowUp1.visibility = View.GONE
                arrowDown1.visibility = View.VISIBLE
            }
        }

        recipe2.setOnClickListener {
            if (detailsText2.visibility == View.GONE) {
                detailsText2.visibility = View.VISIBLE
            } else {
                detailsText2.visibility = View.GONE
            }
            if (arrowDown2.visibility == View.VISIBLE) {
                arrowDown2.visibility = View.GONE
                arrowUp2.visibility = View.VISIBLE
            } else {
                arrowUp2.visibility = View.GONE
                arrowDown2.visibility = View.VISIBLE
            }
        }

        val parentLayout = findViewById<LinearLayout>(R.id.recipes)
        val newCard = createRecipeCard(this, "Pineapple Cake", 2)
        parentLayout.addView(newCard)
        //onclickListener for plus button
    }

    private fun addNewRecipe() {

    }

    private fun changeRecipe() {

    }

    private fun createRecipeCard(context: Context, title: String, ratingStars: Int): CardView {
        //Setting parameters
        val cardView = CardView(context, null, R.style.CardViewStyle)
        cardView.id = View.generateViewId()

        val cardLayoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
        cardView.layoutParams = cardLayoutParams

        cardView.setBackgroundResource(R.drawable.cardview_container)

        val pad = context.resources.getDimensionPixelSize(R.dimen.cardPadding)
        val margin = context.resources.getDimensionPixelSize(R.dimen.cardMargin)

        cardView.setContentPadding(0, 0, 0, 0)
        cardLayoutParams.setMargins(margin, pad, margin, pad)

        // Linear Layout for Card Content
        val cardLinearLayout = LinearLayout(context)
        cardLinearLayout.id = View.generateViewId()

        val linLayoutOrientation = LinearLayout.VERTICAL
        cardLinearLayout.orientation = linLayoutOrientation

        cardLinearLayout.layoutParams = cardLayoutParams
        cardLinearLayout.layoutTransition = LayoutTransition()

        // Relative Layout for top of card elements
        val relativeTopCardView = RelativeLayout(context)

        val wrapParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        relativeTopCardView.layoutParams = wrapParams

        //Creating Recipe Title
        val titleTextView = TextView(context)
        titleTextView.id = View.generateViewId()

        val topCardParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        //topCardParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
        topCardParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        titleTextView.layoutParams = topCardParams

        val font = ResourcesCompat.getFont(context, R.font.hammersmith_one)
        titleTextView.setTypeface(font, Typeface.BOLD)

        titleTextView.text = title
        titleTextView.textSize = 30F

        val textColor = ContextCompat.getColor(context, R.color.black)
        titleTextView.setTextColor(textColor)

        titleTextView.gravity = Gravity.CENTER_VERTICAL

        // Creating down arrow
        val downArrowImageView = ImageView(context)
        downArrowImageView.id = View.generateViewId()

        val plusSize = context.resources.getDimensionPixelSize(R.dimen.plusSize)
        val downParams = RelativeLayout.LayoutParams(plusSize, plusSize)
        downParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
        downParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        downArrowImageView.layoutParams = downParams

        val downSrc = ContextCompat.getDrawable(context, R.drawable.baseline_keyboard_arrow_down_24)
        downArrowImageView.setImageDrawable(downSrc)

        downArrowImageView.visibility = View.VISIBLE

        // Creating up arrow
        val upArrowImageView = ImageView(context)
        upArrowImageView.id = View.generateViewId()

        val upParams = RelativeLayout.LayoutParams(32, 32)
        upParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
        upParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        upArrowImageView.layoutParams = upParams

        val upSrc = ContextCompat.getDrawable(context, R.drawable.baseline_keyboard_arrow_up_24)
        upArrowImageView.setImageDrawable(upSrc)

        upArrowImageView.visibility = View.GONE

        // Dummy Icon 1
        val dummy1 = ImageView(context)
        dummy1.id = View.generateViewId()

        val dummy1LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        dummy1LayoutParams.addRule(RelativeLayout.LEFT_OF, downArrowImageView.id)
        dummy1LayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)

        val imagePad = context.resources.getDimensionPixelSize(R.dimen.imagePadding)
        //dummy1LayoutParams.setMargins(0, 0, rightPad + leftPad, 0)
        dummy1.setPadding(imagePad, 0, imagePad, 0)
        dummy1.layoutParams = dummy1LayoutParams

        val dummySrc = ContextCompat.getDrawable(context, R.mipmap.dietary_plus_round)
        dummy1.setImageDrawable(dummySrc)

        // Dummy Icon 2
        val dummy2 = ImageView(context)
        dummy2.id = View.generateViewId()

        val dummy2LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        dummy2LayoutParams.addRule(RelativeLayout.LEFT_OF, dummy1.id)
        //dummy2LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)

        dummy2.setPadding(imagePad, 0, 0, 0)
        dummy2.layoutParams = dummy2LayoutParams

        dummy2.setImageDrawable(dummySrc)

        // Creating Rating Bar
        val ratingBarView = AppCompatRatingBar(context)
        ratingBarView.numStars = 3
        ratingBarView.id = View.generateViewId()

        val ratingBarParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        ratingBarParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        ratingBarParams.addRule(RelativeLayout.LEFT_OF, dummy2.id)
        ratingBarView.layoutParams = ratingBarParams

        val ratingColor = ContextCompat.getColor(context, R.color.white)
        ratingBarView.progressTintList = ColorStateList.valueOf(ratingColor)

        // Adding elements into cardview
        relativeTopCardView.addView(titleTextView)
        relativeTopCardView.addView(downArrowImageView)
        relativeTopCardView.addView(upArrowImageView)
        relativeTopCardView.addView(dummy1)
        relativeTopCardView.addView(dummy2)
        relativeTopCardView.addView(ratingBarView)

        cardLinearLayout.addView(relativeTopCardView)
        cardView.addView(cardLinearLayout)

        return cardView
    }
}