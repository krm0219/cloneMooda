package com.krm0219.mooda.util

import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter


@BindingAdapter("imageEmoji")
fun loadImage(imageView: ImageView, emoji: Int?) {

    if (emoji == 0) {

        imageView.visibility = View.GONE
    } else {

        imageView.visibility = View.VISIBLE

        val context = imageView.context
        val packageName = context.packageName
        val resourceId = context.resources.getIdentifier("gamttoek_$emoji", "drawable", packageName)
        imageView.setBackgroundResource(resourceId)
    }
}

@BindingAdapter("emoji", "clicked")
fun loadImageAndSized(imageView: ImageView, emoji: Int?, clicked: Int) {

    val context = imageView.context
    val packageName = context.packageName
    val resourceId = context.resources.getIdentifier("gamttoek_$emoji", "drawable", packageName)
    imageView.setBackgroundResource(resourceId)

    if (emoji == clicked) {

        imageView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                imageView.layoutParams.width = (imageView.width * 1.32).toInt()
                imageView.layoutParams.height = (imageView.height * 1.32).toInt()
                imageView.requestLayout()
            }
        })
    }
}


@BindingAdapter("android:visibleIf")
fun View.setVisibleIf(value: Boolean) {

    isVisible = value
}
