package com.example.front_end_current;

// Android
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

// Models
import com.example.Models.CollegeClass;
import com.example.Models.Day;
import com.example.Models.MeetingTime;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Fragment that allows user to create a college class.
 */
public class CreateClassPage extends Fragment {
    private TextInputEditText classNameEditText;
    private TextInputEditText professorNameEditText;
    private TextInputEditText sectionEditText;
    private TextInputEditText locationEditText;
    private TextInputEditText roomEditText;
    private MaterialAutoCompleteTextView dayOfWeekSpinner;
    private TextInputEditText startEditText;
    private TextInputEditText endEditText;
    private Button saveClassButton;
    private Button clearClassButton;

    /**
     * Required empty public constructor
     */
    public CreateClassPage() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_class_page, container, false);

        classNameEditText = view.findViewById(R.id.classNameEditText);
        professorNameEditText = view.findViewById(R.id.professorNameEditText);
        sectionEditText = view.findViewById(R.id.sectionEditText);
        locationEditText = view.findViewById(R.id.locationEditText);
        roomEditText = view.findViewById(R.id.roomEditText);
        dayOfWeekSpinner = view.findViewById(R.id.dayOfWeekSpinner);
        startEditText = view.findViewById(R.id.startEditText);
        endEditText = view.findViewById(R.id.endEditText);
        saveClassButton = view.findViewById(R.id.saveClassButton);
        clearClassButton = view.findViewById(R.id.clearClassButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.weekdays_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOfWeekSpinner.setAdapter(adapter);

        saveClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClass();
            }
        });

        clearClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllFields();
            }
        });

        return view;
    }

    /**
     * Function to load the content from the fragment and save to the database;
     */
    private void saveClass() {
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
            Toast.makeText(requireContext(), "Please enter class details", Toast.LENGTH_SHORT).show();
            return;
        }

        MeetingTime meetingTime = new MeetingTime(Day.valueOf(dayOfWeek),start,end);
        CollegeClass collegeClass = new CollegeClass(className, meetingTime, professorName, section,
                location, room);

        if (Database.DATABASE == null) {
            Log.e("YourFragment", "ScheduleManager is not initialized");
        } else {
            Database.DATABASE.addCourseToSchedule(collegeClass);
        }

        Toast.makeText(requireContext(), "Class saved successfully", Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    /**
     * Function to clear all fields to blank.
     */
    private void clearAllFields() {
        classNameEditText.setText("");
        professorNameEditText.setText("");
        sectionEditText.setText("");
        locationEditText.setText("");
        roomEditText.setText("");
        startEditText.setText("");
        endEditText.setText("");
        dayOfWeekSpinner.setText("");
        dayOfWeekSpinner.clearListSelection();
    }
}