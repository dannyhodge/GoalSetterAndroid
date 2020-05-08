package com.example.goalsetting;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goalsetting.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalVH> {

    private static final String TAG = "GoalAdapter";
    List<Goal> goalList;
    List<Category> categoryList;
    List<Goal> allGoals = new ArrayList<>();
    private HomeFragment fragment;

    public GoalAdapter(List<Goal> goalList, List<Category> categoryList, HomeFragment fragment) {
        this.goalList = goalList;
        this.categoryList = this.categoryList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public GoalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_row, parent, false);
        return new GoalVH(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull GoalVH holder, int position) {

        Goal goal = goalList.get(position);
        holder.titleTextView.setText(goal.getTitle());
        holder.currentValueTextView.setText("" + (int)goal.getEndValue());
        holder.progressText.setText("" + (int)goal.getStartValue());

        if(goal.getStartValue() > goal.getEndValue()) {
            holder.progressBar.setMax((int) goal.getStartValue() - (int) goal.getEndValue());
            holder.progressBar.setProgress((int) goal.getStartValue() - (int) goal.getProgress());
        }
        else {
            holder.progressBar.setMax((int) goal.getEndValue() - (int) goal.getStartValue());
            holder.progressBar.setProgress((int) goal.getProgress() - (int) goal.getStartValue());
        }

        boolean isExpanded = goal.isExpanded();

        if(isExpanded) {
            holder.expandableLayout.setVisibility(View.VISIBLE);
            holder.expandableLayout.setMaxHeight(150);
        }
        else {
            holder.expandableLayout.setVisibility(View.INVISIBLE);
            holder.expandableLayout.setMaxHeight(0);
        }
      //  holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    class GoalVH extends RecyclerView.ViewHolder {

        private static final String TAG = "GoalVH";

        ConstraintLayout expandableLayout;
        TextView titleTextView, currentValueTextView, progressText;
        LinearLayout ll;
        ProgressBar progressBar;

        public GoalVH(@NonNull final View itemView) {
            super(itemView);

            ll = itemView.findViewById(R.id.linearTitle);
            titleTextView = itemView.findViewById(R.id.textTitle);
            currentValueTextView = itemView.findViewById(R.id.textCurrentValue);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            progressBar = itemView.findViewById(R.id.progressBar);
            progressText = itemView.findViewById(R.id.textProgress);

            ll.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {

                    Goal goal1 = goalList.get(getAdapterPosition());

                    fragment.closeAllExpandables(goal1.getId());

                    goal1.setExpanded(!goal1.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                    hideKeyboard((Activity) view.getContext());
                }
            });
        }
        public void hideKeyboard(Activity activity) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}