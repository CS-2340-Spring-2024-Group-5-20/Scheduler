package com.example.front_end_current;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.CollegeClass;
import com.example.Models.Day;

import java.util.List;

public class CollegeClassAdapter extends RecyclerView.Adapter<CollegeClassAdapter.ViewHolder> {
    private List<CollegeClass> collegeClasses;

    // Define interface
    public interface FragmentChangeListener {
        void changeFragment(Fragment fragment);
    }
    private FragmentChangeListener fragmentChangeListener;

    // Method to set the fragment change listener
    public void setFragmentChangeListener(FragmentChangeListener listener) {
        this.fragmentChangeListener = listener;
    }


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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch EditClassFragment with the selected CollegeClass object
                launchEditClassFragment(collegeClass);
            }
        });
    }

    private void launchEditClassFragment(CollegeClass collegeClass) {
        // Create a new instance of EditClassFragment
        EditClassFragment editFragment = EditClassFragment.newInstance(collegeClass);
        Log.d("Log", "Clicked on editable element");

        // Pass the fragment to the activity
        if (fragmentChangeListener != null) {
            fragmentChangeListener.changeFragment(editFragment);
            Log.d("Log", "Clicked on editable element");
        }
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewClassName;
        private TextView textViewDayOfWeek;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewClassName = itemView.findViewById(R.id.textViewClassName);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
        }
    }
}
