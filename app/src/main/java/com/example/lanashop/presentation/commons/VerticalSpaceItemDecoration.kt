package com.example.lanashop.presentation.commons

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/***
 * Made by Lokesh Desai (Android4Dev)
 */
class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        outRect.bottom = verticalSpaceHeight
    }
}