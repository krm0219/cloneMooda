package com.krm0219.mooda.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter


@BindingAdapter("imageEmoji")
fun loadImage(imageView: ImageView, emoji: Int?) {

    val context = imageView.context
    val packageName = context.packageName
    val resourceId = context.resources.getIdentifier("gamttoek_$emoji", "drawable", packageName)
    imageView.setBackgroundResource(resourceId)
}


@BindingAdapter("android:visibleIf")
fun View.setVisibleIf(value: Boolean) {

    isVisible = value
}
