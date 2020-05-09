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
import androidx.room.Database
import androidx.room.Room
import com.example.goalsetting.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var recyclerViewVar: RecyclerView? = null
    var categoryList: MutableList<Category> = mutableListOf()
    lateinit var categoryAdapter: CategoryAdapter
    private lateinit var db: AppDatabase

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

        if(activity?.applicationContext != null) {
            db = Room.databaseBuilder(
                activity!!.applicationContext,
                AppDatabase::class.java, "GoalSettingDB"
            ).allowMainThreadQueries().build()
        }

        val goals: List<GoalDB> = db!!.goalDao().all
        val categories: List<CategoryDB> = db!!.categoryDao().all

        for(category in categories) {

            var goalList: MutableList<Goal> = mutableListOf<Goal>()

            for(goal in goals) {

                if(goal.categoryId == category.id) {

                    var newGoal: Goal = object : Goal(
                        goal.goalTitle,
                        goal.startValue,
                        goal.endValue,
                        goal.progress,
                        goal.id,
                        category.id
                    ) {}
                    goalList.add(newGoal)
                }
            }
            var newCategory: Category = object : Category(category.categoryTitle, goalList, category.id){}
            categoryList.add(newCategory)
        }
    }

    private fun initCategoryRecyclerView() {
        recyclerViewVar?.layoutManager = LinearLayoutManager(activity)
        categoryAdapter = CategoryAdapter(categoryList, this, db)
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
