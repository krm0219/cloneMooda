package com.krm0219.mooda.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.krm0219.mooda.R
import com.krm0219.mooda.data.CalendarData1
import com.krm0219.mooda.databinding.ItemDialogCalendarBinding
import com.krm0219.mooda.viewmodel.DiaryViewModel

class DialogCalendarAdapter(private var viewModel: DiaryViewModel) : RecyclerView.Adapter<DialogCalendarAdapter.ViewHolder>() {

    var dates: List<CalendarData1> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemDialogCalendarBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_dialog_calendar
    }

    override fun getItemCount(): Int {

        return dates.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(viewModel, position)
    }

    inner class ViewHolder(private val binding: ItemDialogCalendarBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: DiaryViewModel, position: Int) {

            val data = dates[position]

            binding.position = position
            binding.data = data
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    fun setDateList(dates: List<CalendarData1>) {

        Log.e("krm0219", "setDateList")
        this.dates = dates
        notifyDataSetChanged()
    }
}