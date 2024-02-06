package com.example.front_end_current;

// Android
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Models
import com.example.Models.Task;

// Java
import java.util.ArrayList;
import java.util.List;

/**
 * ReminderPage class for the fragment holding the task recycler view.
 */
public class ReminderPage extends Fragment implements TaskAdapter.FragmentChangeListener {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> tasks;

    /**
     * Required empty public constructor.
     */
    public ReminderPage() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reminderpage, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        tasks = new ArrayList<>();
        adapter = new TaskAdapter(tasks);
        adapter.setFragmentChangeListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadTasks();

        return rootView;
    }

    /**
     * Helper method to load the tasks from the database instance
     */
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
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }
}
