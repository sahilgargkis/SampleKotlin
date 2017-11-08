package com.samplekotlin.view.custom

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.View

import java.lang.ref.WeakReference

class NestedScrollViewBehavior : AppBarLayout.Behavior() {

    internal var mTotalDy: Int = 0
    internal var mPreviousDy: Int = 0
    internal lateinit var mPreScrollChildRef: WeakReference<AppBarLayout>

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout,
                                   target: View, dx: Int, dy: Int, consumed: IntArray) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed)
        // Reset the total fling delta distance if the user starts scrolling back up
        if (dy < 0) {
            mTotalDy = 0
        }
        // Only track move distance if the movement is positive (since the bug is only present
        // in upward flings), equal to the consumed value and the move distance is greater
        // than the minimum difference value
        if (dy > 0 && consumed[1] == dy && MIN_DY_DELTA < Math.abs(mPreviousDy - dy)) {
            mPreScrollChildRef = WeakReference(child)
            mTotalDy += dy * FLING_FACTOR
        }
        mPreviousDy = dy
    }

    override fun onStartNestedScroll(parent: CoordinatorLayout, child: AppBarLayout,
                                     directTargetChild: View, target: View, nestedScrollAxes: Int): Boolean {
        // Stop any previous fling animations that may be running
        onNestedFling(parent, child, target, 0f, 0f, false)
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes)
    }

    override fun onStopNestedScroll(parent: CoordinatorLayout, abl: AppBarLayout, target: View) {
        if (mTotalDy > 0 && mPreScrollChildRef != null && mPreScrollChildRef.get() != null) {
            // Programmatically trigger fling if all conditions are met
            onNestedFling(parent, mPreScrollChildRef.get()!!, target, 0f, mTotalDy.toFloat(), false)
            mTotalDy = 0
            mPreviousDy = 0
        }
        super.onStopNestedScroll(parent, abl, target)
    }

    companion object {

        // Lower value means fling action is more easily triggered
        internal val MIN_DY_DELTA = 4
        // Lower values mean less velocity, higher means higher velocity
        internal val FLING_FACTOR = 20
    }
}