package com.example.t_mobileapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_mobileapp.databinding.ActivityMainBinding
import com.example.t_mobileapp.R
import com.example.t_mobileapp.adapter.UserAdapter
import com.example.t_mobileapp.model.Item
import com.example.t_mobileapp.model.User
import com.example.t_mobileapp.viewmodel.GitViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Need to use Databinding util to set the content view instead of the
        //Activities setContentView

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        //setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(GitViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.userInfo.observe(this, Observer<User> { user ->
            displayUsers(user.items)
        })

    }

     fun displayUsers(users: List<Item>) {
        val adapter = UserAdapter(users)
        main_recyclerview.adapter = adapter
        val linear = LinearLayoutManager(this)
        main_recyclerview.layoutManager = linear

    }


}
