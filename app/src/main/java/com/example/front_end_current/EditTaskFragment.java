package com.example.front_end_current;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import androidx.annotation.NonNull;

import com.example.Models.Assignment;
import com.example.Models.CollegeClass;
import com.example.Models.Exam;
import com.example.Models.Task;
import com.example.front_end_current.Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EditTaskFragment extends Fragment {

    private Spinner taskTypeSpinner;
    private EditText taskDescriptionEditText;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private Spinner collegeClassSpinner;
    private EditText dayEditText;
    private EditText monthEditText;
    private Button saveTaskButton;
    private Button deleteTaskButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        Log.d("Bro","Reached edit task.");

        taskTypeSpinner = view.findViewById(R.id.taskTypeSpinner);
        taskDescriptionEditText = view.findViewById(R.id.taskDescriptionEditText);
        startTimeEditText = view.findViewById(R.id.startTimeEditText);
        endTimeEditText = view.findViewById(R.id.endTimeEditText);
        collegeClassSpinner = view.findViewById(R.id.collegeClassSpinner);
        dayEditText = view.findViewById(R.id.dayEditText);
        monthEditText = view.findViewById(R.id.monthEditText);
        saveTaskButton = view.findViewById(R.id.saveTaskButton);
        deleteTaskButton = view.findViewById(R.id.deleteTaskButton);

        setupCollegeClassSpinner(collegeClassSpinner);
        setupTaskTypeSpinner();

        Task task = (Task) getArguments().getSerializable("task");
        preFillTaskDetails(task);

        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask(task.getId());
            }
        });

        deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { deleteTask(task.getId()); }
        });

        return view;
    }

    public static EditTaskFragment newInstance(Task task) {
        EditTaskFragment fragment = new EditTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable("task", (Serializable) task);
        fragment.setArguments(args);
        return fragment;
    }

    private void setupTaskTypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.task_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskTypeSpinner.setAdapter(adapter);
        taskTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = parent.getItemAtPosition(position).toString();
                showHideFields(selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void showHideFields(String selectedType) {
        // Get references to the fields you want to show/hide
        EditText endTimeEditText = requireView().findViewById(R.id.endTimeEditText);

        // Hide all fields initially
        endTimeEditText.setVisibility(View.GONE);
        collegeClassSpinner.setVisibility(View.GONE);

        // Show the relevant field based on the selected type
        if ("Exam".equals(selectedType) || "Assignment".equals(selectedType)) {
            endTimeEditText.setVisibility(View.VISIBLE);
            collegeClassSpinner.setVisibility(View.VISIBLE);
        }
    }

    private void setupCollegeClassSpinner(Spinner collegeClassSpinner) {
        List<CollegeClass> collegeClasses = Database.DATABASE.getAllClasses();
        List<String> classTitles = new ArrayList<>();

        if (collegeClasses != null) {
            for (CollegeClass collegeClass : collegeClasses) {
                classTitles.add(collegeClass.getClassTitle());
            }

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, classTitles);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            collegeClassSpinner.setAdapter(spinnerAdapter);
        } else {
            Log.e("EditTaskFragment", "College classes list is null");
        }
    }

    private void preFillTaskDetails(Task task) {
        taskDescriptionEditText.setText(task.getDescription());
        startTimeEditText.setText(task.getTime());
        dayEditText.setText(String.valueOf(task.getDayOfMonth()));
        monthEditText.setText(String.valueOf(task.getMonth()));

        // Pre-select the task type in the spinner
        String taskType = task instanceof Assignment ? "Assignment" :
                task instanceof Exam ? "Exam" : "Task";
        int position = ((ArrayAdapter) taskTypeSpinner.getAdapter()).getPosition(taskType);
        taskTypeSpinner.setSelection(position);

        if (task instanceof Exam || task instanceof Assignment) {
            // Cast task to Exam or Assignment
            CollegeClass associatedClass = null;
            if (task instanceof Exam) {
                associatedClass = ((Exam) task).getCollegeClass();
            } else if (task instanceof Assignment) {
                associatedClass = ((Assignment) task).getCollegeClass();
            }

            // Pre-select the associated college class in the spinner
            if (associatedClass != null) {
                String collegeClassName = associatedClass.getClassTitle();
                int classPosition = ((ArrayAdapter) collegeClassSpinner.getAdapter()).getPosition(collegeClassName);
                collegeClassSpinner.setSelection(classPosition);
            }
        }
    }

    private void saveTask(UUID id) {
        String taskDescription = taskDescriptionEditText.getText().toString().trim();
        String startTime = startTimeEditText.getText().toString().trim();
        int day = Integer.parseInt(dayEditText.getText().toString().trim());
        int month = Integer.parseInt(monthEditText.getText().toString().trim());

        // Determine the task type based on the spinner selection
        String taskType = taskTypeSpinner.getSelectedItem().toString();

        if (taskDescription.isEmpty() || startTime.isEmpty() || taskType.isEmpty() || taskType.isEmpty() ||
            dayEditText.getText().toString().isEmpty() || monthEditText.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Task task;
        if (taskType.equals("Assignment")) {
            CollegeClass selectedCollegeClass = Database.DATABASE.getAllClasses().get(collegeClassSpinner.getSelectedItemPosition());
            task = new Assignment(selectedCollegeClass, startTime, taskDescription, month, day);
        } else if (taskType.equals("Exam")) {
            CollegeClass selectedCollegeClass = Database.DATABASE.getAllClasses().get(collegeClassSpinner.getSelectedItemPosition());
            String endTime = endTimeEditText.getText().toString().trim();
            task = new Exam(selectedCollegeClass, startTime, endTime, taskDescription, month, day);
        } else {
            task = new Task(startTime, taskDescription, month, day);
        }

        // Save the updated task to the database
        Database.DATABASE.updateTaskByUUID(id, task);
        Toast.makeText(requireContext(), "Task updated successfully", Toast.LENGTH_SHORT).show();

        // Navigate back to previous fragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void deleteTask(UUID id) {
        Database.DATABASE.deleteTaskByUUID(id);
        Toast.makeText(requireContext(), "Task deleted successfully", Toast.LENGTH_SHORT).show();

        // Navigate back to previous fragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}
