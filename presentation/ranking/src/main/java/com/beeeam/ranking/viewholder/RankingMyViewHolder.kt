package com.beeeam.ranking.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.beeeam.ranking.databinding.ItemRankingMyBinding
import com.beeeam.util.Const.UNRANKED
import com.beeeam.util.GlideUtil
import com.shypolarbear.domain.model.ranking.Ranking

class RankingMyViewHolder(private val binding: ItemRankingMyBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Ranking) {
        binding.apply {
            executePendingBindings()
            tvRankingRank.text = if (item.rank == UNRANKED) {
                "-"
            } else {
                item.rank.toString()
            }
            GlideUtil.loadCircleImage(
                itemView.context,
                item.profileImage,
                ivRankingProfile,
                com.beeeam.designsystem.R.drawable.ic_user_base_profile,
            )
            item.apply {
                tvRankingName.text = nickName
                tvRankingPoint.text = "${point} P"
                tvRankingWiningRate.text = "${winningPercent}%"
            }
        }

    }
}