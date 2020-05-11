package com.example.goalsetting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.goalsetting.ui.home.HomeFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH> {

    private static final String TAG = "CategoryAdapter";
    private List<Category> categoryList;
    private int currentId = 0;
    public GoalAdapter adapter;
    public List<GoalAdapter> adapters = new ArrayList<>();
    private HomeFragment fragment;
    private List<Goal> allGoals = new ArrayList<>();
    AppDatabase db;
    RecyclerView recyclerView;

    enum ButtonsState {
        GONE,
        LEFT_VISIBLE,
        RIGHT_VISIBLE
    }

    ItemTouchHelper itemTouchHelper;
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback;

    public void createTouchHelper(GoalAdapter touchAdapter) {

        itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Log.d("move", "moved");
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT) {
                    Log.d("move", "removed item");
                    AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
                    builder.setMessage("Are you sure you want to delete?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    //match view title to allgoals list?
                                    GoalAdapter goalAdapter = touchAdapter;  //MAKE ME NOT HARD CODED
                                    Goal goal = goalAdapter.goalList.get(viewHolder.getAdapterPosition());
                                    GoalDB goalToDelete = db.goalDao().findByName(goal.getTitle());
                                    db.goalDao().delete(goalToDelete);
                                    goalAdapter.goalList.remove(goalAdapter.goalList.get(viewHolder.getAdapterPosition()));
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    notifyDataSetChanged();
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }

        };
        itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
    }


    public CategoryAdapter(List<Category> categoryList, HomeFragment fragment, AppDatabase db) {
        this.categoryList = categoryList;
        this.fragment = fragment;
        this.db = db;
        currentId = 0;
        for(int i = 0; i < categoryList.size(); i++) {
            allGoals.addAll(categoryList.get(i).getGoals());
        }
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent, false);
        return new CategoryVH(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, int position) {
        Category category = categoryList.get(position);
        Log.d("Log", "category changed");
        holder.categoryTitle.setText(category.getTitle());
        int y = holder.itemView.getContext().getResources().getIdentifier(String.valueOf(R.id.tempid), null, null);

        if (holder.categoryLL.getId() == y) {
            holder.categoryLL.setId(++currentId);

            double x = (double) holder.itemView.getId() / 4;
            double deciX = x - ((int) x);
            if (deciX == 0.25) {
                holder.titleCard.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorTwo));
            } else if (deciX == 0.5) {
                holder.titleCard.setCardBackgroundColor(holder.itemView.getContext().getColor(R.color.colorThree));
            }
            if (deciX == 0.75) {
                holder.titleCard.setCardBackgroundColor(holder.itemView.getContext().getColor(R.color.colorFour));
            }
            if (deciX == 0) {
                holder.titleCard.setCardBackgroundColor(holder.itemView.getContext().getColor(R.color.colorFive));
            }
        }
        holder.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        adapter = new GoalAdapter(category.getGoals(), categoryList, fragment, db);
        adapters.add(adapter);
        createTouchHelper(adapter);
        recyclerView = holder.recyclerViewCategory;
        itemTouchHelper.attachToRecyclerView(holder.recyclerViewCategory);
        holder.recyclerViewCategory.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryVH extends RecyclerView.ViewHolder {

        private static final String TAG = "CategoryVH";

        TextView categoryTitle;
        LinearLayout categoryLL;
        RecyclerView recyclerViewCategory;
        CardView titleCard;

        public CategoryVH(@NonNull final View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryLL = itemView.findViewById(R.id.tempid);
            recyclerViewCategory = itemView.findViewById(R.id.recyclerViewCategory);
            titleCard = itemView.findViewById(R.id.titleCard);
        }
    }


}