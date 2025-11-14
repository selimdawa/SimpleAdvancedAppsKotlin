package com.flatcode.simpleadvancedapps.news.ui.fragments.toparticles

import com.flatcode.simpleadvancedapps.news.base.BaseViewModel
import com.flatcode.simpleadvancedapps.news.data.repositories.EverythingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopArticlesViewModel @Inject constructor(private val repository: EverythingRepository) :
    BaseViewModel() {

    fun fetchTopArticles() = repository.fetchTopArticles()
}