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

@BindingAdapter("textEmoji")
fun loadText(textView: TextView, emoji: Int?) {

    val context = textView.context
    val packageName = context.packageName
    val resourceId = context.resources.getIdentifier("text_emoji_$emoji", "string", packageName)
    textView.text = context.getText(resourceId)
}


@BindingAdapter("android:visibleIf")
fun View.setVisibleIf(value: Boolean) {

    isVisible = value
}
