package com.krm0219.mooda.util

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krm0219.mooda.data.Repository
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel protected constructor(application: Application) : AndroidViewModel(application) {

    protected val repository: Repository = Repository(application)


    private val compositeDisposable = CompositeDisposable()


    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    protected fun showProgress() {

        _isLoading.value = true
    }

    protected fun hideProgress() {

        _isLoading.value = false
    }
}