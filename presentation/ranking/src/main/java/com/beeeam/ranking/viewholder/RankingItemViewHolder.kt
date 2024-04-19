package com.beeeam.ranking.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.beeeam.ranking.databinding.ItemRankingBinding
import com.beeeam.util.Const
import com.beeeam.util.GlideUtil
import com.shypolarbear.domain.model.ranking.Ranking

class RankingItemViewHolder(private val binding: ItemRankingBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(totalRanking: Ranking) {
        binding.apply {
            tvRankingRank.text = if (totalRanking.rank == Const.UNRANKED) {
                binding.root.context.getString(com.beeeam.designsystem.R.string.ranking_null)
            } else {
                totalRanking.rank.toString()
            }
            GlideUtil.loadCircleImage(
                binding.root.context,
                totalRanking.profileImage,
                ivRankingProfile,
                com.beeeam.designsystem.R.drawable.ic_user_base_profile,
            )
            tvRankingName.text = totalRanking.nickName
            tvRankingPoint.text =
                binding.root.context.getString(com.beeeam.designsystem.R.string.ranking_point_value, totalRanking.point)
            tvRankingWiningRate.text = binding.root.context.getString(
                com.beeeam.designsystem.R.string.ranking_possible_value,
                totalRanking.winningPercent,
            )
        }
    }
}