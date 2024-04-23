package com.beeeam.more.mypage.viewholder

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.myinfo.databinding.ItemPagePostBinding
import com.beeeam.util.GlideUtil
import com.shypolarbear.domain.model.mypage.MyFeed

class ItemPostViewHolder(
    private val binding: ItemPagePostBinding,
    private val onMyFeedPropertyClick: (feedId: Int, view: ImageView) -> Unit = { _, _ -> },
) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var myFeed: MyFeed

    init {
        if (::myFeed.isInitialized) {
            binding.ivItemPostProperty.setOnClickListener {
                onMyFeedPropertyClick(myFeed.feedId, binding.ivItemPostProperty)
            }
        }
    }

    fun bind(item: MyFeed) {
        myFeed = item
        binding.apply {
            tvItemPageTitle.text = item.title
            GlideUtil.loadImage(binding.root.context, item.feedImage, ivItemPage)
            ivItemPostProperty.setOnClickListener {
                onMyFeedPropertyClick(item.feedId, ivItemPostProperty)
            }
        }
    }
}