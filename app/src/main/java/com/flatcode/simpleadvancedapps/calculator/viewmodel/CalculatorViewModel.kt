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

    val expression: LiveData<String>
        field = MutableLiveData("")

    val result: LiveData<String>
        field = MutableLiveData("")

    val historyList: LiveData<List<CalculatorEntity>> = calculatorDao.getAllHistory().asLiveData()

    fun appendValue(value: String) {
        expression.value = (expression.value ?: "") + value
    }

    fun clearAll() {
        expression.value = ""
        result.value = ""
    }

    fun deleteLast() {
        val currentExp = expression.value ?: ""
        if (currentExp.isNotEmpty()) {
            expression.value = currentExp.dropLast(1)
        }
    }

    fun setResultValue(evaluatedResult: String) {
        result.value = evaluatedResult
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