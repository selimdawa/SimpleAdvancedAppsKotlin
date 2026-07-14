package com.flatcode.simpleadvancedapps.crypto.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.crypto.db.entity.CoinDetailEntity
import com.flatcode.simpleadvancedapps.crypto.model.detail.DetailResponse
import com.flatcode.simpleadvancedapps.crypto.utils.NetworkResult
import com.flatcode.simpleadvancedapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository
) : ViewModel() {

    var symbol: String = ""
    var coinId: Int = 0

    private val _detailState =
        MutableStateFlow<NetworkResult<DetailResponse>>(NetworkResult.Loading())
    val detailState: StateFlow<NetworkResult<DetailResponse>> = _detailState.asStateFlow()

    private val _localDetail = MutableStateFlow<CoinDetailEntity?>(null)
    val localDetail: StateFlow<CoinDetailEntity?> = _localDetail.asStateFlow()

    private var dbJob: Job? = null

    fun getDetail(symbol: String, coinId: Int) {
        if (this.coinId == coinId && _localDetail.value != null) return

        this.symbol = symbol
        this.coinId = coinId

        _localDetail.value = null
        _detailState.value = NetworkResult.Loading()

        dbJob?.cancel()
        dbJob = viewModelScope.launch {
            repository.getDetailFromDb(coinId).collectLatest {
                _localDetail.value = it
            }
        }

        viewModelScope.launch {
            if (symbol.isEmpty()) {
                _detailState.value = NetworkResult.Error(false, "Symbol is missing")
                return@launch
            }
            _detailState.value = NetworkResult.Loading()
            val result = repository.getDetailFromApi(DATA.API_KEY_CRYPTO, symbol)
            _detailState.value = result

            if (result is NetworkResult.Success) {
                val coin = result.data.data?.get(symbol)?.firstOrNull()
                coin?.let {
                    repository.saveDetailToDb(
                        CoinDetailEntity(
                            id = it.id ?: coinId,
                            name = it.name ?: "",
                            symbol = it.symbol ?: "",
                            description = it.description ?: "",
                            logo = it.logo ?: ""
                        )
                    )
                }
            }
        }
    }
}