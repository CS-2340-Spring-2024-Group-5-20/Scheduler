package com.example.front_end_current;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.CollegeClass;
import com.example.Models.Day;

import java.util.List;

public class CollegeClassAdapter extends RecyclerView.Adapter<CollegeClassAdapter.ViewHolder> {
    private List<CollegeClass> collegeClasses;

    public CollegeClassAdapter(List<CollegeClass> collegeClasses) {
        this.collegeClasses = collegeClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_college_class, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CollegeClass collegeClass = collegeClasses.get(position);
        holder.textViewClassName.setText(collegeClass.getClassTitle());
        holder.textViewDayOfWeek.setText(getDayOfWeekString(collegeClass.getMeetingTime().getMeetDay()));
    }

    @Override
    public int getItemCount() {
        return collegeClasses.size();
    }

    private String getDayOfWeekString(Day dayOfWeek) {
        switch (dayOfWeek) {
            case Sunday:
                return "Sunday";
            case Monday:
                return "Monday";
            case Tuesday:
                return "Tuesday";
            case Wednesday:
                return "Wednesday";
            case Thursday:
                return "Thursday";
            case Friday:
                return "Friday";
            case Saturday:
                return "Saturday";
            default:
                return "";
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewClassName;
        TextView textViewDayOfWeek;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewClassName = itemView.findViewById(R.id.textViewClassName);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
        }
    }
}
