package com.shypolarbear.presentation.sample

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSampleBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

@AndroidEntryPoint
class SampleFragment: BaseFragment<FragmentSampleBinding, SampleViewModel> (
    R.layout.fragment_sample
) {
    override val viewModel: SampleViewModel by viewModels()

    override fun initView() {
        binding.apply {
            Timber.d("Timber Test(Fragment)")

            binding.tvSampleTitle.text = "부끄북극"

            viewModel.loadSampleData()

            viewModel.sampleData.observe(viewLifecycleOwner) {
                binding.exampleModel = it
            }
        }
    }
}
