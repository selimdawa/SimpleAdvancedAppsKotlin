package com.flatcode.simpleadvancedapps.pop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.pop.model.PopItem
import com.flatcode.simpleadvancedapps.pop.repository.FunkoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FunkoViewModel @Inject constructor(private val funkoRepository: FunkoRepository) :
    ViewModel() {

    val pops: StateFlow<List<PopItem>> = funkoRepository.pops
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _pop = MutableStateFlow<PopItem?>(null)
    val pop: StateFlow<PopItem?> = _pop

    private val _filterText = MutableStateFlow("")
    val filterText: StateFlow<String> = _filterText

    private val _isListFiltered = MutableStateFlow(false)
    val isListFiltered: StateFlow<Boolean> = _isListFiltered

    fun setFilterText(text: String) {
        _filterText.value = text
        Timber.d("State updated: filterText = $text")
    }

    fun fetchData() {
        viewModelScope.launch {
            funkoRepository.refreshPops()
        }
    }

    fun filter() {
        val currentTextLength = filterText.value.length
        _isListFiltered.value = currentTextLength > 1
        Timber.d("State updated: isListFiltered = ${currentTextLength > 1}")
    }

    fun getFilteredList(text: String): List<PopItem> {
        val query = text.lowercase()

        return pops.value.filter { pop ->
            pop.name.lowercase().contains(query) || pop.series.lowercase().contains(query)
        }
    }

    fun onPopClicked(clickedPop: PopItem) {
        _pop.value = clickedPop
        Timber.d("State updated: pop = $clickedPop")
    }
}