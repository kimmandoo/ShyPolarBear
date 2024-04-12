package com.beeeam.ranking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.ranking.databinding.ItemRankingHeaderBinding
import com.beeeam.ranking.viewholder.RankingHeaderViewHolder
import com.shypolarbear.domain.model.ranking.Ranking

class RankingMyAdapter : ListAdapter<Ranking, RecyclerView.ViewHolder>(RankingMyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RankingHeaderViewHolder(
            ItemRankingHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RankingHeaderViewHolder).bind(getItem(position))
    }
}

class RankingMyDiffCallback : DiffUtil.ItemCallback<Ranking>() {
    override fun areItemsTheSame(oldItem: Ranking, newItem: Ranking): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Ranking, newItem: Ranking): Boolean {
        return oldItem == newItem
    }
}