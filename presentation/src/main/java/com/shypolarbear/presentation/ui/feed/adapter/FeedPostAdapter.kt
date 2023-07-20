package com.shypolarbear.presentation.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shypolarbear.domain.model.feed.FeedPost
import com.shypolarbear.presentation.databinding.ItemFeedBinding
import com.shypolarbear.presentation.ui.feed.viewholder.FeedPostViewHolder

class FeedPostAdapter(
    private val onMyPostPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherPostPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onMyBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (view: Button) -> Unit = { _ -> },
    private val onMoveToDetailClick: () -> Unit = { }
    ): ListAdapter<FeedPost, FeedPostViewHolder>(FeedPostDiffCallback()) {

    private lateinit var binding : ItemFeedBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedPostViewHolder {
        binding = ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedPostViewHolder(
            binding,
            onMyPostPropertyClick = onMyPostPropertyClick,
            onOtherPostPropertyClick = onOtherPostPropertyClick,
            onMyBestCommentPropertyClick = onMyBestCommentPropertyClick,
            onOtherBestCommentPropertyClick = onOtherBestCommentPropertyClick,
            onBtnLikeClick = onBtnLikeClick,
            onMoveToDetailClick = onMoveToDetailClick
        )
    }

    override fun onBindViewHolder(holder: FeedPostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FeedPostDiffCallback : DiffUtil.ItemCallback<FeedPost>() {

    override fun areItemsTheSame(oldItem: FeedPost, newItem: FeedPost): Boolean {
        return oldItem.testContent == newItem.testContent
    }

    override fun areContentsTheSame(oldItem: FeedPost, newItem: FeedPost): Boolean {
        return oldItem == newItem
    }
}