package com.sosure.interviewtest.model
import com.sosure.interviewtest.depencyinj.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class PeopleService {
    @Inject
    lateinit var api: PeopleApi

    init{
        DaggerApiComponent.create().inject(this)
    }

    fun getPeople(): Single<People> {
        return api.getPeople()
    }
}