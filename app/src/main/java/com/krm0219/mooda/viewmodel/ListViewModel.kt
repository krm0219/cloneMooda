package com.krm0219.mooda.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.util.BaseViewModel
import com.krm0219.mooda.util.Event


// ViewModel에서 Context가 필요한 경우 AndroidViewModel 클래스를 상속받아
// Application 객체를 넘길 것을 권장 !!
// Context를 갖고 있으면 메모리 누수의 원인이 된다
class ListViewModel(application: Application, id: Long) : BaseViewModel(application) {

    private val _diaryDataList = MutableLiveData<List<DiaryData>>()
    val diaryDataList: MutableLiveData<List<DiaryData>>
        get() = _diaryDataList

    private val _position = MutableLiveData<Int>()
    val position: MutableLiveData<Int>
        get() = _position


    private val _editEvent = MutableLiveData<Event<Long>>()
    val editEvent: MutableLiveData<Event<Long>>
        get() = _editEvent

    private val _deleteEvent = MutableLiveData<Event<Long>>()
    val deleteEvent: MutableLiveData<Event<Long>>
        get() = _deleteEvent

    private val _backEvent = MutableLiveData<Event<Boolean>>()
    val backEvent: MutableLiveData<Event<Boolean>>
        get() = _backEvent


    private val _dialogCloseEvent = MutableLiveData<Event<Boolean>>()
    val dialogCloseEvent: MutableLiveData<Event<Boolean>>
        get() = _dialogCloseEvent


    private val diaryId: Long = id
    private var deleteId = 0L

    init {

    }


    fun setDiaryData() {

        val diaryList = repository.selectAll()

        _diaryDataList.value = diaryList


        for (index in diaryList.indices) {

            if (diaryList[index].id == diaryId) {

                _position.value = index
                break
            }
        }
    }

    fun clickEdit(position: Int) {

        _editEvent.value = Event(_diaryDataList.value!![position].id)
    }

    fun clickDelete(position: Int) {

        Log.e("krm0219", "clickDelete $position")
        deleteId = _diaryDataList.value!![position].id
        _deleteEvent.value = Event(deleteId)
    }

    fun clickBack() {

        _backEvent.value = Event(true)
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