package com.beeeam.feed.feedDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedDetailNoImagePostBinding
import com.beeeam.feed.databinding.ItemFeedDetailPostBinding
import com.beeeam.util.FeedDetailLikeBtnType
import com.beeeam.util.FeedViewType
import com.shypolarbear.domain.model.feed.Feed
import com.beeeam.feed.feedDetail.viewholder.FeedDetailNoImagePostViewHolder
import com.beeeam.feed.feedDetail.viewholder.FeedDetailPostViewHolder
class FeedDetailPostAdapter(
    private val onPostPropertyClick: (Feed, ImageView) -> Unit = { _, _ -> },
    private val onBtnLikeClick: (
        view: Button,
        isLiked: Boolean,
        likeCnt: Int,
        textView: TextView,
        commentId: Int,
        replyId: Int,
        itemType: FeedDetailLikeBtnType,
    ) -> Unit = { _, _, _, _, _, _, _ -> },
    private val onBtnBackClick: () -> Unit = {},
) : ListAdapter<Feed, RecyclerView.ViewHolder>(FeedPostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FeedViewType.ITEM_HAS_IMAGES.viewType -> {
                FeedDetailPostViewHolder(
                    ItemFeedDetailPostBinding.inflate(
                        LayoutInflater.from(
                            parent.context,
                        ),
                        parent,
                        false,
                    ),
                    onPostPropertyClick = onPostPropertyClick,
                    onBtnLikeClick = onBtnLikeClick,
                    onBtnBackClick = onBtnBackClick,
                )
            }

            FeedViewType.ITEM_HAS_NO_IMAGES.viewType -> {
                FeedDetailNoImagePostViewHolder(
                    ItemFeedDetailNoImagePostBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                    onPostPropertyClick = onPostPropertyClick,
                    onBtnLikeClick = onBtnLikeClick,
                    onBtnBackClick = onBtnBackClick,
                )
            }

            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            getItem(position).feedImages.isNullOrEmpty() -> {
                (holder as FeedDetailNoImagePostViewHolder).bind(getItem(position))
            }
            else -> {
                (holder as FeedDetailPostViewHolder).bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            getItem(position).feedImages.isNullOrEmpty() -> {
                FeedViewType.ITEM_HAS_NO_IMAGES.viewType
            }
            else -> {
                FeedViewType.ITEM_HAS_IMAGES.viewType
            }
        }
    }
}

class FeedPostDiffCallback : DiffUtil.ItemCallback<Feed>() {
    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.feedId == newItem.feedId
    }

    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem == newItem
    }
}