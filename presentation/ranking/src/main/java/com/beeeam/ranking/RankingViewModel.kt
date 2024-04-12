package com.beeeam.ranking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beeeam.base.BaseViewModel
import com.beeeam.util.simpleHttpErrorCheck
import com.shypolarbear.domain.model.ranking.Ranking
import com.shypolarbear.domain.model.ranking.RankingScroll
import com.shypolarbear.domain.model.ranking.TotalRanking
import com.shypolarbear.domain.usecase.ranking.LoadMyRankingUseCase
import com.shypolarbear.domain.usecase.ranking.LoadTotalRankingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val loadMyRankingUseCase: LoadMyRankingUseCase,
    private val loadTotalRankingUseCase: LoadTotalRankingUseCase,
) : BaseViewModel() {

    private val _myRanking = MutableLiveData<Ranking>()
    val myRanking: LiveData<Ranking> = _myRanking
    private val _totalRankingResponse = MutableLiveData<TotalRanking>()
    val totalRankingResponse: LiveData<TotalRanking> = _totalRankingResponse

    fun loadRankingData() {
        loadMyRanking()
        loadTotalRanking()
    }

    private fun loadMyRanking() {
        viewModelScope.launch {
            loadMyRankingUseCase().onSuccess { response ->
                _myRanking.value = response.data
            }
                .onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
    }

    private fun loadTotalRanking(lastCommentId: Int? = null): Job {
        val loadJob = viewModelScope.launch {
            loadTotalRankingUseCase(RankingScroll(lastCommentId, limit = null))
                .onSuccess { response ->
                    _totalRankingResponse.value = response.data
                }
                .onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
        return loadJob
    }

    fun loadMoreRanking() {
        viewModelScope.launch {
            if (!_totalRankingResponse.value!!.last) {
                loadTotalRanking(_totalRankingResponse.value!!.content.last().rankingId).join()
            } else {
                // isLast = true
            }
        }
    }
}
