package com.beeeam.ranking.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.beeeam.ranking.databinding.ItemRankingHeaderBinding
import com.beeeam.util.Const
import com.beeeam.util.GlideUtil

class RankingHeaderViewHolder(private val binding: ItemRankingHeaderBinding): RecyclerView.ViewHolder(binding.root) {
    val sampleProductUrl = "https://media.bunjang.co.kr/product/234579740_1_1693213317_w360.jpg"

    fun bind() {
        binding.apply {
            executePendingBindings()
            GlideUtil.loadImage(itemView.context, sampleProductUrl, ivRankingSampleProduct)
        }

    }
}