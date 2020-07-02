package com.sosure.interviewtest.model

import io.reactivex.Single
import retrofit2.http.GET

interface PeopleApi {

        @GET("api/people/4/")
        fun getPeople(): Single<People>

}