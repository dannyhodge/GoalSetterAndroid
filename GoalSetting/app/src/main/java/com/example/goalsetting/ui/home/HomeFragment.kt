package com.example.goalsetting.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goalsetting.Category
import com.example.goalsetting.Goal
import com.example.goalsetting.GoalAdapter
import com.example.goalsetting.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var  recyclerViewVar: RecyclerView? = null
    private var goalList: List<Goal>? = null
    private lateinit var adapter: GoalAdapter

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerViewVar = view.recyclerView
        initData()
        initRecyclerView()
        return view
    }

    fun initData() {
        val goals = listOf(
            Goal("Run a 20 minute 5K", 25.0, 20.0, 25.0 ),
            Goal("Do 50 pushups", 30.0, 50.0, 35.0 ),
            Goal("Squat 100KG", 40.0, 100.0, 60.0 ),
            Goal("Deadlift 200KG", 70.0, 200.0, 80.0 ),
            Goal("Bench Press 300KG", 100.0, 300.0, 120.0 )
        )

        goalList = goals;
        val category: Category = object  : Category( "Fitness", goals){}

        Log.d("log", "added data")
    }

    private fun initRecyclerView() {
        val goalAdapter = object : GoalAdapter(goalList) {}

        recyclerViewVar?.layoutManager = LinearLayoutManager(activity)
        adapter = GoalAdapter(goalList)
        recyclerViewVar?.adapter = adapter
    }


}
