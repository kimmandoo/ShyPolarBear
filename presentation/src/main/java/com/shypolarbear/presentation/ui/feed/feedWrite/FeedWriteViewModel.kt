package com.shypolarbear.presentation.ui.feed.feedWrite

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shypolarbear.domain.model.feed.FeedWriteImg
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedWriteViewModel @Inject constructor(

): BaseViewModel(){
    private val _testImgList = MutableLiveData<MutableList<FeedWriteImg>>(mutableListOf())
    val testImgList: LiveData<MutableList<FeedWriteImg>> = _testImgList

    fun addImgList(imgUri: List<Uri>) {
        val feedWriteImgList = imgUri.map { FeedWriteImg(it.toString()) }
        _testImgList.value!!.addAll(0, feedWriteImgList)
    }

    fun removeImgList(position: Int) {
        _testImgList.value!!.removeAt(position)
    }
}