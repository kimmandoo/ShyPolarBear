package com.shypolarbear.presentation.ui.signup.pages

import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupPhoneBinding
import com.shypolarbear.presentation.ui.signup.InputState
import com.shypolarbear.presentation.ui.signup.SignupViewModel
import com.shypolarbear.presentation.util.afterTextChanged
import com.shypolarbear.presentation.util.keyboardDown
import com.shypolarbear.presentation.util.phonePattern
import com.shypolarbear.presentation.util.setColorStateWithInput

const val PHONE_NUMBER_DASH_INCLUDE = 13
class SignupPhoneFragment :
    BaseFragment<FragmentSignupPhoneBinding, SignupViewModel>(R.layout.fragment_signup_phone) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })
    private lateinit var phoneNumber: String
    override fun initView() {
        binding.apply {
            etSignupPhone.keyboardDown(this@SignupPhoneFragment)

            etSignupPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher("KR"))
            etSignupPhone.afterTextChanged({ s ->
                val state = if(s?.length == PHONE_NUMBER_DASH_INCLUDE){
                    phoneNumber = s.replace(phonePattern, "")
                    tvSignupPhoneRule.text = getString(R.string.signup_phone_hint_confirm)
                    InputState.ACCEPT
                }else{
                    phoneNumber = ""
                    tvSignupPhoneRule.text = getString(R.string.signup_phone_hint_error)
                    InputState.ERROR
                }
                etSignupPhone.setColorStateWithInput(
                    state,
                    tvSignupPhoneRule,
                    signupEtCheck
                )
                viewModel.setPhoneData(phoneNumber)
            })
        }
    }
}