package com.beeeam.ranking.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.beeeam.ranking.databinding.ItemRankingHeaderBinding
import com.beeeam.util.Const
import com.beeeam.util.GlideUtil

class RankingHeaderViewHolder(private val binding: ItemRankingHeaderBinding): RecyclerView.ViewHolder(binding.root) {
    val sampleProductUrl = "https://media.bunjang.co.kr/product/234579740_1_1693213317_w360.jpg"

    fun bind(item: com.shypolarbear.domain.model.ranking.Ranking) {
        binding.apply {
            executePendingBindings()
            GlideUtil.loadImage(itemView.context, sampleProductUrl, ivRankingSampleProduct)
            tvRankingRank.text = if (item.rank == Const.UNRANKED) {
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
            tvRankingName.text = item.nickName
            tvRankingPoint.text = "${item.point}P"
            tvRankingWiningRate.text = "${item.winningPercent}%"
        }

    }
}