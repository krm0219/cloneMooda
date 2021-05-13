package com.krm0219.mooda.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

object ViewAnimation {

    fun rotateFab(v: View, rotate: Boolean): Boolean {

        v.animate().setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
            })
            .rotation(if (rotate) 135f else 0f)
        return rotate
    }

    fun showIn(v: View) {

        v.visibility = View.VISIBLE
        v.alpha = 0F
        v.translationY = v.height.toFloat()

        v.animate().setDuration(5000).translationY(0F)
            .setListener(object : AnimatorListenerAdapter() {
            })
            .alpha(1F)
            .start()
    }

    fun showOut(v: View) {

        v.visibility = View.VISIBLE
        v.alpha = 1F
        v.translationY = 0F
        v.animate().setDuration(200).translationY(v.height.toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                    v.visibility = View.GONE
                    super.onAnimationEnd(animation, isReverse)
                }
            })
            .alpha(0F)
            .start()
    }

    fun init(v: View) {

        v.visibility = View.GONE
        v.translationY = v.height.toFloat()
        v.alpha = 0F
    }
}