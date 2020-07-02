package com.sosure.interviewtest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sosure.interviewtest.R
import com.sosure.interviewtest.model.People
import com.sosure.interviewtest.viewmodel.NameViewModel
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(NameViewModel::class.java)

        main_button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.refresh()
                observeViewModel()
            }})

    }
    fun observeViewModel(){
        viewModel.people.observe(this, Observer{people ->
            people?.let{
                getPeople(people)}
        })
        viewModel.peopleLoadError.observe(this, Observer{ isError ->
            isError?.let{
                if(it){
                    data_error.visibility = View.VISIBLE
                    main_button.visibility = View.VISIBLE
                } }
        })
        viewModel.loading.observe(this, Observer{ isLoading ->
            isLoading?.let{
                loading.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    data_error.visibility = View.GONE
                    people_data.visibility = View.GONE
                    main_button.visibility = View.GONE
                }}
        })
    }

    fun getPeople(people: People){
        people_data.text = people.name
        people_data.visibility = View.VISIBLE

    }
}
