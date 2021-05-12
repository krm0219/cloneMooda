package com.krm0219.mooda.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.util.BaseViewModel
import com.krm0219.mooda.util.Event
import com.krm0219.mooda.view.ListActivity


// ViewModel에서 Context가 필요한 경우 AndroidViewModel 클래스를 상속받아
// Application 객체를 넘길 것을 권장 !!
// Context를 갖고 있으면 메모리 누수의 원인이 된다
class ListViewModel(application: Application, private val id: Long) : BaseViewModel(application) {

    private val _diaryDataList = MutableLiveData<List<DiaryData>>()
    val diaryDataList: MutableLiveData<List<DiaryData>>
        get() = _diaryDataList

    private val _position = MutableLiveData<Int>()
    val position: MutableLiveData<Int>
        get() = _position

    private val _title = MutableLiveData<String>()
    val title: MutableLiveData<String>
        get() = _title


    private val _backEvent = MutableLiveData<Event<Boolean>>()
    val backEvent: MutableLiveData<Event<Boolean>>
        get() = _backEvent

    private val _editEvent = MutableLiveData<Event<Long>>()
    val editEvent: MutableLiveData<Event<Long>>
        get() = _editEvent

    private val _deleteEvent = MutableLiveData<Event<Long>>()
    val deleteEvent: MutableLiveData<Event<Long>>
        get() = _deleteEvent


    private val _dialogCloseEvent = MutableLiveData<Event<Boolean>>()
    val dialogCloseEvent: MutableLiveData<Event<Boolean>>
        get() = _dialogCloseEvent


    private var deleteId = 0L


    fun setDiaryData(called: String) {

        val diaryList = repository.selectAll()
        _diaryDataList.value = diaryList

        if (called == ListActivity.CALLED_RESUME) {

            // 클릭한 ITEM ID 찾아서 세팅
            for (index in diaryList.indices) {

                if (diaryList[index].id == id) {

                    _position.value = index
                    break
                }
            }
        } else {

            // 삭제한 ITEM 위치 계산해서 세팅
            var pos = _position.value!!.minus(1)
            if (pos < diaryList.size) {

                pos = 0
            }

            _position.value = pos
        }

        setTitle(_position.value!!)
    }

    fun setTitle(position: Int) {

        val diary = repository.selectDiaryById(_diaryDataList.value!![position].id)
        _title.value = "${diary.getMonthString()} ${diary.getFormatYear()}"
    }


    // Top Button
    fun clickBack() {

        _backEvent.value = Event(true)
    }

    //
    fun clickEdit(position: Int) {

        _editEvent.value = Event(_diaryDataList.value!![position].id)
    }

    fun clickDelete(position: Int) {

        deleteId = _diaryDataList.value!![position].id
        _deleteEvent.value = Event(deleteId)
    }


    // Delete Dialog
    fun clickDialogClose(isDelete: Boolean) {

        if (isDelete) {

            Log.e("krm0219", "Delete  $deleteId")
            repository.deleteDiaryById(deleteId)
        }
        
        _dialogCloseEvent.value = Event(isDelete)
    }
}