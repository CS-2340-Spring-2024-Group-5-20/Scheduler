package com.example.front_end_current;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Models.CollegeClass;
import com.example.Models.Day;
import com.example.Models.MeetingTime;
import com.example.Models.ScheduleManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchedulePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchedulePage extends Fragment {
    private RecyclerView recyclerView;
    private CollegeClassAdapter adapter;
    private List<CollegeClass> collegeClasses;
    private ScheduleManager scheduleManager;
    private ScheduleManagerLogger scheduleManagerLogger = new ScheduleManagerLogger();

    public SchedulePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment schedulepage.
     */
    // TODO: Rename and change types and number of parameters
    public static SchedulePage newInstance(String param1, String param2) {
        SchedulePage fragment = new SchedulePage();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedulepage, container, false);

        // Initialize RecyclerView and adapter
        recyclerView = rootView.findViewById(R.id.recyclerView);
        collegeClasses = new ArrayList<>();
        adapter = new CollegeClassAdapter(collegeClasses);

        // Set layout manager and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // Populate college classes
        if (scheduleManager != null) {
            loadCollegeClasses(scheduleManager);
        }

        return rootView;
    }

    public void onResume() {
        super.onResume();
        // Update college classes when the Fragment resumes
        if (scheduleManager != null) {
            loadCollegeClasses(scheduleManager);
            scheduleManagerLogger.logScheduleManager(scheduleManager);
        }
    }

    public void loadCollegeClasses(ScheduleManager scheduleManager) {
        collegeClasses.clear();
        collegeClasses.addAll(scheduleManager.mondayClasses);
        collegeClasses.addAll(scheduleManager.tuesdayClasses);
        collegeClasses.addAll(scheduleManager.wednesdayClasses);
        collegeClasses.addAll(scheduleManager.thursdayClasses);
        collegeClasses.addAll(scheduleManager.fridayClasses);
        collegeClasses.add(new CollegeClass("Heron's Class", new MeetingTime(Day.Monday, "11:10", "12:30"), "Mr.Tyler", "L04", "CULC", "144"));
        Collections.sort(collegeClasses);
        adapter.notifyDataSetChanged();
    }

    public void setScheduleManager(ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
    }
}