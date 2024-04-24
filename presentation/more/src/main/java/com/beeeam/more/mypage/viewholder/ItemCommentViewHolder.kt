package com.beeeam.more.mypage.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.beeeam.myinfo.databinding.ItemPageCommentBinding
import com.beeeam.util.GlideUtil
import com.shypolarbear.domain.model.mypage.MyCommentFeed

class ItemCommentViewHolder(private val binding: ItemPageCommentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MyCommentFeed) {
        binding.apply {
            tvItemPage.text = item.title
            item.feedImage?.let { image ->
                GlideUtil.loadImage(binding.root.context, image, ivItemPage)
            }
            tvItemAuthor.text = item.author
            item.authorProfileImage?.let { image ->
                GlideUtil.loadImage(binding.root.context, image, ivItemProfile)
            }
        }
    }
}