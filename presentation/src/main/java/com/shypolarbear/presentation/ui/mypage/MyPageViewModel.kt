package com.shypolarbear.presentation.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.mypage.MyPost
import com.shypolarbear.domain.model.mypage.MyPostRequest
import com.shypolarbear.domain.usecase.mypage.LoadMyPostUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import com.shypolarbear.presentation.ui.mypage.adapter.MyPostAdapter
import com.shypolarbear.presentation.util.simpleHttpErrorCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val loadMyPostUseCase: LoadMyPostUseCase,
) : BaseViewModel() {

    private val _myPostResponse = MutableLiveData<MyPost>()
    val myPostResponse: LiveData<MyPost> = _myPostResponse

    fun loadMyPost(lastFeedId: Int? = null): Job{
        val loadJob = viewModelScope.launch {
            val responseMyPost = loadMyPostUseCase(getMyPostRequest = MyPostRequest(lastFeedId, null))
            Timber.tag("MY_PAGE").d("${responseMyPost}")

            responseMyPost.onSuccess { response ->
                _myPostResponse.value = response.data
                Timber.tag("MY_PAGE").d("${_myPostResponse.value}")
            }
                .onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
        return loadJob
    }

    fun loadMoreMyPost(contentType: FeedContentType){
        viewModelScope.launch {
            if (!myPostResponse.value!!.isLast) {
                val loadJob = loadMyPost(myPostResponse.value!!.content.last().feedId
                )
                loadJob.join()
                when(contentType){
                    FeedContentType.POST -> TODO()
                    FeedContentType.COMMENT -> TODO()
                }
//                if (adapter is MyPostAdapter) adapter.updateList(viewModel.myPostResponse.value!!.content)

            }else{
                // isLast = true
            }
        }
    }

}