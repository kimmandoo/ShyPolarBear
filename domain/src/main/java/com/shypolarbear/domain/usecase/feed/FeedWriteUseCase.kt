package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.model.feed.feedChange.FeedChangeResponse
import com.shypolarbear.domain.repository.feed.FeedRepo

class FeedWriteUseCase(
    private val repo: FeedRepo
) {
    suspend fun requestWriteFeed(
        title: String,
        content: String,
        feedImages: List<String>?
    ): Result<FeedChangeResponse> {
        return repo.writeFeedData(title, content, feedImages)
    }
}