package com.beeeam.more.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.more.mypage.viewholder.ItemPostViewHolder
import com.beeeam.myinfo.databinding.ItemPagePostBinding
import com.beeeam.ui.LoadingViewHolder
import com.beeeam.ui.databinding.ItemLoadingBinding
import com.beeeam.util.ItemType
import com.shypolarbear.domain.model.mypage.MyFeed

class MyPostAdapter(
    private val _items: List<MyFeed?>,
    private val onMyFeedPropertyClick: (feedId: Int, view: ImageView) -> Unit = { _, _ -> },
) :
    ListAdapter<MyFeed, RecyclerView.ViewHolder>(MyFeedDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return if (_items[position] != null) ItemType.ITEM.state else ItemType.LOADING.state
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ItemType.ITEM.state) {
            ItemPostViewHolder(
                ItemPagePostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                onMyFeedPropertyClick = onMyFeedPropertyClick,
            )
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
        (holder as ItemPostViewHolder).bind(_items[position]!!)
    }

    override fun getItemCount(): Int {
        return _items.size
    }
}

class MyFeedDiffCallback : DiffUtil.ItemCallback<MyFeed>() {

    override fun areItemsTheSame(oldItem: MyFeed, newItem: MyFeed): Boolean {
        return oldItem.feedId == newItem.feedId
    }

    override fun areContentsTheSame(oldItem: MyFeed, newItem: MyFeed): Boolean {
        return oldItem == newItem
    }
}
