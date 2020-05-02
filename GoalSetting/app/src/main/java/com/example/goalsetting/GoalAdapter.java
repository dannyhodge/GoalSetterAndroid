package com.example.goalsetting;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.MovieVH> {

    private static final String TAG = "GoalAdapter";
    List<Goal> goalList;

    public GoalAdapter(List<Goal> goalList) {
        this.goalList = goalList;
    }

    @NonNull
    @Override
    public MovieVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_row, parent, false);
        return new MovieVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVH holder, int position) {

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

    class MovieVH extends RecyclerView.ViewHolder {

        private static final String TAG = "MovieVH";

        ConstraintLayout expandableLayout;
        TextView titleTextView, currentValueTextView;
        LinearLayout ll;

        public MovieVH(@NonNull final View itemView) {
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
                }
            });
        }
    }
}