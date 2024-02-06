package com.example.front_end_current;

// Android
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Models
import com.example.Models.CollegeClass;
import com.example.Models.ScheduleManager;

// Java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class SchedulePage that represents the container for the recycle view of class objects.
 */
public class SchedulePage extends Fragment  implements CollegeClassAdapter.FragmentChangeListener {
    private RecyclerView recyclerView;
    private CollegeClassAdapter adapter;
    private List<CollegeClass> collegeClasses;
    private ScheduleManagerLogger scheduleManagerLogger;

    /**
     * Required empty public constructor
     */
    public SchedulePage() {}

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

    /**
     * Function to load the college classes to be rendered from the database
     * @param scheduleManager database instance to load from
     */
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

    @Override
    public void changeFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }
}
