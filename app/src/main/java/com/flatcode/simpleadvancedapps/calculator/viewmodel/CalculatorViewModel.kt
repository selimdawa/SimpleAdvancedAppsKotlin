package com.flatcode.simpleadvancedapps.calculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.calculator.data.CalculatorDao
import com.flatcode.simpleadvancedapps.calculator.data.CalculatorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(private val calculatorDao: CalculatorDao) :
    ViewModel() {

    private val _expression = MutableLiveData("")
    val expression: LiveData<String> get() = _expression

    private val _result = MutableLiveData("")
    val result: LiveData<String> get() = _result

    val historyList: LiveData<List<CalculatorEntity>> = calculatorDao.getAllHistory().asLiveData()

    fun appendValue(value: String) {
        _expression.value = (_expression.value ?: "") + value
    }

    fun clearAll() {
        _expression.value = ""
        _result.value = ""
    }

    fun deleteLast() {
        val currentExp = _expression.value ?: ""
        if (currentExp.isNotEmpty()) {
            _expression.value = currentExp.dropLast(1)
        }
    }

    fun setResultValue(evaluatedResult: String) {
        _result.value = evaluatedResult
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