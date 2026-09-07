package com.flatcode.simpleadvancedapps.dictionary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.dictionary.data.repository.DictionaryRepository
import com.flatcode.simpleadvancedapps.dictionary.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application, private val repository: DictionaryRepository
) : AndroidViewModel(application) {

    val uiState: StateFlow<UiState<String>>
        field = MutableStateFlow<UiState<String>>(UiState.Idle)

    val navigationEvent: SharedFlow<Unit>
        field = MutableSharedFlow<Unit>()

    fun searchWord(word: String) {
        if (word.isBlank()) return

        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val result = repository.getDefinition(word)
                uiState.value = UiState.Success(result)
                navigationEvent.emit(Unit)
            } catch (e: Exception) {
                uiState.value = UiState.Error(
                    e.message ?: getApplication<Application>().getString(R.string.unknown_error)
                )
            }
        }
    }
}
