package com.shypolarbear.domain.model.ranking

data class Ranking(
    val rank: Int = 0,
    val profileImage: String = "",
    val nickName: String = "",
    val point: Int = 0,
    val winningPercent: Int = 0,
    val rankingId: Int = 0,
)

data class RankingScroll(
    val lastCommentId: Int?,
    val limit: Int?,
)
