package com.example.amatiberkah.view.explore

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amatiberkah.R
import com.example.amatiberkah.databinding.ActivityExploreBinding
import com.example.amatiberkah.model.remote.response.*
import com.example.amatiberkah.view.exploreVillageDetail.ExploreVillageDetailActivity
import com.example.amatiberkah.view.adapter.CourseAdapter
import com.example.amatiberkah.view.adapter.VillageAdapter
import com.example.amatiberkah.view.detail.DetailCourseActivity
import com.example.amatiberkah.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ExploreActivity : AppCompatActivity() {


    lateinit var binding : ActivityExploreBinding
    private val viewModel: ExploreViewModel by viewModels()
    companion object {
        const val EXTRA_TOKEN = "extra_token"
        const val ROLE = "role"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMain()

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.courseReview.layoutManager = layoutManager

        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.villageReview.layoutManager = layoutManager2


        binding.logoutButton.setOnClickListener {
            logOutHandler()
        }
    }



    private fun setListVillage(listVillage: List<ListVillageResponse>){
        val adapter = VillageAdapter(listVillage) {village ->
            val intent = Intent(this, ExploreVillageDetailActivity::class.java)
            intent.putExtra(ExploreVillageDetailActivity.VILLAGE_ID, village.id)
            startActivity(intent)
        }
        binding.villageReview.adapter = adapter
    }

    private fun setupMain() {
        lifecycleScope.launch {
            viewModel.getToken().collect { tokenUser ->
                if (tokenUser !== null) {
                    val token = "Bearer $tokenUser"
                    viewModel.getAllModule(
                        token
                    ).collectLatest { result ->
                        if (result.isSuccess) {
                            val courseResponse = result.getOrThrow()
                            setListCourse(courseResponse.data)
                        } else {
                            Log.d("ERROR LIST","Home Failed: ${result.exceptionOrNull()?.message}")
                            showToast("Home Failed: ${result.exceptionOrNull()?.message}")
                        }
                    }

                    viewModel.getVillages(
                        token
                    ).collectLatest { result ->
                        if(result.isSuccess) {
                            val villageResponse = result.getOrThrow()
                            Log.d("Data LIST Village","Data: $villageResponse")
                            setListVillage(villageResponse.data)
                        } else {
                            Log.d("ERROR LIST","Home Failed: ${result.exceptionOrNull()?.message}")
                            showToast("Home Failed: ${result.exceptionOrNull()?.message}")
                        }
                    }

                    viewModel.getUserDataName().collect {name ->
                        binding.statusBox.text = name
                    }

//                    viewModel.getUserDataName().collect {name ->
//                        binding.usernameBox.text = name
//                    }
                }
                else {
                    val intent = Intent(this@ExploreActivity, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setListCourse(listCourse: List<Data>?){
        val adapter = CourseAdapter((listCourse ?: emptyList()))
        Log.d("CourseAdapter", "ListCourse: $listCourse")
        binding.courseReview.adapter = adapter

        adapter.setOnItemClickCallback(object : CourseAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Data) {
                val intentDetail = Intent(this@ExploreActivity, DetailCourseActivity::class.java)
                intentDetail.putExtra(DetailCourseActivity.EXTRA_COURSE, data.id)
                startActivity(intentDetail)
            }
        })
    }

    private fun logOutHandler() {
        lifecycleScope.launch {
            viewModel.logOut().collect { result ->
                if (result.isSuccess) {
                    showToast("Logout Successful")
                    val intent = Intent(this@ExploreActivity, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    showToast("Logout Failed: ${result.exceptionOrNull()?.message}")
                }
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}