package com.krm0219.mooda.diary

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.krm0219.mooda.R
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.util.KUtil

class DetailViewPagerAdapter(private var context: Context, var diaryDataList: ArrayList<DiaryData>) : RecyclerView.Adapter<DetailViewPagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_detail_diary, parent, false)
        )

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(diaryDataList[position], position)
    }

    override fun getItemCount(): Int = diaryDataList.size


    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int, method: String)
    }

    // 리스너 객체 참조를 저장하는 변수
    private var mListener: OnItemClickListener? = null

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private val text_diary_day: TextView = itemView.findViewById(R.id.text_diary_day)
        private val text_diary_day_of_the_week: TextView = itemView.findViewById(R.id.text_diary_day_of_the_week)
        private val img_diary_emoji: ImageView = itemView.findViewById(R.id.img_diary_emoji)
        private val text_diary_title: TextView = itemView.findViewById(R.id.text_diary_title)
        private val img_diary_highlighter: ImageView = itemView.findViewById(R.id.img_diary_highlighter)
        private val text_diary_message: TextView = itemView.findViewById(R.id.text_diary_message)

        private val btn_detail_edit: FrameLayout = itemView.findViewById(R.id.btn_detail_edit)
        private val btn_detail_delete: FrameLayout = itemView.findViewById(R.id.btn_detail_delete)


        fun bind(diaryDataData: DiaryData, position: Int) {

            text_diary_day.text = diaryDataData.day.toString()
            text_diary_day_of_the_week.text = diaryDataData.dayOfWeek

            val resourceId = context.resources.getIdentifier("emoji${diaryDataData.emoji}", "drawable", context.packageName)
            img_diary_emoji.setBackgroundResource(resourceId)

            text_diary_title.text = diaryDataData.title
            text_diary_message.text = diaryDataData.message
            setHighLighter()

            btn_detail_edit.setOnClickListener {

                if (mListener != null) {
                    mListener!!.onItemClick(it, position, "Edit")
                }
            }

            btn_detail_delete.setOnClickListener {

                if (mListener != null) {
                    mListener!!.onItemClick(it, position, "Delete")
                }
            }
        }

        private fun setHighLighter() {

            val drawListener = ViewTreeObserver.OnDrawListener {

                val width = text_diary_title.width + 100
                Log.e("krm0219", "width1  $width")

                val params = RelativeLayout.LayoutParams(width, KUtil.dpToPx(context, 30f).toInt())
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                img_diary_highlighter.layoutParams = params
            }


            text_diary_title.viewTreeObserver.addOnDrawListener(drawListener)
            text_diary_title.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    text_diary_title.viewTreeObserver.removeOnDrawListener(drawListener)
                    text_diary_title.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val width = text_diary_title.width + 50
                    Log.e("krm0219", "width  $width")

                    val params = RelativeLayout.LayoutParams(width, KUtil.dpToPx(context, 30f).toInt())
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                    img_diary_highlighter.layoutParams = params
                }
            })
        }
    }
}