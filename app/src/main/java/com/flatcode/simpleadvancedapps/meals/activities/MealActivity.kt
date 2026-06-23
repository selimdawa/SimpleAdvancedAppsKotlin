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
import com.flatcode.simpleadvancedapps.Unit.THEME
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
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubeLink: String
    private val context = this@MealActivity

    private var mealToSave: Meal? = null
    private var isMealFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
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
            if (isMealFavorite) {
                binding.btnAddToFav.setImageResource(R.drawable.ic_heart_selected)
            } else {
                binding.btnAddToFav.setImageResource(R.drawable.ic_heart_unselected)
            }
        }
    }

    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener {
            mealToSave?.let { meal ->
                if (isMealFavorite) {
                    mealMvvm.deleteMeal(meal)
                    Toast.makeText(this, getString(R.string.meal_removed), Toast.LENGTH_SHORT)
                        .show()
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

            binding.tvCategory.text =
                getString(R.string.category_placeholder, value?.strCategory.orEmpty())
            binding.tvArea.text = getString(R.string.area_placeholder, value?.strArea.orEmpty())
            binding.tvInstructionsSteps.text = value?.strInstructions.orEmpty()

            youtubeLink = value?.strYoutube.toString()
        }
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        binding.collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun getMealInformationFromIntent() {
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}