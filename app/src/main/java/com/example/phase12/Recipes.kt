package com.example.phase12

import android.animation.LayoutTransition
import android.widget.CheckBox
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.PersistableBundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.phase12.databinding.RecipesBinding
import androidx.cardview.widget.CardView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.SearchView
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.node.getOrAddAdapter
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phase12.ui.theme.AppBar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray
import java.io.BufferedReader
import java.io.FileNotFoundException

class Recipes : AppBar() {
    private lateinit var binding: RecipesBinding
    private lateinit var parentLayout: LinearLayout
    private lateinit var cardLayout : LinearLayout
    private lateinit var navBar: BottomAppBar
    private lateinit var ingredientViews : MutableMap<String, Int>
    private lateinit var instructionViews : MutableMap<String, Int>
    private lateinit var ingredientsRelLayout : RelativeLayout
    private lateinit var instructionsRelLayout : RelativeLayout
    private lateinit var cardViews : MutableMap<String, Int>

    class Recipe (title : String = "RecipeTitle", skillLevel : Float = 1F, prepTime: Int = 30,
                  cookTime : Int = 30, serves : Int = 4, diet : MutableList<String> = mutableListOf("vegan"),
                  ingredients: MutableList<String> = mutableListOf("exampleIngredient"),
                  instructions : MutableList<String> = mutableListOf("exampleInstruction")
    ) {
        val title : String
        val skillLevel : Float
        val prepTime : Int
        val cookTime : Int
        val serves : Int
        val diet : MutableList<String>
        var ingredients : MutableList<String>
        var instructions : MutableList<String>

        init {
            this.title = title
            this.skillLevel = skillLevel
            this.prepTime = prepTime
            this.cookTime = cookTime
            this.serves = serves
            this.diet = diet
            this.ingredients = ingredients
            this.instructions = instructions
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipes)
        binding = RecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBar()

        navBar = findViewById<BottomAppBar>(R.id.bottomAppBar)

        parentLayout = findViewById<LinearLayout>(R.id.recipes)
        cardLayout = findViewById<LinearLayout>(R.id.cardListLayout)

        val recipes = parse(readJson())
        val recipeTitles = mutableListOf<String>()

        ingredientViews = HashMap<String, Int>()
        instructionViews = HashMap<String, Int>()

        for (recipe in recipes) {
            recipeTitles.add(recipe.title)
            val newCard = createRecipeCard(
                this, recipe.title, recipe.skillLevel,
                recipe.prepTime, recipe.cookTime, recipe.serves, recipe.diet,
                recipe.ingredients, recipe.instructions
            )
            cardLayout.addView(newCard)
        }

        // Dietary Filter
        val dairyFree = findViewById<ImageButton>(R.id.dairy_free)
        val glutenFree = findViewById<ImageButton>(R.id.gluten_free)
        val halal = findViewById<ImageButton>(R.id.halal)
        val kosher = findViewById<ImageButton>(R.id.kosher)
        val lowSodium = findViewById<ImageButton>(R.id.low_sodium)
        val nutFree = findViewById<ImageButton>(R.id.nut_free)
        val sugarFree = findViewById<ImageButton>(R.id.sugar_free)
        val vegan = findViewById<ImageButton>(R.id.vegan_v)

//        var filtered = recipes
//        dairyFree.setOnClickListener() {
//            filtered = filterRecipes("dairy_free", recipes)
//
//        }
//        glutenFree.setOnClickListener() {
//            filtered = filterRecipes("gluten_free", recipes)
//        }
//        halal.setOnClickListener() {
//            filtered = filterRecipes("halal", recipes)
//        }
//        kosher.setOnClickListener() {
//            filtered = filterRecipes("kosher", recipes)
//        }
//        lowSodium.setOnClickListener() {
//            filtered = filterRecipes("low_sodium", recipes)
//        }
//        nutFree.setOnClickListener() {
//            filtered = filterRecipes("nut_free", recipes)
//        }
//        sugarFree.setOnClickListener() {
//            filtered = filterRecipes("sugar_free", recipes)
//        }
//        vegan.setOnClickListener() {
//            filtered = filterRecipes("vegan", recipes)
//        }

        binding.fabRecipeList.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = LayoutInflater.from(this)
            val view = inflater.inflate(R.layout.add_recipe, null)

            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, recipeTitles)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
            val addIngredientLayout = view.findViewById<LinearLayout>(R.id.add_ingredient)
            val addInstructionLayout = view.findViewById<LinearLayout>(R.id.add_instruction)
            val addRecipeLayout = view.findViewById<LinearLayout>(R.id.add_recipe)

            val ingredientSpinner = view.findViewById<Spinner>(R.id.ingredient_recipe)
            ingredientSpinner.adapter = adapter

            val instructionSpinner = view.findViewById<Spinner>(R.id.instruction_recipe)
            instructionSpinner.adapter = adapter

            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_item_1 -> {
                        addIngredientLayout.visibility = View.VISIBLE
                        addInstructionLayout.visibility = View.GONE
                        addRecipeLayout.visibility = View.GONE
                    }
                    R.id.radio_item_2 -> {
                        addIngredientLayout.visibility = View.GONE
                        addInstructionLayout.visibility = View.VISIBLE
                        addRecipeLayout.visibility = View.GONE
                    }
                    R.id.radio_list_3 -> {
                        addIngredientLayout.visibility = View.GONE
                        addInstructionLayout.visibility = View.GONE
                        addRecipeLayout.visibility = View.VISIBLE
//                        addNewRecipe()


                    }
                }
            }
            builder.setView(view)
            builder.setTitle("What would you like to add?")

            builder.setPositiveButton("Add") { dialog, which ->
                when (radioGroup.checkedRadioButtonId) {
                    R.id.radio_item_1 -> {
                        val selectedIngredientRecipe = ingredientSpinner.selectedItemPosition
                        val selectedRecipe : Recipe = recipes[selectedIngredientRecipe]
                        val newIngredient = view.findViewById<EditText>(R.id.ingredient).text.toString()
                        selectedRecipe.ingredients.add(newIngredient)

                        val newIngredientLayout = RelativeLayout(this@Recipes)
                        newIngredientLayout.id = View.generateViewId()

                        val newIngredientLayoutParams = RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                        )
                        newIngredientLayout.layoutParams = newIngredientLayoutParams

                        val newCheckBox = CheckBox(this@Recipes)
                        newCheckBox.id = View.generateViewId()
                        val checkBoxLayoutParams = RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                        )
                        checkBoxLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
                        newCheckBox.layoutParams = checkBoxLayoutParams
                        newCheckBox.text = newIngredient
                        newCheckBox.setTextColor(Color.parseColor("#000000"))
                        newCheckBox.textSize = 20F

                        newIngredientLayout.addView(newCheckBox)

//                        val layoutToAdd = findViewById<RelativeLayout>(ingredientViews[selectedRecipe])
                        ingredientsRelLayout.addView(newIngredientLayout)
                        ingredientsRelLayout.invalidate()
                        //(ingredientSpinner.adapter as? ArrayAdapter<String>)?.notifyDataSetChanged()
                    }
                    R.id.radio_item_2 -> {
                        val selectedInstructionRecipeItem = instructionSpinner.selectedItem
                        val selectedInstructionRecipeIndex = recipes.indexOf(selectedInstructionRecipeItem)
                        val selectedInstructionRecipe : Recipe = recipes[selectedInstructionRecipeIndex]
                        val newInstruction = view.findViewById<EditText>(R.id.instruction).text.toString()
                        selectedInstructionRecipe.instructions.add(newInstruction)
                        (instructionSpinner.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                    }
                    R.id.radio_list_3 -> {
                        val recipeView = view.findViewById<LinearLayout>(R.id.recipeBuilder)

                        if (recipeView != null) {
                            val title =
                                recipeView.findViewById<EditText>(R.id.recipeTitle).text.toString()
                            val skillLevel =
                                recipeView.findViewById<EditText>(R.id.skillLevel).text.toString()
                                    .toFloat()
                            val prepTime =
                                recipeView.findViewById<EditText>(R.id.prepTime).text.toString()
                                    .toInt()
                            val cookTime =
                                recipeView.findViewById<EditText>(R.id.cookTime).text.toString()
                                    .toInt()
                            val serves =
                                recipeView.findViewById<EditText>(R.id.serves).text.toString()
                                    .toInt()
                            val diet = mutableListOf<String>()
                            val ingredients = mutableListOf<String>()
                            val instructions = mutableListOf<String>()

                            val cardView = createRecipeCard(
                                this@Recipes,
                                title,
                                skillLevel,
                                prepTime,
                                cookTime,
                                serves,
                                diet,
                                ingredients,
                                instructions
                            )

                            cardLayout.addView(cardView)
                        }

                    }
                    else -> { }
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }

            builder.setCancelable(true)
            builder.create().show()
        }
    }

    private fun addNewRecipe() {

    }
    private fun filterRecipes(diet : String, recipes : MutableList<Recipe>) : MutableList<Recipe> {
        val filteredRecipes = mutableListOf<Recipe>()
        for (recipe in recipes) {
            if (recipe.diet.contains(diet)) {
                filteredRecipes.add(recipe)
            }
        }
        return filteredRecipes
    }
    private fun createRecipeCard(context: Context, title: String, ratingStars: Float, prepTime : Int,
                                 cookTime: Int, serves: Int, diet: MutableList<String>, ingredients : MutableList<String>,
                                 instructions : MutableList<String>): CardView {
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

        dummy1.setOnClickListener(){
            dietaryRestrictions(dummy1)
        }

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
        dummy2.setOnClickListener(){
            dietaryRestrictions(dummy2)
        }

        // Creating Rating Bar
        val ratingBarView = AppCompatRatingBar(context)
        ratingBarView.numStars = 3
        ratingBarView.id = View.generateViewId()
        ratingBarView.rating = ratingStars

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

        // Ingredients Sub Card Relative Layout
        val ingredientsCardRelLayout = RelativeLayout(context)
        ingredientsCardRelLayout.id = View.generateViewId()
        ingredientViews[title] = ingredientsCardRelLayout.id

        ingredientsCardRelLayout.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        )

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
        ingredientsRelLayout = RelativeLayout(context)
        ingredientsRelLayout.id = View.generateViewId()

        val ingredientsRelParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        ingredientsRelLayout.layoutParams = ingredientsRelParams

        var prevId = -Int.MAX_VALUE
        for (ingredient in ingredients) {
            val newItem = RelativeLayout(context)
            newItem.id = View.generateViewId()

            val newItemParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            if (prevId > 0) {
                newItemParams.addRule(RelativeLayout.BELOW, prevId)
            }
            newItem.layoutParams = newItemParams

            val checkItem = CheckBox(context)
            checkItem.id = View.generateViewId()

            val checkParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)

            checkParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
            checkItem.layoutParams = checkParams
            checkItem.gravity = Gravity.CENTER_VERTICAL
            checkItem.setPadding(0,context.resources.getDimensionPixelSize(R.dimen.checkEditPadding), 0, 0)
            checkItem.text = ingredient
            checkItem.setTextColor(textColor)
            checkItem.textSize = 20F

            val plusImage = R.drawable.ic_plus_24
            val newButton = Button(context)
            newButton.id = View.generateViewId()

            val buttonSize = context.resources.getDimensionPixelSize(R.dimen.plusSize)
            val buttonParams = RelativeLayout.LayoutParams(buttonSize, buttonSize)
            buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
            newButton.layoutParams = buttonParams

            newButton.setBackgroundResource(plusImage)

            newItem.addView(checkItem)
            newItem.addView(newButton)

//            newButton.setOnClickListener() {
//
//            }

//            val checkEditText = EditText(context)
//
//            val checkEditParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT)
//
//            checkEditParams.addRule(RelativeLayout.ALIGN_END, checkItem.id)
//            checkEditParams.leftMargin = context.resources.getDimensionPixelSize(R.dimen.checkText)
//            checkItem.layoutParams = checkParams
//
//            checkEditText.setText(ingredient)
//            checkEditText.setTextColor(textColor)
//            checkEditText.textSize = 20F
//            checkEditText.gravity = Gravity.CENTER_VERTICAL
            //checkEditText.setPadding(context.resources.getDimensionPixelSize(R.dimen.checkText),0,0, 0)
//
//            newItem.addView(checkItem)
//            newItem.addView(checkEditText)

            ingredientsRelLayout.addView(newItem)
            prevId = newItem.id
        }

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

        // Instructions Relative Layout for all Instructions
        instructionsRelLayout = RelativeLayout(context)
        instructionsRelLayout.id = View.generateViewId()
        instructionViews[title] = instructionsRelLayout.id

        val instructionsRelParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        instructionsRelLayout.layoutParams = instructionsRelParams

        var prevInstructionId = -Int.MAX_VALUE
        for (instruction in instructions) {
            val newItem = RelativeLayout(context)
            newItem.id = View.generateViewId()

            val newItemParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            newItemParams.topMargin = context.resources.getDimensionPixelSize(R.dimen.itemPad)
            if (prevInstructionId > 0) {
                newItemParams.addRule(RelativeLayout.BELOW, prevInstructionId)
            }
            newItem.layoutParams = newItemParams

            val checkItem = CheckBox(context)
            checkItem.id = View.generateViewId()

            val checkParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)

            checkParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
            checkItem.layoutParams = checkParams

            checkItem.text = instruction
            checkItem.setTextColor(textColor)
            checkItem.textSize = 20F

            newItem.addView(checkItem)
            instructionsRelLayout.addView(newItem)
            prevInstructionId = newItem.id
        }

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
        innerLayout.addView(ingredientsRelLayout)
        innerLayout.addView(instructionsHeader)
        innerLayout.addView(instructionsRelLayout)

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

    // From Alex's Grocery List
    private fun readJson(): JSONArray {
        return JSONArray(
                // JSON reading stolen from this https://www.youtube.com/watch?v=B9jrhLyRwBs
                applicationContext.resources.openRawResource(R.raw.recipes).bufferedReader()
                    .use<BufferedReader, String> { it.readText() })
    }

    // From Alex's Grocery List
    private fun parse(jsonArray: JSONArray) : MutableList<Recipes.Recipe>  {
        val recipes = mutableListOf<Recipes.Recipe>()

        for (i in 0 until jsonArray.length()) {
            val curr = jsonArray.getJSONObject(i)
            val title = curr.getString("name")
            val skillLevel = curr.getDouble("rating").toFloat()
            val prepTime = curr.getInt("prepTime")
            val cookTime = curr.getInt("cookTime")
            val serves = curr.getInt("servings")

            val dietJSON = curr.getJSONArray("diet")
            val diet = MutableList(dietJSON.length()) {
                dietJSON.getString(it)
            }

            val ingredientsJSON = curr.getJSONArray("ingredients")
            val ingredients = MutableList(ingredientsJSON.length()) {
                ingredientsJSON.getString(it)
            }

            val instructionsJSON = curr.getJSONArray("instructions")
            val instructions = MutableList(instructionsJSON.length()) {
                instructionsJSON.getString(it)
            }

            val newRecipe = Recipe(title, skillLevel, prepTime, cookTime,
                serves, diet, ingredients, instructions)
            recipes.add(newRecipe)
        }
        return recipes
    }

    private fun dietaryRestrictions(currentIcon: ImageView) {
        val diets = arrayOf("Dairy Free", "Gluten Free", "Halal", "Kosher",
            "Low Sodium", "Nut Free", "Sugar Free", "Vegan")

        val icons = mapOf("Dairy Free" to R.mipmap.dairy_free_round, "Gluten Free" to R.mipmap.gluten_free_round,
            "Halal" to R.mipmap.halal_round, "Kosher" to R.mipmap.kosher_round,
            "Low Sodium" to R.mipmap.low_sodium_round, "Nut Free" to R.mipmap.nut_free_round,
            "Sugar Free" to R.mipmap.sugar_free_round, "Vegan" to R.mipmap.vegan_round)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Diet Label")
        builder.setItems(diets) { _, diet ->
            val newDiet = diets[diet]
            var drawableId = R.mipmap.dietary_plus_round
            if (icons.containsKey(newDiet)) {
                drawableId = icons[newDiet]!!
            }
            currentIcon.setImageDrawable(ContextCompat.getDrawable(this, drawableId))
        }
        builder.show()
    }
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
}