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

import java.util.ArrayList;
import java.util.List;
import com.example.Models.*;
public class CreateTaskFragment extends Fragment {

    private Spinner taskTypeSpinner;
    private EditText taskDescriptionEditText;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private Spinner collegeClassSpinner;
    private EditText dayEditText;
    private EditText monthEditText;
    private Button saveTaskButton;
    private Button clearTaskButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_task_page, container, false);

        taskTypeSpinner = view.findViewById(R.id.taskTypeSpinner);
        taskDescriptionEditText = view.findViewById(R.id.taskDescriptionEditText);
        startTimeEditText = view.findViewById(R.id.startTimeEditText);
        endTimeEditText = view.findViewById(R.id.endTimeEditText);
        collegeClassSpinner = view.findViewById(R.id.collegeClassSpinner);
        dayEditText = view.findViewById(R.id.dayEditText);
        monthEditText = view.findViewById(R.id.monthEditText);
        saveTaskButton = view.findViewById(R.id.saveTaskButton);
        clearTaskButton = view.findViewById(R.id.clearTaskButton);

        setupCollegeClassSpinner(collegeClassSpinner);
        setupTaskTypeSpinner();

        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });

        clearTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTaskFields();
            }
        });

        return view;
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
        Spinner assignmentField = collegeClassSpinner;
        EditText examField = endTimeEditText;
        //EditText taskField = getView().findViewById(R.id.collegeClassSpinner);

        // Hide all fields initially
        assignmentField.setVisibility(View.GONE);
        examField.setVisibility(View.GONE);
        //taskField.setVisibility(View.GONE);

        // Show the relevant field based on the selected type
        if ("Assignment".equals(selectedType)) {
            assignmentField.setVisibility(View.VISIBLE);
        } else if ("Exam".equals(selectedType)) {
            examField.setVisibility(View.VISIBLE);
            assignmentField.setVisibility(View.VISIBLE);
        } else if ("Task".equals(selectedType)) {
            //taskField.setVisibility(View.VISIBLE);
        }
    }

    private void setupCollegeClassSpinner(Spinner collegeClassSpinner) {
        if (getContext() != null) {
            List<CollegeClass> collegeClasses = Database.DATABASE.getAllClasses();
            List<String> classTitles = new ArrayList<>();

            if (collegeClasses != null) {
                for (CollegeClass collegeClass : collegeClasses) {
                    classTitles.add(collegeClass.getClassTitle());
                }

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, classTitles);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                if (collegeClassSpinner != null) {
                    collegeClassSpinner.setAdapter(spinnerAdapter);
                } else {
                    Log.e("CreateTaskFragment", "College class spinner is null");
                }
            } else {
                Log.e("CreateTaskFragment", "College classes list is null");
            }
        } else {
            Log.e("CreateTaskFragment", "Context is null");
        }
    }

    // Method to get task data and save it
    private void saveTask() {
        String taskDescription = taskDescriptionEditText.getText().toString().trim();
        String startTime = startTimeEditText.getText().toString().trim();
        String endTime = endTimeEditText.getText().toString().trim();
        int day = Integer.valueOf(dayEditText.getText().toString().trim());
        int month= Integer.valueOf(monthEditText.getText().toString().trim());
        CollegeClass selectedCollegeClass = (CollegeClass) collegeClassSpinner.getSelectedItem();

        // Determine the task type based on the spinner selection
        String taskType = taskTypeSpinner.getSelectedItem().toString();

        Task task;
        if (taskType.equals("Assignment")) {
            task = new Assignment(selectedCollegeClass, startTime, taskDescription, month, day);
        } else if (taskType.equals("Exam")) {
            task = new Exam(selectedCollegeClass, startTime, endTime, taskDescription, month, day);
        } else {
            task = new Task(startTime, taskDescription, month, day);
        }

        // Save the task to the database
        Database.DATABASE.addTaskToSchedule(task);

        Toast.makeText(requireContext(), "Task saved successfully", Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void clearTaskFields() {
        // Clear EditText fields
        taskDescriptionEditText.setText("");
        startTimeEditText.setText("");
        endTimeEditText.setText("");
        dayEditText.setText("");
        monthEditText.setText("");

        // Clear Spinner fields
        taskTypeSpinner.setSelection(0); // Set to default selection
        collegeClassSpinner.setSelection(0); // Set to default selection
    }

}
