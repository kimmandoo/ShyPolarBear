package com.beeeam.ranking

import StickyHeaderItemDecoration
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.base.BaseFragment
import com.beeeam.ranking.adapter.RankingAdapter
import com.beeeam.ranking.databinding.FragmentRankingBinding
import com.beeeam.stickyheaderrecyclerview.SectionCallBack
import com.beeeam.util.infiniteScroll
import com.shypolarbear.domain.model.ranking.Ranking
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingFragment :
    BaseFragment<FragmentRankingBinding, RankingViewModel>(R.layout.fragment_ranking) {
    override val viewModel: RankingViewModel by viewModels()
    private val rankingAdapter = RankingAdapter()
    private val items: MutableList<Ranking> = mutableListOf(Ranking())

    override fun initView() {

        viewModel.apply {
            loadRankingData()
            myRanking.observe(viewLifecycleOwner) { myRanking ->
                items.add(myRanking)
            }

            totalRankingResponse.observe(viewLifecycleOwner) { totalRanking ->
                totalRanking?.let {
                    binding.apply {
                        if(items.size > 2)  items.removeLast()
                        totalRanking.content.forEach { ranking ->
                            items.add(ranking)
                        }
                        if(!totalRanking.last) items.add(Ranking())
                        rankingAdapter.submitList(items.toList())
                        rankingProgressbar.isVisible = false
                    }
                }
            }
        }

        binding.apply {
            setAdapter(rankingAdapter)
            rankingProgressbar.isVisible = true
        }
    }

    private fun setAdapter(adapter: RankingAdapter) {
        binding.apply {
            rvRanking.adapter = adapter
            rvRanking.addItemDecoration(StickyHeaderItemDecoration(getSectionCallback()))
            rvRanking.infiniteScroll {
                viewModel.loadMoreRanking()
            }
        }
    }

    private fun getSectionCallback(): SectionCallBack {
        return object : SectionCallBack {
            override fun getHeaderView(list: RecyclerView, position: Int): View? {
                return rankingAdapter.getHeaderView(list, position)
            }
        }
    }
}
