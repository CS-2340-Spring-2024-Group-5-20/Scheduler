package com.example.front_end_current;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.Models.CollegeClass;
import com.example.Models.Day;
import com.example.Models.MeetingTime;
import com.example.Models.ScheduleManager;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.UUID;

public class EditClassFragment extends Fragment {
    private TextInputEditText classNameEditText;
    private TextInputEditText professorNameEditText;
    private TextInputEditText sectionEditText;
    private TextInputEditText locationEditText;
    private TextInputEditText roomEditText;
    private MaterialAutoCompleteTextView dayOfWeekSpinner;
    private TextInputEditText startEditText;
    private TextInputEditText endEditText;
    private Button saveClassButton;
    private Button deleteClassButton;
    public EditClassFragment() {
        // Required empty public constructor
    }

    public static EditClassFragment newInstance(CollegeClass collegeClass) {
        EditClassFragment fragment = new EditClassFragment();
        Bundle args = new Bundle();
        args.putSerializable("collegeClass", (Serializable) collegeClass);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_class, container, false);

        classNameEditText = view.findViewById(R.id.classNameEditText);
        professorNameEditText = view.findViewById(R.id.professorNameEditText);
        sectionEditText = view.findViewById(R.id.sectionEditText);
        locationEditText = view.findViewById(R.id.locationEditText);
        roomEditText = view.findViewById(R.id.roomEditText);
        dayOfWeekSpinner = view.findViewById(R.id.dayOfWeekSpinner);
        startEditText = view.findViewById(R.id.startEditText);
        endEditText = view.findViewById(R.id.endEditText);
        saveClassButton = view.findViewById(R.id.saveClassButton);
        deleteClassButton = view.findViewById(R.id.deleteClassButton);

        // Get the CollegeClass object from arguments
        CollegeClass collegeClass = (CollegeClass) getArguments().getSerializable("collegeClass");

        // Pre-fill EditText fields with the details of the CollegeClass object
        classNameEditText.setText(collegeClass.getClassTitle());
        professorNameEditText.setText(collegeClass.getProfessor());
        sectionEditText.setText(collegeClass.getClassSection());
        locationEditText.setText(collegeClass.getLocation());
        roomEditText.setText(collegeClass.getRoomNumber());
        dayOfWeekSpinner.setText(collegeClass.getMeetingTime().getMeetDay().toString());
        startEditText.setText(collegeClass.getMeetingTime().getStartTime());
        endEditText.setText(collegeClass.getMeetingTime().getEndTime());

        // Set OnClickListener for the Save button
        saveClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save changes and navigate back to previous fragment
                saveClass(collegeClass.getUUID());
            }
        });

        deleteClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {deleteClass(collegeClass.getUUID());
            }
        });

        return view;
    }

    /**
     * Function to load the content from the fragment and save to the database;
     */
    private void saveClass(UUID id) {

        String className = classNameEditText.getText().toString().trim();
        String professorName = professorNameEditText.getText().toString().trim();
        String section = sectionEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();
        String room = roomEditText.getText().toString().trim();
        String dayOfWeek = dayOfWeekSpinner.getText().toString().trim();
        String start = startEditText.getText().toString().trim();
        String end = endEditText.getText().toString().trim();

        if (className.isEmpty() || professorName.isEmpty() || section.isEmpty()
                || location.isEmpty() || room.isEmpty() || dayOfWeek.isEmpty()
                || start.isEmpty() || end.isEmpty() ) {
            Toast.makeText(requireContext(), "Please enter event details", Toast.LENGTH_SHORT).show();
            return;
        }

        MeetingTime meetingTime = new MeetingTime(Day.valueOf(dayOfWeek),start,end);
        CollegeClass collegeClass = new CollegeClass(className, meetingTime, professorName, section,
                location, room);

        if (Database.DATABASE == null) {
            Log.e("YourFragment", "ScheduleManager is not initialized");
        } else {
            Database.DATABASE.updateCourseByUUID(id, collegeClass);
        }

        Toast.makeText(requireContext(), "Event saved successfully", Toast.LENGTH_SHORT).show();

        // navigate back to schedule fragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void deleteClass(UUID id) {
        Database.DATABASE.deleteClassByUUID(id);
        Toast.makeText(requireContext(), "Class deleted successfully", Toast.LENGTH_SHORT).show();

        // Navigate back to previous fragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}
