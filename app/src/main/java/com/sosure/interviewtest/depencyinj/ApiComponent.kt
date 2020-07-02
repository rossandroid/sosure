package com.sosure.interviewtest.depencyinj

import com.sosure.interviewtest.model.PeopleService
import com.sosure.interviewtest.viewmodel.NameViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: PeopleService)
    fun inject(viewModel: NameViewModel)
}