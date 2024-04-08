package com.beeeam.util

import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.beeeam.util.Const.POWER_MENU_OFFSET_X
import com.beeeam.util.Const.POWER_MENU_OFFSET_Y
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenuItem
import com.skydoves.powermenu.kotlin.createPowerMenu

object PowerMenuUtil {

    private const val MENU_RADIUS = 18f
    private const val MENU_WIDTH = 350
    private const val MENU_PADDING = 14
    private const val MENU_TEXT_SIZE = 18

    fun showPowerMenu(
        context: Context,
        view: View,
        lifecycle: LifecycleOwner,
        items: List<PowerMenuItem>,
        onItemClickListener: OnMenuItemClickListener<PowerMenuItem>,
    ) {
        createPowerMenu(context) {
            addItemList(items)
            setWidth(MENU_WIDTH)
            setTextSize(MENU_TEXT_SIZE)
            setMenuRadius(MENU_RADIUS)
            setPadding(MENU_PADDING)
            setLifecycleOwner(lifecycle)
            setAutoDismiss(true)
            setOnMenuItemClickListener(onItemClickListener)
            build()
        }.showAsDropDown(
            view,
            POWER_MENU_OFFSET_X,
            POWER_MENU_OFFSET_Y,
        )
    }
}
