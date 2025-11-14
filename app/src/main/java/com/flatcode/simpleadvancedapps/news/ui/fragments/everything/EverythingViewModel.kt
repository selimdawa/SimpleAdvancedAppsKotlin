package com.flatcode.simpleadvancedapps.news.ui.fragments.everything

import com.flatcode.simpleadvancedapps.news.base.BaseViewModel
import com.flatcode.simpleadvancedapps.news.data.repositories.EverythingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EverythingViewModel @Inject constructor(private val repository: EverythingRepository) :
    BaseViewModel() {

    fun fetchEverything() = repository.fetchEverything()
}