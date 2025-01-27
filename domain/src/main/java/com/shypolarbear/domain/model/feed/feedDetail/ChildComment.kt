package com.shypolarbear.domain.model.feed.feedDetail

data class ChildComment(
    val authorNickname: String = "",
    val authorProfileImage: String = "",
    val commentId: Int = 0,
    val content: String = "",
    val createdDate: String = "",
    val isAuthor: Boolean = true,
    val isLike: Boolean = false,
    val likeCount: Int = 0,
    val isDeleted: Boolean = false
)