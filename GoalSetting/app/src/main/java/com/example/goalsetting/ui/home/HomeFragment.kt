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
import com.example.goalsetting.CategoryAdapter
import com.example.goalsetting.Goal
import com.example.goalsetting.R
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
            Goal("Run a 20 minute 5K", 25.0, 20.0, 24.0, 0 ),
            Goal("Do 50 pushups", 30.0, 50.0, 45.0, 1 ),
            Goal("Squat 100KG", 40.0, 100.0, 60.0, 2 ),
            Goal("Deadlift 200KG", 70.0, 200.0, 80.0, 3 ),
            Goal("Bench Press 300KG", 100.0, 300.0, 270.0, 4 )
        )

        val goals2 = listOf(
            Goal("Cook 3 new meals", 0.0, 3.0, 1.0, 5 ),
            Goal("Lose 30 pounds", 0.0, 30.0, 25.0, 6 )
        )

        val goals3 = listOf(
            Goal("Read 8 books", 0.0, 8.0, 1.0, 7 )
        )

        goalList = goals;
        val fitnessCategory: Category = object  : Category( "Fitness", goals){}
        val dietCategory: Category = object  : Category( "Diet", goals2){}
        val readingCategory: Category = object  : Category( "Reading", goals3){}

        categoryList = listOf(fitnessCategory, dietCategory, readingCategory)
        Log.d("log", "added data")
    }

    private fun initCategoryRecyclerView() {
        recyclerViewVar?.layoutManager = LinearLayoutManager(activity)
        categoryAdapter = CategoryAdapter(categoryList, this)
        recyclerViewVar?.adapter = categoryAdapter
    }

    fun closeAllExpandables(id: Int) {
        for (i in categoryList!!) {
            for (j in i.goals) {
                if(id != j.id)
                j.isExpanded = false;
            }
        }
        categoryAdapter.notifyDataSetChanged();
    }
}
