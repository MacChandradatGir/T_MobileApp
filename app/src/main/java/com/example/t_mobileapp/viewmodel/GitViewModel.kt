package com.example.t_mobileapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.t_mobileapp.model.User
import com.example.t_mobileapp.network.GitFactory
import com.example.t_mobileapp.view.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers



class GitViewModel : ViewModel(){

    private val gitFactory = GitFactory()

     var mainActivity: MainActivity = MainActivity()

    private val compositeDisposable = CompositeDisposable()
    private lateinit var viewModel: GitViewModel

    //We will use LiveData to hold results from the API Call
    val userInfo : MutableLiveData<User> = MutableLiveData()

    //NOTE:  Create a function that uses the textwatcher method "onTextChanged"
    //         to handle the action of the user entering input

    fun onUserNameTextChanged(currentInput: CharSequence, start: Int, before: Int,
                              count: Int){
        val currentUserNameEntered = currentInput.toString()
        makeApiCall(currentUserNameEntered)
    }


    fun getUsers(userName : String): Observable<User> {
        return gitFactory.getUsers(userName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }


    fun makeApiCall(searchEditText: String) {

        compositeDisposable.add(
            getUsers("${searchEditText}")
                .subscribe({user ->
                    userInfo.postValue(user)
                }, {throwable ->
                    Log.e("TAG_ERROR", throwable.toString())
                })

        )
    }



}