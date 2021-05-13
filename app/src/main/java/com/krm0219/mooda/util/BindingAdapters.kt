package com.krm0219.mooda.util

import android.graphics.Color
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.krm0219.mooda.R
import com.krm0219.mooda.data.CalendarData


@BindingAdapter("imageEmoji")
fun loadImage(imageView: ImageView, emoji: Int?) {

    if (emoji != 0) {

        val context = imageView.context
        val packageName = context.packageName
        val resourceId = context.resources.getIdentifier("gamtteok_$emoji", "drawable", packageName)
        imageView.setBackgroundResource(resourceId)
    } else {

        imageView.setBackgroundColor(Color.TRANSPARENT)
    }
}

@BindingAdapter("emoji", "clicked")
fun loadImageAndSized(imageView: ImageView, emoji: Int?, clicked: Int) {

    val context = imageView.context
    val packageName = context.packageName
    val resourceId = context.resources.getIdentifier("gamtteok_$emoji", "drawable", packageName)
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


@BindingAdapter("setDate")
fun setDate(textView: TextView, data: CalendarData) {

    val res = textView.context.resources
    val text = String.format(res.getString(R.string.text_month_day_dayofweek), data.getFormatMonth(), data.getFormatDay(), data.getDayString())
    textView.text = text
}


@BindingAdapter("android:visibleIf")
fun View.setVisibleIf(value: Boolean) {

    isVisible = value
}
