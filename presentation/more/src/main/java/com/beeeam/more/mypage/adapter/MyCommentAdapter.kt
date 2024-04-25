package com.beeeam.more.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.more.mypage.viewholder.ItemCommentViewHolder
import com.beeeam.more.mypage.viewholder.ItemPostViewHolder
import com.beeeam.myinfo.databinding.ItemPageCommentBinding
import com.beeeam.ui.LoadingViewHolder
import com.beeeam.ui.databinding.ItemLoadingBinding
import com.beeeam.util.ItemType
import com.shypolarbear.domain.model.mypage.MyCommentFeed

class MyCommentAdapter: ListAdapter<MyCommentFeed, RecyclerView.ViewHolder>(MyCommentDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).feedId == 0) ItemType.LOADING.state else ItemType.ITEM.state
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ItemType.ITEM.state) {
            ItemCommentViewHolder(ItemPageCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            LoadingViewHolder(
                ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItem(position).feedId != 0) {
            (holder as ItemCommentViewHolder).bind(getItem(position)!!)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}

class MyCommentDiffCallback : DiffUtil.ItemCallback<MyCommentFeed>() {

    override fun areItemsTheSame(oldItem: MyCommentFeed, newItem: MyCommentFeed): Boolean {
        return oldItem.feedId == newItem.feedId
    }

    override fun areContentsTheSame(oldItem: MyCommentFeed, newItem: MyCommentFeed): Boolean {
        return oldItem == newItem
    }
}