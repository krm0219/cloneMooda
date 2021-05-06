package com.krm0219.mooda.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.krm0219.mooda.R
import com.krm0219.mooda.databinding.ActivityDeveloperModeBinding
import com.krm0219.mooda.viewmodel.DeveloperModeViewModel


class DeveloperModeActivity : AppCompatActivity() {
    val tag = "DiaryActivity"

    var emoji = 0
    lateinit var binding: ActivityDeveloperModeBinding
    val viewModel: DeveloperModeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_developer_mode)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        viewModel.deleteEvent.observe(this, Observer {

            it.getContentIfNotHandled()?.let {

                Toast.makeText(this@DeveloperModeActivity, "Delete Success", Toast.LENGTH_SHORT).show()
            }
        })
    }
}