package com.beeeam.more.mypage

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.beeeam.base.BaseFragment
import com.beeeam.more.mypage.adapter.MyCommentAdapter
import com.beeeam.more.mypage.adapter.MyPostAdapter
import com.beeeam.myinfo.R
import com.beeeam.myinfo.databinding.FragmentMyPageBinding
import com.beeeam.util.FeedContentType
import com.beeeam.util.PostProperty
import com.beeeam.util.PowerMenuUtil
import com.beeeam.util.createNavDeepLinkRequest
import com.beeeam.util.infiniteScroll
import com.skydoves.powermenu.PowerMenuItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel: MyPageViewModel by viewModels()
    private val postAdapter = MyPostAdapter(
        onMyFeedPropertyClick = { feedId: Int, view: ImageView ->
            showMyPostPropertyMenu(feedId, view)
        }
    )
    private val commentAdapter = MyCommentAdapter()

    override fun initView() {

        binding.apply {
            viewModel.loadMyFeed()
            setAdapter(postAdapter, FeedContentType.POST)

            viewModel.myPostResponse.observe(viewLifecycleOwner) { postFeed ->
                postFeed?.let {
                    myFeedProgressbar.isVisible = false
                    if (it.count != 0) {
                        tvMyPostNonPost.isVisible = false
                    }
                    postAdapter.submitList(postFeed.content)
                }
            }

            viewModel.myCommentResponse.observe(viewLifecycleOwner) { commentFeed ->
                commentFeed?.let {
                    commentAdapter.submitList(commentFeed.content)
                }
            }

            myFeedProgressbar.isVisible = true
            tvMyPostPost.isActivated = true

            tvMyPostPost.setOnClickListener {
                showNonDataText(FeedContentType.POST, viewModel.myPostResponse.value?.count)
                invertActivation(it, tvMyPostComment)
                setAdapter(postAdapter, FeedContentType.POST)
            }

            tvMyPostComment.setOnClickListener {
                showNonDataText(FeedContentType.COMMENT, viewModel.myCommentResponse.value?.count)
                invertActivation(it, tvMyPostPost)
                setAdapter(commentAdapter, FeedContentType.COMMENT)
            }

            myPostBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onBackPressed() {
        findNavController().popBackStack()
    }

    private fun setAdapter(adapter: Adapter<ViewHolder>, contentType: FeedContentType) {
        binding.apply {
            rvMyPost.adapter = adapter
            rvMyPost.infiniteScroll {
                viewModel.loadMoreMyPost(contentType)
            }
        }
    }

    private fun showMyPostPropertyMenu(feedId: Int, view: ImageView) {
        val myPostPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_delete)),
            )

        PowerMenuUtil.showPowerMenu(
            requireContext(),
            view,
            viewLifecycleOwner,
            myPostPropertyItems,
            onItemClickListener = { position, item ->
                when (position) {
                    PostProperty.MODIFY.state -> {
                        findNavController().navigate(createNavDeepLinkRequest("shyPolarBear://fragmentFeedDetail/${feedId}"))
                    }
                    PostProperty.DELETE.state -> {
                        viewModel.requestDeleteFeed(feedId = feedId)
                    }
                }
            },
        )
    }

    private fun showNonDataText(type: FeedContentType, count: Int?) {
        binding.apply {
            if (type == FeedContentType.POST) {
                tvMyPostNonComment.isVisible = false
                if (count == 0 || count == null) {
                    tvMyPostNonPost.isVisible = true
                }
            } else {
                tvMyPostNonPost.isVisible = false
                if (count == 0 || count == null) {
                    tvMyPostNonComment.isVisible = true
                }
            }
        }
    }

    private fun invertActivation(onSelected: View, offSelection: View) {
        onSelected.isActivated = true
        offSelection.isActivated = false
    }
}
