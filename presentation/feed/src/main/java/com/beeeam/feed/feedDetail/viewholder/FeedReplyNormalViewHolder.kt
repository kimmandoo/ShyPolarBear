package com.beeeam.feed.feedDetail.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedReplyNormalBinding
import com.beeeam.util.FeedDetailLikeBtnType
import com.beeeam.util.GlideUtil
import com.beeeam.util.showLikeBtnIsLike
import com.shypolarbear.domain.model.feed.feedDetail.ChildComment

class FeedReplyNormalViewHolder(
    private val binding: ItemFeedReplyNormalBinding,
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
) : RecyclerView.ViewHolder(binding.root) {

    private var childComment: ChildComment = ChildComment()

    init {
        binding.apply {
            btnFeedReplyNormalLike.setOnClickListener {
                onBtnLikeClick(
                    btnFeedReplyNormalLike,
                    childComment.isLike,
                    childComment.likeCount,
                    tvFeedReplyNormalLikeCnt,
                    parentCommentId,
                    childComment.commentId,
                    FeedDetailLikeBtnType.REPLY_LIKE_BTN,
                )
            }

            ivFeedReplyNormalProperty.setOnClickListener {
                when (childComment.isAuthor) {
                    true ->
                        onMyReplyPropertyClick(ivFeedReplyNormalProperty, childComment.commentId, 0, childComment.content)
                    false ->
                        onOtherReplyPropertyClick(ivFeedReplyNormalProperty)
                }
            }
        }
    }

    fun bind(item: ChildComment) {
        childComment = item
        setReply(item)

        binding.executePendingBindings()
    }

    private fun setReply(item: ChildComment) {
        binding.apply {
            tvFeedReplyNormalNickname.text = item.authorNickname
            tvFeedReplyNormalContent.text = item.content
            tvFeedReplyNormalTime.text = item.createdDate
            btnFeedReplyNormalLike.showLikeBtnIsLike(item.isLike, btnFeedReplyNormalLike)
            tvFeedReplyNormalLikeCnt.text = item.likeCount.toString()

            if (!item.authorProfileImage.isNullOrBlank()) {
                GlideUtil.loadImage(itemView.context, item.authorProfileImage, ivFeedReplyNormalProfile)
            } else {
                GlideUtil.loadImage(itemView.context, url = null, view = ivFeedReplyNormalProfile, placeHolder = com.beeeam.designsystem.R.drawable.ic_user_base_profile)
            }
        }
    }
}
