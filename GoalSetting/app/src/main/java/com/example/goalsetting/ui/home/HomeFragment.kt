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
import com.example.goalsetting.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var recyclerViewVar: RecyclerView? = null
    private var goalList: List<Goal>? = null
    private var categoryList: List<Category>? = null
    private lateinit var categoryAdapter: CategoryAdapter

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
        initCategoryRecyclerView()
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

        val goals2 = listOf(
            Goal("Cook 3 new meals", 0.0, 3.0, 1.0 ),
            Goal("Lose 30 pounds", 0.0, 30.0, 25.0 )
        )

        goalList = goals;
        val fitnessCategory: Category = object  : Category( "Fitness", goals){}
        val dietCategory: Category = object  : Category( "Diet", goals2){}

        categoryList = listOf(fitnessCategory, dietCategory, dietCategory, dietCategory, dietCategory)
        Log.d("log", "added data")
    }

    private fun initCategoryRecyclerView() {
        recyclerViewVar?.layoutManager = LinearLayoutManager(activity)
        categoryAdapter = CategoryAdapter(categoryList)
        recyclerViewVar?.adapter = categoryAdapter
    }

}
