package com.beeeam.feed.feedDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedReplyDeleteBinding
import com.beeeam.feed.databinding.ItemFeedReplyNormalBinding
import com.beeeam.util.FeedCommentViewType
import com.beeeam.util.FeedDetailLikeBtnType
import com.shypolarbear.domain.model.feed.feedDetail.ChildComment
import com.beeeam.feed.feedDetail.viewholder.FeedReplyDeleteViewHolder
import com.beeeam.feed.feedDetail.viewholder.FeedReplyNormalViewHolder

class FeedReplyAdapter(
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
    private val parentCommentId: Int,
) : ListAdapter<ChildComment, RecyclerView.ViewHolder>(FeedReplyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FeedCommentViewType.NORMAL.commentType -> {
                FeedReplyNormalViewHolder(
                    ItemFeedReplyNormalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                    onMyReplyPropertyClick = onMyReplyPropertyClick,
                    onOtherReplyPropertyClick = onOtherReplyPropertyClick,
                    onBtnLikeClick = onBtnLikeClick,
                    parentCommentId = parentCommentId,
                )
            }

            FeedCommentViewType.DELETE.commentType -> {
                FeedReplyDeleteViewHolder(
                    ItemFeedReplyDeleteBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )
            }

            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItem(position).isDeleted) {
            true -> {
                (holder as FeedReplyDeleteViewHolder).bind()
            }
            false -> {
                (holder as FeedReplyNormalViewHolder).bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isDeleted) {
            FeedCommentViewType.DELETE.commentType
        } else {
            FeedCommentViewType.NORMAL.commentType
        }
    }
}

class FeedReplyDiffCallback : DiffUtil.ItemCallback<ChildComment>() {

    override fun areItemsTheSame(oldItem: ChildComment, newItem: ChildComment): Boolean {
        return oldItem.commentId == newItem.commentId
    }

    override fun areContentsTheSame(oldItem: ChildComment, newItem: ChildComment): Boolean {
        return oldItem == newItem
    }
}