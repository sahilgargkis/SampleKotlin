package com.samplekotlin.view.custom

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class HorizontalSpaceItemDecoration(private val horizontalSpaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State?) {
        if (parent.getChildAdapterPosition(view) != parent.adapter.itemCount - 1) {
            outRect.right = horizontalSpaceHeight
        }
    }
}