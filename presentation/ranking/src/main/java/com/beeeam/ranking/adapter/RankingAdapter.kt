package com.beeeam.ranking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.ranking.databinding.ItemRankingBinding
import com.beeeam.ranking.databinding.ItemRankingHeaderBinding
import com.beeeam.ranking.databinding.ItemRankingMyBinding
import com.beeeam.ranking.viewholder.RankingHeaderViewHolder
import com.beeeam.ranking.viewholder.RankingItemViewHolder
import com.beeeam.ranking.viewholder.RankingMyViewHolder
import com.shypolarbear.domain.model.ranking.Ranking

class RankingAdapter : ListAdapter<Ranking, RecyclerView.ViewHolder>(RankingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            0 -> {
                RankingHeaderViewHolder(
                    ItemRankingHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                )
            }
            1 -> {
                RankingMyViewHolder(
                    ItemRankingMyBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                )
            }
            else -> {
                RankingItemViewHolder(
                    ItemRankingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is RankingItemViewHolder -> holder.bind(currentList[position])
            is RankingMyViewHolder -> holder.bind(currentList[position])
            is RankingHeaderViewHolder -> holder.bind()
        }
    }

    fun isHeader(position: Int) = false

    fun getHeaderView(list: RecyclerView, position: Int): View? {
        val lastPosition = if (position < currentList.size) position else currentList.size - 1
        for (pos in lastPosition downTo 0) {
            if (pos == 1) {
                val binding =
                    ItemRankingMyBinding.inflate(LayoutInflater.from(list.context), list, false)
                binding.apply {
                    tvRankingRank.text = currentList[pos].rank.toString()
                    tvRankingName.text = currentList[pos].nickName
                    tvRankingPoint.text =
                        list.context.getString(
                            com.beeeam.designsystem.R.string.ranking_point_value,
                            currentList[pos].point
                        )
                    tvRankingWiningRate.text =
                        list.context.getString(
                            com.beeeam.designsystem.R.string.ranking_possible_value,
                            currentList[pos].point
                        )
                    return root
                }
            }
        }
        return null
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> 0
            1 -> 1
            else -> 2
        }
    }
}

class RankingDiffCallback : DiffUtil.ItemCallback<Ranking>() {

    override fun areItemsTheSame(oldItem: Ranking, newItem: Ranking): Boolean {
        return oldItem.rankingId == newItem.rankingId
    }

    override fun areContentsTheSame(oldItem: Ranking, newItem: Ranking): Boolean {
        return oldItem == newItem
    }
}
