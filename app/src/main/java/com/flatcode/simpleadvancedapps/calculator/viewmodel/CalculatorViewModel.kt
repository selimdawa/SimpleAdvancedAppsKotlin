package com.flatcode.simpleadvancedapps.calculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.calculator.data.CalculatorDao
import com.flatcode.simpleadvancedapps.calculator.data.CalculatorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(private val calculatorDao: CalculatorDao) :
    ViewModel() {

    val expression: StateFlow<String>
        field = MutableStateFlow("")

    val result: StateFlow<String>
        field = MutableStateFlow("")

    val historyList: StateFlow<List<CalculatorEntity>> = calculatorDao.getAllHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun appendValue(value: String) {
        val newValue = (expression.value) + value
        (expression as MutableStateFlow).value = newValue
        Timber.d("State updated: expression = $newValue")
    }

    fun clearAll() {
        (expression as MutableStateFlow).value = ""
        (result as MutableStateFlow).value = ""
        Timber.d("State updated: expression = , result = ")
    }

    fun deleteLast() {
        val currentExp = expression.value
        if (currentExp.isNotEmpty()) {
            val newValue = currentExp.dropLast(1)
            (expression as MutableStateFlow).value = newValue
            Timber.d("State updated: expression = $newValue")
        }
    }

    fun setResultValue(evaluatedResult: String) {
        (result as MutableStateFlow).value = evaluatedResult
        Timber.d("State updated: result = $evaluatedResult")
    }

    fun saveToHistory(exp: String, res: String) {
        viewModelScope.launch {
            if (exp.isNotEmpty() && res.isNotEmpty()) {
                calculatorDao.insertHistory(
                    CalculatorEntity(expression = exp, result = res)
                )
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            calculatorDao.clearHistory()
        }
    }
}