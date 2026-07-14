package com.flatcode.simpleadvancedapps.pop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.pop.model.PopItem
import com.flatcode.simpleadvancedapps.pop.repository.FunkoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FunkoViewModel @Inject constructor(private val funkoRepository: FunkoRepository) :
    ViewModel() {

    val pops: LiveData<List<PopItem>> = funkoRepository.pops

    private val _pop = MutableLiveData<PopItem>()
    val pop: LiveData<PopItem> get() = _pop

    val filterText = MutableLiveData("")

    private val _isListFiltered = MutableLiveData(false)
    val isListFiltered: LiveData<Boolean> get() = _isListFiltered

    fun fetchData() {
        viewModelScope.launch {
            funkoRepository.refreshPops()
        }
    }

    fun filter() {
        val currentTextLength = filterText.value?.length ?: 0
        _isListFiltered.value = currentTextLength > 1
    }

    fun getFilteredList(text: String): List<PopItem> {
        val currentPops = pops.value ?: return emptyList()
        val query = text.lowercase()

        return currentPops.filter { pop ->
            pop.name.lowercase().contains(query) || pop.series.lowercase().contains(query)
        }
    }

    fun onPopClicked(pop: PopItem) {
        _pop.value = pop
    }
}