package com.example.front_end_current;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.CollegeClass;
import com.example.Models.Day;
import com.example.Models.MeetingTime;
import com.example.Models.ScheduleManager;
import com.example.front_end_current.CollegeClassAdapter;
import com.example.front_end_current.ScheduleManagerLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SchedulePage extends Fragment  implements CollegeClassAdapter.FragmentChangeListener {
    private RecyclerView recyclerView;
    private CollegeClassAdapter adapter;
    private List<CollegeClass> collegeClasses;
    private ScheduleManagerLogger scheduleManagerLogger;

    public SchedulePage() {
        // Required empty public constructor
    }

    public static SchedulePage newInstance() {
        return new SchedulePage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedulepage, container, false);

        // Initialize RecyclerView and adapter
        recyclerView = rootView.findViewById(R.id.recyclerView);
        collegeClasses = new ArrayList<>();
        adapter = new CollegeClassAdapter(collegeClasses);
        adapter.setFragmentChangeListener(this);

        // Set layout manager and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Add divider between items
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // Initialize ScheduleManagerLogger
        scheduleManagerLogger = new ScheduleManagerLogger();

        // Populate college classes
        if (Database.DATABASE != null) {
            loadCollegeClasses(Database.DATABASE);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update college classes when the Fragment resumes
        if (Database.DATABASE != null) {
            loadCollegeClasses(Database.DATABASE);
            // Log schedule manager
            scheduleManagerLogger.logScheduleManager(Database.DATABASE);
        }
    }

    public void loadCollegeClasses(ScheduleManager scheduleManager) {
        collegeClasses.clear();
        collegeClasses.addAll(scheduleManager.mondayClasses);
        collegeClasses.addAll(scheduleManager.tuesdayClasses);
        collegeClasses.addAll(scheduleManager.wednesdayClasses);
        collegeClasses.addAll(scheduleManager.thursdayClasses);
        collegeClasses.addAll(scheduleManager.fridayClasses);
        //collegeClasses.add(new CollegeClass("Heron's Class", new MeetingTime(Day.Monday, "11:10", "12:30"), "Mr.Tyler", "L04", "CULC", "144"));
        Collections.sort(collegeClasses);
        adapter.notifyDataSetChanged();
    }

    public void changeFragment(Fragment fragment) {
        // Replace the current fragment with the new fragment
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }
}
