package com.example.goalsetting;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalVH> {

    private static final String TAG = "GoalAdapter";
    List<Goal> goalList;

    public GoalAdapter(List<Goal> goalList) {
        this.goalList = goalList;
    }

    @NonNull
    @Override
    public GoalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_row, parent, false);
        return new GoalVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalVH holder, int position) {

        Goal goal = goalList.get(position);
        holder.titleTextView.setText(goal.getTitle());
        holder.currentValueTextView.setText("Progress: " + goal.getProgress());

        boolean isExpanded = goalList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    class GoalVH extends RecyclerView.ViewHolder {

        private static final String TAG = "GoalVH";

        ConstraintLayout expandableLayout;
        TextView titleTextView, currentValueTextView;
        LinearLayout ll;

        public GoalVH(@NonNull final View itemView) {
            super(itemView);

            ll = itemView.findViewById(R.id.linearTitle);
            titleTextView = itemView.findViewById(R.id.textTitle);
            currentValueTextView = itemView.findViewById(R.id.textCurrentValue);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Goal goal = goalList.get(getAdapterPosition());
                    goal.setExpanded(!goal.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                    hideKeyboard((Activity) view.getContext());

                }
            });
        }
        public void hideKeyboard(Activity activity) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}