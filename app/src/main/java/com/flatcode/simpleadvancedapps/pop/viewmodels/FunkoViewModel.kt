package com.flatcode.simpleadvancedapps.pop.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.pop.models.PopItem
import com.flatcode.simpleadvancedapps.pop.repository.FunkoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FunkoViewModel(private val funkoRepository: FunkoRepository) : ViewModel() {

    private val _pops = MutableLiveData<MutableList<PopItem>>()
    val pops: LiveData<MutableList<PopItem>> get() = _pops

    private val _pop = MutableLiveData<PopItem>()
    val pop: LiveData<PopItem> get() = _pop

    val filterText = MutableLiveData("")

    private val _isListFiltered = MutableLiveData(false)
    val isListFiltered: LiveData<Boolean> get() = _isListFiltered

    fun fetchData(): LiveData<MutableList<PopItem>> {
        viewModelScope.launch(Dispatchers.IO) {
            _pops.postValue(funkoRepository.getFunkoPops())
        }
        return pops
    }

    fun filter() {
        val currentTextLength = filterText.value?.length ?: 0
        _isListFiltered.value = currentTextLength > 1
    }

    fun getFilteredList(text: String): List<PopItem> {
        val currentPops = _pops.value ?: return emptyList()
        val query = text.lowercase()

        return currentPops.filter { pop ->
            pop.name.lowercase().contains(query) || pop.series.lowercase().contains(query)
        }
    }

    fun onPopClicked(pop: PopItem) {
        _pop.value = pop
    }

    class FunkoViewModelFactory(private val funkoRepository: FunkoRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FunkoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FunkoViewModel(funkoRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}