package com.flatcode.simpleadvancedapps.dogs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.dogs.data.DogRepository
import com.flatcode.simpleadvancedapps.dogs.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface DogUiState {
    data object Start : DogUiState
    data object Loading : DogUiState
    data class Success(val photos: List<String>) : DogUiState
    data object Error : DogUiState
}

@HiltViewModel
class DogViewModel @Inject constructor(
    private val repository: DogRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DogUiState>(DogUiState.Start)
    val uiState: StateFlow<DogUiState> = _uiState.asStateFlow()

    private val _breedsList = MutableStateFlow<List<String>>(emptyList())
    val breedsList: StateFlow<List<String>> = _breedsList.asStateFlow()

    fun setBreedsList(list: Array<String>) {
        _breedsList.value = list.toList()
    }

    fun getDogPhotosList(breed: String) {
        viewModelScope.launch {
            _uiState.update { DogUiState.Loading }
            try {
                repository.getDogsByBreed(breed, networkHelper.isNetworkConnected())
                    .collect { photos ->
                        if (photos.isNotEmpty()) {
                            _uiState.update { DogUiState.Success(photos) }
                        } else {
                            _uiState.update { DogUiState.Error }
                        }
                    }
            } catch (_: Exception) {
                _uiState.update { DogUiState.Error }
            }
        }
    }

    fun retryLastBreed(breed: String?) {
        breed?.let { getDogPhotosList(it) }
    }
}