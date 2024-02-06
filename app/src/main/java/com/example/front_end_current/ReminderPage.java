package com.example.front_end_current;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.CollegeClass;
import com.example.Models.Day;
import com.example.Models.Task;
import com.example.Models.ScheduleManager;
import com.example.front_end_current.CollegeClassAdapter;
import com.example.front_end_current.ScheduleManagerLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;import com.example.front_end_current.TaskAdapter;

public class ReminderPage extends Fragment implements TaskAdapter.FragmentChangeListener {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> tasks;

    public ReminderPage() {
        // Required empty public constructor
    }

    public static ReminderPage newInstance() {
        return new ReminderPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reminderpage, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        tasks = new ArrayList<>();
        adapter = new TaskAdapter(tasks);
        adapter.setFragmentChangeListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        loadTasks();

        return rootView;
    }

    private void loadTasks() {
        tasks.clear();

        if (Database.DATABASE != null) {
            List<Task> academicTasks = Database.DATABASE.academicTasks;

            if (academicTasks != null) {
                tasks.addAll(academicTasks);
            }
        } else {
            Toast.makeText(getContext(), "Database not initialized", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void changeFragment(Fragment fragment) {
        // You can implement fragment navigation logic here if needed
    }
}
