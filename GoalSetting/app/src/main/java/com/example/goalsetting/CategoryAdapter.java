package com.example.goalsetting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH> {

    private static final String TAG = "CategoryAdapter";
    private List<Category> categoryList;
    private int currentId = 0;
    private GoalAdapter adapter;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent, false);
        return new CategoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryTitle.setText(category.getTitle());
        holder.categoryLL.setId(currentId++);

        holder.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        adapter = new GoalAdapter(category.getGoals());
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

        public CategoryVH(@NonNull final View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryLL = itemView.findViewById(R.id.tempid);
            recyclerViewCategory = itemView.findViewById(R.id.recyclerViewCategory);
        }
    }
}