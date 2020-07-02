package com.sosure.interviewtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sosure.interviewtest.model.People
import com.sosure.interviewtest.model.PeopleService
import com.sosure.interviewtest.viewmodel.NameViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class NameViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var peopleService: PeopleService

    @InjectMocks
    var nameViewModel = NameViewModel()

    private var testSingle: Single<People>? = null

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }
    @Test
    fun getPeopleSuccess(){
        val people = People("Test Name")

        testSingle = Single.just(people)

        `when`(peopleService.getPeople()).thenReturn(testSingle)

        nameViewModel.refresh()

        Assert.assertEquals("Test Name", nameViewModel.people.value?.name)
        Assert.assertEquals(false, nameViewModel.peopleLoadError.value)
        Assert.assertEquals(false, nameViewModel.loading.value)

    }

    @Test
    fun getMenuFail(){
        testSingle = Single.error(Throwable())

        `when`(peopleService.getPeople()).thenReturn(testSingle)

        nameViewModel.refresh()

        Assert.assertEquals(true, nameViewModel.peopleLoadError.value)
        Assert.assertEquals(false, nameViewModel.loading.value)

    }

    @Before
    fun setUpRxSchedulers(){
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor {it.run() }, true)
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate}
    }
}