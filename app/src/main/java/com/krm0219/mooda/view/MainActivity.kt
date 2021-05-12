package com.krm0219.mooda.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.krm0219.mooda.R
import com.krm0219.mooda.databinding.ActivityMainBinding
import com.krm0219.mooda.view.adapter.MainAdapter
import com.krm0219.mooda.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()

    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setRecyclerView()
        viewModel.setMonthData()

        viewModel.addEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { position ->

                val intent = Intent(this, DiaryActivity::class.java)
                intent.putExtra(DiaryActivity.EXTRA_METHOD, DiaryActivity.METHOD_ADD)
                intent.putExtra(DiaryActivity.EXTRA_EMOJI, position)
                addDiaryActivity.launch(intent)
            }
        })


        viewModel.itemClickEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { id ->

                viewModel.setMonthPosition(id)

                val intent = Intent(this, ListActivity::class.java)
                intent.putExtra(ListActivity.EXTRA_DIARY_ID, id)
                startActivity(intent)
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
            }
        })


        viewModel.developerEvent.observe(this, Observer {

            it.getContentIfNotHandled()?.let {

                val intent = Intent(this, DeveloperModeActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()

        Log.e("MainActivity", "onResume")
    }


    private fun setRecyclerView() {

        adapter = MainAdapter(viewModel)
        binding.viewPagerMain.adapter = adapter


        viewModel.monthDataList.observe(this, Observer {

            adapter.setMonthList(it)
        })

        viewModel.monthPosition.observe(this, Observer {

            Log.e(TAG, " Month Position $it")
            view_pager_main.setCurrentItem(it, false)
        })
    }

    private val addDiaryActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        if (result.resultCode == Activity.RESULT_OK) {

            val addId = result.data?.getLongExtra(DiaryActivity.EXTRA_DIARY_ID, -1)
            addId?.let {

                Log.e(TAG, "requestActivity  $addId")
                viewModel.setMonthPosition(it)
                viewModel.setMonthData()
            }
        }
    }

    
    companion object {

        const val TAG = "MainActivity"
    }
}