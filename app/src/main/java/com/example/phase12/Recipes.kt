package com.example.phase12

import android.animation.LayoutTransition
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.os.PersistableBundle
import android.text.InputType
import android.view.Gravity
import android.widget.LinearLayout
import com.example.phase12.databinding.RecipesBinding
import androidx.cardview.widget.CardView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.compose.foundation.layout.Box
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import com.example.phase12.ui.theme.AppBar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
        val newCard = createRecipeCard(this, "Pineapple Cake", 2, 30, 40, 8)
        parentLayout.addView(newCard)
        //onclickListener for plus button
    }

    private fun addNewRecipe() {

    }

    private fun changeRecipe() {

    }

    private fun createRecipeCard(context: Context, title: String, ratingStars: Int, prepTime : Int,
                                 cookTime: Int, serves: Int): CardView {
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
        dummy1LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
        dummy1LayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)

        val imagePad = context.resources.getDimensionPixelSize(R.dimen.imagePadding)
        val imageMargin = context.resources.getDimensionPixelSize(R.dimen.imageMargin)

        dummy1LayoutParams.setMargins(0, 0, imageMargin, 0)
        dummy1.setPadding(imagePad, 0, 0, 0)
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

        ratingBarView.stepSize = 0.5F

        val ratingColor = ContextCompat.getColor(context, R.color.white)
        ratingBarView.progressTintList = ColorStateList.valueOf(ratingColor)

        /***********************************************/
        /* Section with interior (expandable) elements */
        /***********************************************/
        // Linear Layout for all internal contents
        val innerLayout = LinearLayout(context)
        innerLayout.id = View.generateViewId()

        val innerLayoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        innerLayout.layoutParams = innerLayoutParams
        innerLayout.layoutTransition = LayoutTransition()
        innerLayout.orientation = LinearLayout.VERTICAL

        innerLayout.visibility = View.GONE

        // Linear Layout for time/serving row of internal elements
        val timeLayout = LinearLayout(context)
        timeLayout.id = View.generateViewId()

        val timeLayoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        timeLayout.layoutParams = timeLayoutParams
        timeLayout.layoutTransition = LayoutTransition()
        timeLayout.orientation = LinearLayout.HORIZONTAL

        val timeLayoutPad = context.resources.getDimensionPixelSize(R.dimen.timePadding)
        timeLayout.setPadding(0, timeLayoutPad, 0, 0)

        // Linear Layout for Prep Time
        val prepLayout = LinearLayout(context)
        prepLayout.id = View.generateViewId()

        val prepLayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        prepLayoutParams.weight = 1F
        prepLayout.layoutParams = prepLayoutParams
        prepLayout.orientation=LinearLayout.VERTICAL
        prepLayout.gravity = Gravity.CENTER_VERTICAL or Gravity.START

        // Text View for Prep Time
        val prepText = TextView(context)
        prepText.id = View.generateViewId()

        val prepTextLayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        prepText.layoutParams = prepTextLayoutParams
        prepText.text = "Prep Time"
        prepText.setTypeface(font, Typeface.BOLD)
        prepText.setTextColor(textColor)
        prepText.textSize = 24F

        prepText.gravity = Gravity.CENTER_VERTICAL

        // InputText for Prep Time
        val prepInputText = TextInputLayout(context)
        prepInputText.id = View.generateViewId()

        val prepInputParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        prepInputText.layoutParams = prepInputParams

        val hintColor = ContextCompat.getColorStateList(context, R.color.black)
        prepInputText.setHintTextColor(hintColor)
        prepInputText.hint = "min."

        prepInputText.setPaddingRelative(imagePad, 0, 0, 0)
        prepInputText.boxStrokeColor = ContextCompat.getColor(context, R.color.white)
        prepInputText.boxStrokeWidth = context.resources.getDimensionPixelSize(R.dimen.inputBoxStroke)
        prepInputText.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE

        // EditText for Prep Time
        val prepEditText = TextInputEditText(context)
        prepEditText.id = View.generateViewId()

        val editWidth = context.resources.getDimensionPixelSize(R.dimen.editBoxWidth)
        val prepEditParams = LinearLayout.LayoutParams(editWidth, WRAP_CONTENT)
        prepEditText.setTextColor(textColor)
        prepEditText.layoutParams = prepEditParams
        prepEditText.inputType = InputType.TYPE_CLASS_NUMBER
        prepEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        prepEditText.setText(prepTime.toString())

        // Linear Layout for Cook Time
        val cookLayout = LinearLayout(context)
        cookLayout.id = View.generateViewId()

        val cookLayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        cookLayoutParams.weight = 1F
        cookLayout.layoutParams = cookLayoutParams
        cookLayout.orientation=LinearLayout.VERTICAL
        cookLayout.gravity = Gravity.CENTER_VERTICAL or Gravity.START

        // Text View for cook Time
        val cookText = TextView(context)
        cookText.id = View.generateViewId()

        val cookTextLayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        cookText.layoutParams = cookTextLayoutParams
        cookText.text = "Cook Time"
        cookText.setTypeface(font, Typeface.BOLD)
        cookText.setTextColor(textColor)
        cookText.textSize = 24F

        cookText.gravity = Gravity.CENTER_VERTICAL

        // InputText for cook Time
        val cookInputText = TextInputLayout(context)
        cookInputText.id = View.generateViewId()

        val cookInputParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        cookInputText.layoutParams = cookInputParams

        cookInputText.setHintTextColor(hintColor)
        cookInputText.hint = "min."

        cookInputText.setPaddingRelative(imagePad, 0, 0, 0)
        cookInputText.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        //cookInputText.boxStrokeColor = ContextCompat.getColor(context, R.color.white)
        //cookInputText.boxStrokeWidth = context.resources.getDimensionPixelSize(R.dimen.inputBoxStroke)

        // EditText for cook Time
        val cookEditText = TextInputEditText(context)
        cookEditText.id = View.generateViewId()

        val cookEditParams = LinearLayout.LayoutParams(editWidth, WRAP_CONTENT)
        cookEditText.setTextColor(textColor)
        cookEditText.layoutParams = cookEditParams
        cookEditText.inputType = InputType.TYPE_CLASS_NUMBER
        cookEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        cookEditText.setText(cookTime.toString())

        // Linear Layout for Servings
        val servesLayout = LinearLayout(context)
        servesLayout.id = View.generateViewId()

        val servesLayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        servesLayoutParams.weight = 1F
        servesLayout.layoutParams = servesLayoutParams
        servesLayout.orientation=LinearLayout.VERTICAL
        servesLayout.gravity = Gravity.CENTER_VERTICAL or Gravity.START

        // Text View for serves Time
        val servesText = TextView(context)
        servesText.id = View.generateViewId()

        val servesTextLayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        servesText.layoutParams = servesTextLayoutParams
        servesText.text = "Serves"
        servesText.setTypeface(font, Typeface.BOLD)
        servesText.setTextColor(textColor)
        servesText.textSize = 24F

        servesText.gravity = Gravity.CENTER_VERTICAL

        // InputText for serves Time
        val servesInputText = TextInputLayout(context)
        servesInputText.id = View.generateViewId()

        val servesInputParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        servesInputText.layoutParams = servesInputParams

        servesInputText.setHintTextColor(hintColor)
        servesInputText.hint = "ppl."

        servesInputText.setPaddingRelative(imagePad, 0, 0, 0)
        servesInputText.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        //servesInputText.boxStrokeColor = ContextCompat.getColor(context, R.color.white)
        //servesInputText.boxStrokeWidth = context.resources.getDimensionPixelSize(R.dimen.inputBoxStroke)

        // EditText for serves Time
        val servesEditText = TextInputEditText(context)
        servesEditText.id = View.generateViewId()

        val servesEditParams = LinearLayout.LayoutParams(editWidth, WRAP_CONTENT)
        servesEditText.setTextColor(textColor)
        servesEditText.layoutParams = servesEditParams
        servesEditText.inputType = InputType.TYPE_CLASS_NUMBER
        servesEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        servesEditText.setText(serves.toString())

        // Ingredients Header
        val ingredientsHeader = TextView(context)
        ingredientsHeader.id = View.generateViewId()

        val ingredientsHeaderParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        ingredientsHeader.layoutParams = ingredientsHeaderParams
        ingredientsHeader.setPadding(0, timeLayoutPad, 0, 0)

        ingredientsHeader.text = "Ingredients"
        ingredientsHeader.setTypeface(font, Typeface.BOLD)
        ingredientsHeader.setTextColor(textColor)
        ingredientsHeader.textSize = 24F

        // Ingredients Relative Layout for all Ingredients
        val ingredientsRelLayout = RelativeLayout(context)
        ingredientsRelLayout.id = View.generateViewId()

        val ingredientsRelParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        ingredientsRelLayout.layoutParams = ingredientsRelParams



        // Instructions Header
        val instructionsHeader = TextView(context)
        instructionsHeader.id = View.generateViewId()

        val instructionsHeaderParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        instructionsHeader.layoutParams = instructionsHeaderParams
        instructionsHeader.setPadding(0, timeLayoutPad, 0, 0)

        instructionsHeader.text = "Instructions"
        instructionsHeader.setTypeface(font, Typeface.BOLD)
        instructionsHeader.setTextColor(textColor)
        instructionsHeader.textSize = 24F

        // Adding elements into top of cardview
        relativeTopCardView.addView(titleTextView)
        relativeTopCardView.addView(downArrowImageView)
        relativeTopCardView.addView(upArrowImageView)
        relativeTopCardView.addView(dummy1)
        relativeTopCardView.addView(dummy2)
        relativeTopCardView.addView(ratingBarView)

        // Adding layouts into cardview
        cardLinearLayout.addView(relativeTopCardView)
        cardLinearLayout.addView(innerLayout)
        cardView.addView(cardLinearLayout)

        // Adding Elements into internal card unfolded section
        prepLayout.addView(prepText)
        prepInputText.addView(prepEditText)
        prepLayout.addView(prepInputText)

        cookLayout.addView(cookText)
        cookInputText.addView(cookEditText)
        cookLayout.addView(cookInputText)

        servesLayout.addView(servesText)
        servesInputText.addView(servesEditText)
        servesLayout.addView(servesInputText)

        timeLayout.addView(prepLayout)
        timeLayout.addView(cookLayout)
        timeLayout.addView(servesLayout)

        innerLayout.addView(timeLayout)
        innerLayout.addView(ingredientsHeader)
        innerLayout.addView(instructionsHeader)

        // Expansion onClickListener
        cardView.setOnClickListener(){
            if (innerLayout.visibility == View.GONE) {
                innerLayout.visibility = View.VISIBLE
            } else {
                innerLayout.visibility = View.GONE
            }
            if (downArrowImageView.visibility == View.VISIBLE) {
                downArrowImageView.visibility = View.GONE
                upArrowImageView.visibility = View.VISIBLE
            } else {
                downArrowImageView.visibility = View.VISIBLE
                upArrowImageView.visibility = View.GONE
            }
        }

        return cardView
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
}