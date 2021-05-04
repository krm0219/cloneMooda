package com.krm0219.mooda.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.krm0219.mooda.R
import com.krm0219.mooda.data.MonthData
import com.krm0219.mooda.databinding.ItemMainMonthBinding
import com.krm0219.mooda.viewmodel.MainViewModel

// Month 단위로 움직임
class MainAdapter(private var viewModel: MainViewModel) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var months: List<MonthData> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMainMonthBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_main_month
    }

    override fun getItemCount(): Int {
        return months.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(viewModel, position)
    }

    inner class ViewHolder(private val binding: ItemMainMonthBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: MainViewModel, position: Int) {

            val month = months[position]

            Log.e("ViewPager", "position $position // data ${months[position].month}")
            binding.position = position
            binding.data = month
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }


    companion object MonthDiffUtil : DiffUtil.ItemCallback<MonthData>() {

        override fun areItemsTheSame(oldItem: MonthData, newItem: MonthData): Boolean {
            // 각 아이템들의 고유한 값을 비교해야 한다.
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MonthData, newItem: MonthData): Boolean {
            return oldItem == newItem
        }
    }

    fun setMonthList(months: List<MonthData>) {

        this.months = months
        notifyDataSetChanged()
    }
}