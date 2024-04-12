package com.beeeam.ranking

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.beeeam.base.BaseFragment
import com.beeeam.ranking.adapter.RankingAdapter
import com.beeeam.ranking.adapter.RankingMyAdapter
import com.beeeam.ranking.databinding.FragmentRankingBinding
import com.beeeam.util.infiniteScroll
import com.shypolarbear.domain.model.ranking.Ranking
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RankingFragment :
    BaseFragment<FragmentRankingBinding, RankingViewModel>(R.layout.fragment_ranking) {
    override val viewModel: RankingViewModel by viewModels()
    override fun initView() {
        var totalRankingList: MutableList<Ranking> = mutableListOf()
        val rankingMyAdapter = RankingMyAdapter()
        val rankingAdapter = RankingAdapter()

        viewModel.apply {
            loadRankingData()
            myRanking.observe(viewLifecycleOwner) { myRanking ->
                rankingMyAdapter.submitList(mutableListOf(myRanking))
            }

            totalRankingResponse.observe(viewLifecycleOwner) { totalRanking ->
                totalRanking?.let {
                    binding.apply {
                        if(totalRankingList.isEmpty()) {
                            totalRankingList = totalRanking.content.toMutableList()
                        } else {
                            totalRanking.content.forEach { ranking ->
                                totalRankingList.add(ranking)
                                Timber.d(totalRankingList.toString())
                            }
                        }
                        rankingAdapter.submitList(totalRankingList.toList())
                        rankingProgressbar.isVisible = false
                    }
                }
            }
        }

        binding.apply {
            setAdapter(ConcatAdapter(rankingMyAdapter, rankingAdapter))
            rankingProgressbar.isVisible = true
        }
    }

    private fun setAdapter(adapter: ConcatAdapter) {
        binding.apply {
            rvRanking.adapter = adapter
            rvRanking.infiniteScroll {
                viewModel.loadMoreRanking()
            }
        }
    }
}
