package com.example.front_end_current;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.Models.Assignment;
import com.example.Models.CollegeClass;
import com.example.Models.Day;
import com.example.Models.Exam;
import com.example.Models.MeetingTime;
import com.example.Models.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditTaskFragment extends Fragment {

    private Spinner taskTypeSpinner;
    private EditText taskDescriptionEditText;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private Spinner collegeClassSpinner;
    private EditText dayEditText;
    private EditText monthEditText;

    private List<CollegeClass> collegeClasses;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        taskTypeSpinner = view.findViewById(R.id.taskTypeSpinner);
        taskDescriptionEditText = view.findViewById(R.id.taskDescriptionEditText);
        startTimeEditText = view.findViewById(R.id.startTimeEditText);
        endTimeEditText = view.findViewById(R.id.endTimeEditText);
        collegeClassSpinner = view.findViewById(R.id.collegeClassSpinner);
        dayEditText = view.findViewById(R.id.dayEditText);
        monthEditText = view.findViewById(R.id.monthEditText);

        setupCollegeClassSpinner(collegeClassSpinner);
        setupTaskTypeSpinner();

        // Retrieve task details and pre-fill form fields for editing
        Task task = (Task) getArguments().getSerializable("task");
        preFillTaskDetails(task);

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

        // Show the relevant field based on the selected type
        if ("Exam".equals(selectedType)) {
            endTimeEditText.setVisibility(View.VISIBLE);
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


    private void saveTask() {
        String taskDescription = taskDescriptionEditText.getText().toString().trim();
        String startTime = startTimeEditText.getText().toString().trim();
        int day = Integer.parseInt(dayEditText.getText().toString().trim());
        int month = Integer.parseInt(monthEditText.getText().toString().trim());
        CollegeClass selectedCollegeClass = collegeClasses.get(collegeClassSpinner.getSelectedItemPosition());

        // Determine the task type based on the spinner selection
        String taskType = taskTypeSpinner.getSelectedItem().toString();

        Task task;
        if (taskType.equals("Assignment")) {
            task = new Assignment(selectedCollegeClass, startTime, taskDescription, month, day);
        } else if (taskType.equals("Exam")) {
            String endTime = endTimeEditText.getText().toString().trim();
            task = new Exam(selectedCollegeClass, startTime, endTime, taskDescription, month, day);
        } else {
            task = new Task(startTime, taskDescription, month, day);
        }

        // Save the updated task to the database
        Database.DATABASE.updateTaskByUUID(task.getId(), task);
        Toast.makeText(requireContext(), "Task updated successfully", Toast.LENGTH_SHORT).show();

        // Navigate back to previous fragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

}
