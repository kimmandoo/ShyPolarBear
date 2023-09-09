package com.shypolarbear.presentation.ui.feed.feedTotal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.usecase.feed.FeedDeleteUseCase
import com.shypolarbear.domain.usecase.feed.FeedDetailUseCase
import com.shypolarbear.domain.usecase.feed.FeedLikeUseCase
import com.shypolarbear.domain.usecase.feed.FeedTotalUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedTotalViewModel @Inject constructor (
    private val feedTotalUseCase: FeedTotalUseCase,
    private val feedDeleteUseCase: FeedDeleteUseCase,
    private val feedLikeUseCase: FeedLikeUseCase,
): BaseViewModel() {

    private val _feed = MutableLiveData<List<Feed>>()
    val feed: LiveData<List<Feed>> = _feed

    fun loadFeedTotalData(sort: String) {
        viewModelScope.launch {
            val feedData = feedTotalUseCase.loadFeedTotalData(sort)

            feedData
                .onSuccess {
                    val newDataList = it.data.content
                    val currentList = _feed.value ?: emptyList()
                    _feed.value = currentList + newDataList
                }
                .onFailure {

                }
        }
    }

    fun requestDeleteFeed(feedId: Int) {
        viewModelScope.launch {
            feedDeleteUseCase.requestDeleteFeed(feedId)
        }
    }

    fun clickFeedLikeBtn(isLiked: Boolean, likeCnt: Int, feedId: Int) {
        val currentFeed = _feed.value?: return
        val updatedFeed = currentFeed.map { feed ->

            if (feed.feedId == feedId)
                feed.copy(isLike = isLiked, likeCount = likeCnt)
            else
                feed
        }
        viewModelScope.launch {
            feedLikeUseCase.requestLikeFeed(feedId)
        }
        _feed.value = updatedFeed
    }

    fun clickFeedBestCommentLikeBtn(isLiked: Boolean, likeCnt: Int, feedId: Int) {
        val currentFeed = _feed.value?: return
        val updatedFeed = currentFeed.map { feed ->

            if (feed.feedId == feedId) {
                val updatedBestComment = feed.comment.copy(isLike = isLiked, likeCount = likeCnt)
                feed.copy(comment = updatedBestComment)
            }
            else
                feed
        }
        _feed.value = updatedFeed
    }

    fun removeFeedList(position: Int) {
        val feedList: MutableList<Feed> = mutableListOf()

        feedList.addAll(0, _feed.value!!)

        feedList.removeAt(position)
        _feed.value = feedList
    }

    fun clearFeedList() {
        val feedList: MutableList<Feed> = mutableListOf()

        _feed.value = feedList
    }
}