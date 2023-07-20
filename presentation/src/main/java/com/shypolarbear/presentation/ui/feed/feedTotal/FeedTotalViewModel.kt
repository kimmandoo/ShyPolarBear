package com.shypolarbear.presentation.ui.feed.feedTotal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.feed.FeedPost
import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.usecase.feed.FeedTotalUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedTotalViewModel @Inject constructor (
    private val feedTotalUseCase: FeedTotalUseCase
): BaseViewModel() {

    private val _feedPost = MutableLiveData<List<FeedPost>>()
    val feedPost: LiveData<List<FeedPost>> = _feedPost

    private val _feed = MutableLiveData<List<Feed>>()
    val feed: LiveData<List<Feed>> = _feed

    fun loadFeedPost() {
        _feedPost.value = mutableListOf(
            // 테스트 데이터
            FeedPost("1", "my", "other"),
            FeedPost("1", "other", "other"),
            FeedPost("1", "my", "other"),
            FeedPost("1", "other", "my")
        )
    }

    fun loadFeedTotalData() {
        viewModelScope.launch {
            val testData = feedTotalUseCase.loadFeedTotalData()

            testData
                .onSuccess {
                    Timber.d(it.data.feeds.toString())
                    _feed.value = it.data.feeds
                }
                .onFailure {

                }

        }
    }
}