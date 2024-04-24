package com.beeeam.feed.feedDetail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedReplyDeleteBinding
import com.shypolarbear.domain.model.feed.feedDetail.ChildComment

class FeedReplyDeleteViewHolder(private val binding: ItemFeedReplyDeleteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.executePendingBindings()
    }
}
