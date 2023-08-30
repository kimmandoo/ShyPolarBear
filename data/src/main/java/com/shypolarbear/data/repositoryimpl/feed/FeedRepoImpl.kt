package com.shypolarbear.data.repositoryimpl.feed

import com.shypolarbear.data.api.feed.FeedApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.model.feed.feedChange.FeedChangeResponse
import com.shypolarbear.domain.model.feed.feedChange.WriteFeedForm
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.domain.model.feed.feedDetail.FeedDetail
import com.shypolarbear.domain.model.feed.feedLike.FeedLikeResponse
import com.shypolarbear.domain.repository.feed.FeedRepo
import javax.inject.Inject

class FeedRepoImpl @Inject constructor(
    private val api: FeedApi
): FeedRepo {
    override suspend fun getFeedTotalData(): Result<FeedTotal> {
        return try {
            val response = api.getFeedTotal()
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
                }
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFeedDetailData(feedId: Int): Result<FeedDetail> {
        return try {
            val response = api.getFeedDetail(feedId)
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
                }
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFeedCommentData(feedId: Int): Result<FeedComment> {
        return try {
            val response = api.getFeedComment(feedId)
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
                }
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun requestChangePostData(
        feedId: Int,
        content: String,
        feedImages: List<String>?,
        title: String
    ): Result<FeedChangeResponse> {
        return try {
            val response = api.requestChangePost(
                feedId,
                WriteFeedForm(
                    content = content,
                    feedId = feedId,
                    feedImages = listOf("NULL"),  // TODO("이미지 작업 완료되면 수정할 예정")
                    title = title
                )
            )
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
                }
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteFeedData(feedId: Int): Result<FeedChangeResponse> {
        return try {
            val response = api.deleteFeed(feedId)
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
                }
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun writeFeedData(
        title: String,
        content: String,
        feedImages: List<String>?
    ): Result<FeedChangeResponse> {
        return try {
            val response = api.writeFeed(
                WriteFeedForm(
                    title = title,
                    content = content,
                    feedImages = listOf("NULL"),  // TODO("이미지 작업 완료되면 수정할 예정"
                )
            )
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
                }
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun requestLikeFeedData(feedId: Int): Result<FeedLikeResponse> {
        return try {
            val response = api.likeFeed(feedId)
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
                }
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}