package com.krm0219.mooda.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krm0219.mooda.R
import com.krm0219.mooda.util.KUtil

// Month 단위로 움직임
class MainViewPagerAdapter1(private var context: Context) : RecyclerView.Adapter<MainViewPagerAdapter1.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_month, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        //   holder.bind(position)

        holder.text_main_year.text = KUtil.mainList[position].year.toString()
        holder.text_main_month.text = KUtil.mainList[position].monthName
        setHighLighter(holder)

        val gridLayoutManager = GridLayoutManager(context, 5, GridLayoutManager.VERTICAL, false)
        holder.recyclerview_main.layoutManager = gridLayoutManager
        val adapter = MainEmojiAdapter(context, position)
        holder.recyclerview_main.adapter = adapter
    }

    override fun getItemCount(): Int = KUtil.mainList.size


    private fun setHighLighter(holder: PagerViewHolder) {

        val drawListener = ViewTreeObserver.OnDrawListener {

            val width = holder.text_main_month.width + 100
            Log.e("krm0219", "width1  $width")

            val params = RelativeLayout.LayoutParams(width, KUtil.dpToPx(context, 30f).toInt())
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            holder.img_main_highlighter.layoutParams = params
        }


        holder.text_main_month.viewTreeObserver.addOnDrawListener(drawListener)
        holder.text_main_month.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                holder.text_main_month.viewTreeObserver.removeOnDrawListener(drawListener)
                holder.text_main_month.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val width = holder.text_main_month.width + 100
                Log.e("krm0219", "width2  $width")

                val params = RelativeLayout.LayoutParams(width, KUtil.dpToPx(context, 30f).toInt())
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                holder.img_main_highlighter.layoutParams = params
            }
        })
    }

    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val text_main_year: TextView
        val text_main_month: TextView
        val img_main_highlighter: ImageView
        val recyclerview_main: RecyclerView

        init {

            text_main_year = view.findViewById(R.id.text_main_year)
            text_main_month = view.findViewById(R.id.text_main_month)
            img_main_highlighter = view.findViewById(R.id.img_main_highlighter)
            recyclerview_main = view.findViewById(R.id.recyclerview_main)
        }

//        fun bind(position: Int) {
//
//            text_main_year.text = KUtil.mainList[position].year.toString()
//            text_main_month.text = KUtil.mainList[position].monthName
//            setHighLighter()
//
//
//            val gridLayoutManager = GridLayoutManager(context, 5, GridLayoutManager.VERTICAL, false)
//            recyclerview_main.layoutManager = gridLayoutManager
//            val adapter = MainEmojiAdapter(context, position)
//            recyclerview_main.adapter = adapter
//        }
//
//
//        private fun setHighLighter() {
//
//            val drawListener = ViewTreeObserver.OnDrawListener {
//
//                val width = text_main_month.width + 100
//                Log.e("krm0219", "width1  $width")
//
//                val params = RelativeLayout.LayoutParams(width, KUtil.dpToPx(context, 30f).toInt())
//                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
//                img_main_highlighter.layoutParams = params
//            }
//
//
//            text_main_month.viewTreeObserver.addOnDrawListener(drawListener)
//            text_main_month.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//                override fun onGlobalLayout() {
//
//                    text_main_month.viewTreeObserver.removeOnDrawListener(drawListener)
//                    text_main_month.viewTreeObserver.removeOnGlobalLayoutListener(this)
//
//                    val width = text_main_month.width + 100
//                    Log.e("krm0219", "width2  $width")
//
//                    val params = RelativeLayout.LayoutParams(width, KUtil.dpToPx(context, 30f).toInt())
//                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
//                    img_main_highlighter.layoutParams = params
//                }
//            })
//        }
    }
}