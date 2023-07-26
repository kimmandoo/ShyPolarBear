package com.shypolarbear.presentation.ui.signup

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupBinding
import com.shypolarbear.presentation.ui.signup.pages.SignupMailFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupNameFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupPhoneFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupTermsFragment
import timber.log.Timber

class SignupFragment :
    BaseFragment<FragmentSignupBinding, SignupViewModel>(R.layout.fragment_signup) {

    override val viewModel: SignupViewModel by viewModels()
    private lateinit var pagerAdapter: SignupAdapter
    private var idx = 1

    override fun initView() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        viewModel.getTermData().observe(viewLifecycleOwner, Observer { newData ->
            // 데이터 받아오는 곳
        })

        val pageList = listOf(
            SignupTermsFragment(),
            SignupNameFragment(),
            SignupPhoneFragment(),
            SignupMailFragment()
        )
        pagerAdapter = SignupAdapter(requireActivity(), pageList)
        binding.apply {
            signupIndicator.text = getString(R.string.signup_page_indicator, idx)
            signupViewpager.apply {
                adapter = pagerAdapter
                isUserInputEnabled = false
            }

            signupBtnNext.setOnClickListener {
                //조건을 갖추면 활성화 되도록
                binding.signupBtnNext.isActivated = true
                binding.signupTvNext.isActivated = true
                if (signupTvNext.text.equals("가입 완료")) {
                    // 메인페이지로 넘어가도록
                } else {
                    val currentItem = signupViewpager.currentItem

                    if (currentItem < (signupViewpager.adapter!!.itemCount.minus(1) ?: 0)) {
                        idx = currentItem + 2
                        signupIndicator.text = getString(R.string.signup_page_indicator, idx)
                        signupViewpager.setCurrentItem(currentItem + 1, true)
                    }
                    Timber.d("SIGN + $currentItem")
                }
            }

            signupBtnBack.setOnClickListener {
                val currentItem = signupViewpager.currentItem

                if (currentItem > 0) {
                    idx--
                    signupIndicator.text = getString(R.string.signup_page_indicator, idx)
                    signupViewpager.setCurrentItem(currentItem - 1, true)
                    Timber.d("SIGN - $currentItem")
                }
            }
        }
    }
}