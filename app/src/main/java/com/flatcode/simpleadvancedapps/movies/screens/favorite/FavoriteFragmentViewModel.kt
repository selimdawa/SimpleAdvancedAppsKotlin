package com.flatcode.simpleadvancedapps.movies.screens.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simpleadvancedapps.Unit.DATA.REALIZATION
import com.flatcode.simpleadvancedapps.movies.models.MovieItemModel

class FavoriteFragmentViewModel : ViewModel() {

    fun getAllMovies(): LiveData<List<MovieItemModel>> {
        return REALIZATION.allMovies
    }
}