package com.example.goalsetting;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.goalsetting.ui.home.HomeFragment;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH> {

    private static final String TAG = "CategoryAdapter";
    private List<Category> categoryList;
    private int currentId = 0;
    public GoalAdapter adapter;
    private HomeFragment fragment;
    AppDatabase db;

    public CategoryAdapter(List<Category> categoryList, HomeFragment fragment, AppDatabase db) {
        this.categoryList = categoryList;
        this.fragment = fragment;
        this.db = db;
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
        }

        double x = (double)holder.itemView.getId() / 4;
        double deciX = x - ((int) x);
        if(deciX == 0.25) {
            holder.titleCard.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorTwo));
        }
        else if(deciX == 0.5) {
            holder.titleCard.setCardBackgroundColor(holder.itemView.getContext().getColor(R.color.colorThree));
        }
        if(deciX == 0.75) {
            holder.titleCard.setCardBackgroundColor(holder.itemView.getContext().getColor(R.color.colorFour));
        }
        if(deciX == 0) {
            holder.titleCard.setCardBackgroundColor(holder.itemView.getContext().getColor(R.color.colorFive));
        }

        holder.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        adapter = new GoalAdapter(category.getGoals(), categoryList, fragment, db);
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