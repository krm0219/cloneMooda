package com.krm0219.mooda.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.krm0219.mooda.R
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.diary.DetailListActivity
import com.krm0219.mooda.util.KUtil
import java.util.*

class MainEmojiAdapter(private var context: Context, var pos: Int) : RecyclerView.Adapter<MainEmojiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_emoji, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

     //   holder.bind(pos, position)
        val diaryList = KUtil.mainList[pos].diaryDataList
        Collections.sort(diaryList, CompareDayAsc())

        val diaryData = diaryList[position]
        val resourceId = context.resources.getIdentifier("emoji${diaryData.emoji}", "drawable", context.packageName)
        holder.img_main_emoji_item.setBackgroundResource(resourceId)


        holder.img_main_emoji_item.setOnClickListener {

            val intent = Intent(context, DetailListActivity::class.java)
            intent.putExtra("calendarPosition", pos)
            intent.putExtra("emojiPosition", position)
            (context as Activity).startActivityForResult(intent, KUtil.REQUEST_LIST_DIARY)
            (context as Activity).overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        }
    }

    override fun getItemCount(): Int = KUtil.mainList[pos].diaryDataList.size


    class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {

         val img_main_emoji_item: ImageView

        init {

            img_main_emoji_item = itemView.findViewById(R.id.img_main_emoji_item)
        }

//        private val context = con
//
//        fun bind(calendarPosition: Int, emojiPosition: Int) {
//
//            val diaryList = KUtil.mainList[calendarPosition].diaryList
//            Collections.sort(diaryList, CompareDayAsc())
//
//            val diaryData = diaryList[emojiPosition]
//            val resourceId = context.resources.getIdentifier("emoji${diaryData.emoji}", "drawable", context.packageName)
//            img_main_emoji_item.setBackgroundResource(resourceId)
//
//
//            img_main_emoji_item.setOnClickListener {
//
//                val intent = Intent(context, DetailListActivity::class.java)
//                intent.putExtra("calendarPosition", calendarPosition)
//                intent.putExtra("emojiPosition", emojiPosition)
//                (context as Activity).startActivityForResult(intent, KUtil.REQUEST_LIST_DIARY)
//                (context as Activity).overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
//            }
//        }
    }

    internal class CompareDayAsc : Comparator<DiaryData?> {

        override fun compare(o1: DiaryData?, o2: DiaryData?): Int {
            return o1!!.day.compareTo(o2!!.day)
        }
    }
}