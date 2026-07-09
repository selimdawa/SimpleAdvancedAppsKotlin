package com.flatcode.simpleadvancedapps.meals.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.utils.THEME
import com.flatcode.simpleadvancedapps.databinding.ActivityMealBinding
import com.flatcode.simpleadvancedapps.meals.db.MealDatabase
import com.flatcode.simpleadvancedapps.meals.fragments.HomeFragment
import com.flatcode.simpleadvancedapps.meals.mvvm.MealViewModel
import com.flatcode.simpleadvancedapps.meals.mvvm.MealViewModelFactory
import com.flatcode.simpleadvancedapps.meals.pojo.Meal

class MealActivity : AppCompatActivity() {

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private var _binding: ActivityMealBinding? = null
    private val binding get() = _binding!!
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubeLink: String
    private val context = this

    private var mealToSave: Meal? = null
    private var isMealFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        _binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInViews()
        loadingCase()

        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()
        observeFavoriteStatus()
        onYoutubeImageClick()
        onFavoriteClick()
    }

    private fun observeFavoriteStatus() {
        mealMvvm.observeFavoritesMealsLiveData().observe(this) { favoritesList ->
            isMealFavorite = favoritesList.any { it.idMeal == mealId }
            binding.btnAddToFav.setImageResource(
                if (isMealFavorite) R.drawable.ic_heart_selected else R.drawable.ic_heart_unselected
            )
        }
    }

    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener {
            mealToSave?.let { meal ->
                if (isMealFavorite) {
                    mealMvvm.deleteMeal(meal)
                    Toast.makeText(this, getString(R.string.meal_removed), Toast.LENGTH_SHORT).show()
                } else {
                    mealMvvm.insertMeal(meal)
                    Toast.makeText(this, getString(R.string.meal_saved), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            if (::youtubeLink.isInitialized && youtubeLink.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, youtubeLink.toUri())
                startActivity(intent)
            }
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this) { value ->
            onResponseCase()
            mealToSave = value

            with(binding) {
                tvCategory.text = getString(R.string.category_placeholder, value?.strCategory.orEmpty())
                tvArea.text = getString(R.string.area_placeholder, value?.strArea.orEmpty())
                tvInstructionsSteps.text = value?.strInstructions.orEmpty()
            }

            youtubeLink = value?.strYoutube.toString()
        }
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        with(binding.collapsingToolbar) {
            title = mealName
            setCollapsedTitleTextColor(ContextCompat.getColor(this@MealActivity, R.color.white))
            setExpandedTitleColor(ContextCompat.getColor(this@MealActivity, R.color.white))
        }
    }

    private fun getMealInformationFromIntent() {
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID).orEmpty()
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME).orEmpty()
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB).orEmpty()
    }

    private fun loadingCase() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            btnAddToFav.visibility = View.INVISIBLE
            tvArea.visibility = View.INVISIBLE
            tvCategory.visibility = View.INVISIBLE
            tvInstructions.visibility = View.INVISIBLE
            imgYoutube.visibility = View.INVISIBLE
        }
    }

    private fun onResponseCase() {
        with(binding) {
            progressBar.visibility = View.INVISIBLE
            btnAddToFav.visibility = View.VISIBLE
            tvArea.visibility = View.VISIBLE
            tvCategory.visibility = View.VISIBLE
            tvInstructions.visibility = View.VISIBLE
            imgYoutube.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}