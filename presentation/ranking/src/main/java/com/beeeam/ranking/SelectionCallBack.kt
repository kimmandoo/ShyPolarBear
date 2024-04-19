package com.beeeam.stickyheaderrecyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface SectionCallBack {
    fun getHeaderView(list: RecyclerView, position: Int): View?
}