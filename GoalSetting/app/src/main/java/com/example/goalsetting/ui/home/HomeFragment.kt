package com.example.goalsetting.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.goalsetting.*
import com.example.goalsetting.ui.additem.AddItemFragment
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var recyclerViewVar: RecyclerView? = null
    var categoryList: MutableList<Category> = mutableListOf()
    lateinit var categoryAdapter: CategoryAdapter
    private lateinit var db: AppDatabase
    private lateinit var speedDialView: SpeedDialView

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

        speedDialView = view.findViewById<SpeedDialView>(R.id.speedDial)

        speedDialView.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_no_label, R.drawable.ic_add_white_24dp)
                .setFabBackgroundColor(getResources().getColor(R.color.colorOne))
                .setLabel("Add Category")
                .create())


        speedDialView.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_no_label2, R.drawable.ic_add_white_24dp)
                .setFabBackgroundColor(getResources().getColor(R.color.colorOne))
                .setLabel("Add Goal")
                .create())

        speedDialView.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
            when (actionItem.id) {
                R.id.fab_no_label2 -> {
                    speedDialView.close() // To close the Speed Dial with animation
                    val intent = Intent(activity, addnewgoal::class.java)
                    startActivity(intent)
                    return@OnActionSelectedListener true // false will close it without animation
                }
            }
            true // To keep the Speed Dial open
        })

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

        UpdateGoalList();
    }

    fun UpdateGoalList() {
        val goals: List<GoalDB> = db!!.goalDao().all
        val categories: List<CategoryDB> = db!!.categoryDao().all
        categoryList = mutableListOf()
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
