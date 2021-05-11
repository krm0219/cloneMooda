package com.krm0219.mooda.util

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kotlin.math.abs
import kotlin.math.sqrt

class SliderLayoutManager(context: Context?) : LinearLayoutManager(context) {
    init {
        orientation = VERTICAL
    }

    var callback: OnItemSelectedListener? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var snapHelper: LinearSnapHelper


    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        recyclerView = view!!

        // Smart snapping
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        scaleDownView()
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return if (orientation == VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            scaleDownView()
            scrolled
        } else {
            0
        }
    }

    private fun scaleDownView() {

        val mid = height / 5.0f

        for (i in 0 until childCount) {

            // Calculating the distance of the child from the center
            val child = getChildAt(i)
            val childMid = (getDecoratedTop(child!!) + getDecoratedBottom(child)) / 5.0f
            val distanceFromCenter = abs(mid - childMid)

            // The scaling formula
            val scale = 1 - sqrt((distanceFromCenter / width).toDouble()).toFloat() * 0.66f

            // Set scale to view
            child.scaleX = scale
            child.scaleY = scale
            child.alpha = scale
        }
    }


    private var snapPosition = RecyclerView.NO_POSITION
    override fun onScrollStateChanged(newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            maybeNotifySnapPositionChange(recyclerView)
        }
    }

    private fun maybeNotifySnapPositionChange(recyclerView: RecyclerView) {

        val snapPosition = snapHelper.getSnapPosition(recyclerView)
        val snapPositionChanged = this.snapPosition != snapPosition
        if (snapPositionChanged) {
            callback?.onItemSelected(snapPosition)
            this.snapPosition = snapPosition
        }
    }

    private fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
        val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }

    //
//    override fun onScrollStateChanged(state: Int) {
//        super.onScrollStateChanged(state)
//
//        // When scroll stops we notify on the selected item
//        if (state == RecyclerView.SCROLL_STATE_IDLE) {
//
//            Log.e("View", "onScrollStateChanged  ")
//
//            // Find the closest child to the recyclerView center --> this is the selected item.
//            val recyclerViewCenterX = getRecyclerViewCenterX()
//            var minDistance = recyclerView.height
//            var position = -1
//            for (i in 0 until recyclerView.childCount) {
//                val child = recyclerView.getChildAt(i)
//                val childCenterX = getDecoratedTop(child) + (getDecoratedBottom(child) - getDecoratedTop(child)) / 2
//                val newDistance = abs(childCenterX - recyclerViewCenterX)
//                if (newDistance < minDistance) {
//                    minDistance = newDistance
//                    position = recyclerView.getChildLayoutPosition(child)
//                }
//            }
//
//            // Notify on item selection
//            callback?.onItemSelected(position)
//        }
//    }
//
//    private fun getRecyclerViewCenterX(): Int {
//        return (recyclerView.bottom - recyclerView.top) / 2 + recyclerView.top
//    }

    interface OnItemSelectedListener {
        fun onItemSelected(position: Int)
    }
}