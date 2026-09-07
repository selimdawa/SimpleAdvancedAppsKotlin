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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository
) : ViewModel() {

    var symbol: String = ""
    var coinId: Int = 0

    val detailState: StateFlow<NetworkResult<DetailResponse>>
        field = MutableStateFlow<NetworkResult<DetailResponse>>(NetworkResult.Loading())

    val localDetail: StateFlow<CoinDetailEntity?>
        field = MutableStateFlow<CoinDetailEntity?>(null)

    private var dbJob: Job? = null

    fun getDetail(symbol: String, coinId: Int) {
        if (this.coinId == coinId && localDetail.value != null) return

        this.symbol = symbol
        this.coinId = coinId

        localDetail.value = null
        detailState.value = NetworkResult.Loading()

        dbJob?.cancel()
        dbJob = viewModelScope.launch {
            repository.getDetailFromDb(coinId).collectLatest {
                localDetail.value = it
            }
        }

        viewModelScope.launch {
            if (symbol.isEmpty()) {
                detailState.value = NetworkResult.Error(false, "Symbol is missing")
                return@launch
            }
            detailState.value = NetworkResult.Loading()
            val result = repository.getDetailFromApi(DATA.API_KEY_CRYPTO, symbol)
            detailState.value = result

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