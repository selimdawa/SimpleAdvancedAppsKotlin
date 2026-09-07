package com.flatcode.simpleadvancedapps.dogs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.dogs.data.DogRepository
import com.flatcode.simpleadvancedapps.dogs.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    val uiState: StateFlow<DogUiState>
        field = MutableStateFlow<DogUiState>(DogUiState.Start)

    val breedsList: StateFlow<List<String>>
        field = MutableStateFlow<List<String>>(emptyList())

    fun setBreedsList(list: Array<String>) {
        breedsList.value = list.toList()
    }

    fun getDogPhotosList(breed: String) {
        viewModelScope.launch {
            if (uiState.value !is DogUiState.Success) {
                uiState.update { DogUiState.Loading }
            }

            try {
                repository.getDogsByBreed(breed, networkHelper.isNetworkConnected())
                    .collect { photos ->
                        uiState.update { DogUiState.Success(photos) }
                    }
            } catch (_: Exception) {
                if (uiState.value !is DogUiState.Success) {
                    uiState.update { DogUiState.Error }
                }
            }
        }
    }

    fun retryLastBreed(breed: String?) {
        breed?.let { getDogPhotosList(it) }
    }
}