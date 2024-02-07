package com.example.front_end_current;

// Android
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Models
import com.example.Models.Assignment;
import com.example.Models.CollegeClass;
import com.example.Models.Exam;
import com.example.Models.Task;

// Java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ReminderPage class for the fragment holding the task recycler view.
 */
public class ReminderPage extends Fragment implements TaskAdapter.FragmentChangeListener {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> tasks;
    private Button filter;
    private Spinner sortingSpinner;

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

        sortingSpinner = rootView.findViewById(R.id.sortingSpinner);
        filter = rootView.findViewById(R.id.filterButton);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortTasks();
            }
        });

        loadTasks();

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.sorting_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortingSpinner.setAdapter(spinnerAdapter);

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

    /**
     * Function to sort the task list in desired order when the filter button is clicked,
     * based on the status of the sorting spinner.
     */
    private void sortTasks() {
        String selectedOption = sortingSpinner.getSelectedItem().toString();

        tasks = Database.DATABASE.academicTasks;

        List<Task> exams = new ArrayList<Task>();
        List<Task> assignments = new ArrayList<Task>();
        List<Task> new_tasks = new ArrayList<Task>();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i) instanceof Exam) {
                exams.add((Exam) tasks.get(i));
            } else if (tasks.get(i) instanceof Assignment) {
                assignments.add((Assignment) tasks.get(i));
            } else {
                new_tasks.add(tasks.get(i));
            }
        }

        ArrayList<Task> unsortedTasks = new ArrayList<Task>(tasks);
        List<Task> sortedByCourses = new ArrayList<Task>();
        List<CollegeClass> courses = Database.DATABASE.getAllClasses();
        for (int i = 0; i < courses.size(); i++) {
            CollegeClass currentClass = courses.get(i);
            for (int j = 0; j < unsortedTasks.size(); j++) {
                Task currentTask = tasks.get(j);
                if (currentTask instanceof Exam)
                {
                    currentTask = (Exam) currentTask;
                    if (currentTask.getClass().equals(currentClass)) {
                        sortedByCourses.add(unsortedTasks.remove(j));
                    }
                } else if (currentTask instanceof Assignment)
                {
                    currentTask = (Assignment) currentTask;
                    if (currentTask.getClass().equals(currentClass)) {
                        sortedByCourses.add(unsortedTasks.remove(j));
                    }
                } else {
                    continue;
                }
            }
        }
        sortedByCourses.addAll(unsortedTasks);

        List<Task> sortedByDueDate = new ArrayList<Task>(tasks);
        Comparator<Task> comparator = new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                // Compare by month first
                int monthComparison = Integer.compare(task1.getMonth(), task2.getMonth());
                if (monthComparison != 0) {
                    return monthComparison;
                }
                // If months are equal, compare by day
                return Integer.compare(task1.getDayOfMonth(), task2.getDayOfMonth());
            }
        };

        Collections.sort(sortedByDueDate, comparator);


        switch (selectedOption) {
            case "By Exam":
                tasks = exams;
                break;
            case "By Assignment":
                tasks = assignments;
                break;
            case "By Task":
                tasks = new_tasks;
                break;
            case "By All":
                tasks.clear();
                tasks.addAll(exams);
                tasks.addAll(assignments);
                tasks.addAll(new_tasks);
                break;
            case "By Course":
                tasks = sortedByCourses;
                break;
            case "By Due Date":
                tasks = sortedByDueDate;
                break;
        }

        adapter.updateTasks(tasks);

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
