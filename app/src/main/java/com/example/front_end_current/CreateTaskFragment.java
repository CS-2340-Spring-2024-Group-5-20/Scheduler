package com.example.front_end_current;

// Android
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

// Java
import java.util.ArrayList;
import java.util.List;
import com.example.Models.*;

/**
 * Fragment that allows user to add a task
 */
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

    /**
     * Helper function to pre-populate the task spinner with task types
     */
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

    /**
     * Helper function to show or hide fields depending on which type of task is desired
     * @param selectedType type of task to add
     */
    private void showHideFields(String selectedType) {
        Spinner assignmentField = collegeClassSpinner;
        EditText examField = endTimeEditText;
        //EditText taskField = getView().findViewById(R.id.collegeClassSpinner);

        assignmentField.setVisibility(View.GONE);
        examField.setVisibility(View.GONE);
        //taskField.setVisibility(View.GONE);

        if ("Assignment".equals(selectedType)) {
            assignmentField.setVisibility(View.VISIBLE);
        } else if ("Exam".equals(selectedType)) {
            examField.setVisibility(View.VISIBLE);
            assignmentField.setVisibility(View.VISIBLE);
        } else if ("Task".equals(selectedType)) {
            //taskField.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Helper function to populate collegeClass spinner with classes in the database
     * @param collegeClassSpinner to be populated
     */
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

    /**
     * Helper function to save a task to the database
     */
    private void saveTask() {
        String taskDescription = taskDescriptionEditText.getText().toString().trim();
        String startTime = startTimeEditText.getText().toString().trim();
        String endTime;
        if (endTimeEditText.getText() != null) {
            endTime = endTimeEditText.getText().toString().trim();
        } else {
            endTime = "";
        }

        if (taskDescription.isEmpty() || startTime.isEmpty() || TextUtils.isEmpty(dayEditText.getText().toString()) ||
                TextUtils.isEmpty(String.valueOf(monthEditText.getText().toString()))) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int day = Integer.valueOf(dayEditText.getText().toString().trim());
        int month= Integer.valueOf(monthEditText.getText().toString().trim());

        String taskType = taskTypeSpinner.getSelectedItem().toString();

        CollegeClass selectedCollegeClass = null;

        if (taskType.equals("Exam") || taskType.equals("Assignment")) {
            selectedCollegeClass = (CollegeClass) Database.DATABASE.getCollegeClassByName(collegeClassSpinner.getSelectedItem().toString());
        }

        Task task;
        if (taskType.equals("Assignment")) {
            task = new Assignment(selectedCollegeClass, startTime, taskDescription, month, day);
        } else if (taskType.equals("Exam")) {
            task = new Exam(selectedCollegeClass, startTime, endTime, taskDescription, month, day);
        } else {
            task = new Task(startTime, taskDescription, month, day);
        }

        Database.DATABASE.addTaskToSchedule(task);

        Toast.makeText(requireContext(), "Task saved successfully", Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    /**
     * Clear all function to reset fields to blank
     */
    private void clearTaskFields() {
        // Clear EditText fields
        taskDescriptionEditText.setText("");
        startTimeEditText.setText("");
        endTimeEditText.setText("");
        dayEditText.setText("");
        monthEditText.setText("");

        // Clear Spinner fields
        taskTypeSpinner.setSelection(0);
        collegeClassSpinner.setSelection(0);
    }

}
