package com.beeeam.feed.feedDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedCommentDeleteBinding
import com.beeeam.feed.databinding.ItemFeedCommentNormalBinding
import com.beeeam.util.FeedCommentViewType
import com.beeeam.util.FeedDetailLikeBtnType
import com.shypolarbear.domain.model.feed.Comment
import com.beeeam.feed.feedDetail.viewholder.FeedCommentDeleteViewHolder
import com.beeeam.feed.feedDetail.viewholder.FeedCommentNormalViewHolder
import com.beeeam.ui.LoadingViewHolder
import com.beeeam.ui.databinding.ItemLoadingBinding

class FeedCommentAdapter(
    private val onMyCommentPropertyClick: (view: ImageView, commentId: Int, position: Int, commentAuthor: String, content: String) -> Unit = { _, _, _, _, _ -> },
    private val onOtherCommentPropertyClick: (view: ImageView, commentId: Int, position: Int, commentAuthor: String) -> Unit = { _, _, _, _ -> },
    private val onMyReplyPropertyClick: (view: ImageView, commentId: Int, feedId: Int, content: String) -> Unit = { _, _, _, _ -> },
    private val onOtherReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (
        view: Button,
        isLiked: Boolean,
        likeCnt: Int,
        textView: TextView,
        commentId: Int,
        replyId: Int,
        itemType: FeedDetailLikeBtnType,
    ) -> Unit = { _, _, _, _, _, _, _ -> },
    private val onItemClick: () -> Unit,
) : ListAdapter<Comment, RecyclerView.ViewHolder>(FeedCommentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FeedCommentViewType.LOADING.commentType -> {
                LoadingViewHolder(ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }

            FeedCommentViewType.NORMAL.commentType -> {
                FeedCommentNormalViewHolder(
                    ItemFeedCommentNormalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                    onMyCommentPropertyClick = onMyCommentPropertyClick,
                    onOtherCommentPropertyClick = onOtherCommentPropertyClick,
                    onMyReplyPropertyClick = onMyReplyPropertyClick,
                    onOtherReplyPropertyClick = onOtherReplyPropertyClick,
                    onBtnLikeClick = onBtnLikeClick,
                    onItemClick = onItemClick,
                )
            }

            FeedCommentViewType.DELETE.commentType -> {
                FeedCommentDeleteViewHolder(
                    ItemFeedCommentDeleteBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                    onMyReplyPropertyClick = onMyReplyPropertyClick,
                    onOtherReplyPropertyClick = onOtherReplyPropertyClick,
                    onBtnLikeClick = onBtnLikeClick,
                )
            }

            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            getItem(position).isDeleted -> {
                (holder as FeedCommentDeleteViewHolder).bind(getItem(position))
            }
            getItem(position).commentId == 0 -> {}
            !getItem(position).isDeleted -> {
                (holder as FeedCommentNormalViewHolder).bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            getItem(position).isDeleted -> {
                FeedCommentViewType.DELETE.commentType
            }
            getItem(position).commentId == 0 -> {
                FeedCommentViewType.LOADING.commentType
            }
            else -> {
                FeedCommentViewType.NORMAL.commentType
            }
        }
    }
}

class FeedCommentDiffCallback : DiffUtil.ItemCallback<Comment>() {

    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.commentId == newItem.commentId
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }
}