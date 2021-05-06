package com.krm0219.mooda.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.util.BaseViewModel
import com.krm0219.mooda.util.Event
import com.krm0219.mooda.util.KUtil
import com.krm0219.mooda.util.Preferences


// ViewModel에서 Context가 필요한 경우 AndroidViewModel 클래스를 상속받아
// Application 객체를 넘길 것을 권장 !!
// Context를 갖고 있으면 메모리 누수의 원인이 된다
class DeveloperModeViewModel(application: Application) : BaseViewModel(application) {

    private val _deleteEvent = MutableLiveData<Event<Boolean>>()
    val deleteEvent: MutableLiveData<Event<Boolean>>
        get() = _deleteEvent


    fun deleteDB() {

        repository.deleteAllDiary()

        Log.e("ViewModel", "deleteAllDiary")
        _deleteEvent.value = Event(true)
    }
}