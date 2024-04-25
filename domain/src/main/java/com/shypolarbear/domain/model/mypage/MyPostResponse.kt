package com.shypolarbear.domain.model.mypage

data class MyPostResponse(
    val code: Int,
    val data: MyPost,
    val message: String,
)

data class MyPost(
    val count: Int,
    val last: Boolean,
    val content: List<MyFeed>,
)

data class MyFeed(
    val feedId: Int = 0,
    val title: String = "",
    val feedImage: String = "",
)
