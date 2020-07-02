package com.sosure.interviewtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sosure.interviewtest.depencyinj.DaggerApiComponent
import com.sosure.interviewtest.model.People
import com.sosure.interviewtest.model.PeopleService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class NameViewModel : ViewModel() {
    val people = MutableLiveData<People>()
    val peopleLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()

    @Inject
    lateinit var peopleService: PeopleService

    init{
        DaggerApiComponent.create().inject(this)
    }

    fun refresh(){
        fetchPeople()
    }
    private fun fetchPeople(){

        loading.value = true

        disposable.add(
            peopleService.getPeople()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<People>(){
                    override fun onSuccess(value: People) {
                        people.value = value
                        peopleLoadError.value = false
                        loading.value = false
                        Timber.d("Name: ${value.name}")
                    }

                    override fun onError(e: Throwable) {
                        peopleLoadError.value = true
                        loading.value = false
                        Timber.d("Error: ${e.message}")
                    }

                })
        )
    }

}