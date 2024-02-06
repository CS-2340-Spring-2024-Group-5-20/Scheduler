package com.example.front_end_current;

// Android
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Fragment class that holds the two create views and allows user to toggle
 * between them using a frame layout.
 */
public class SelectorFragment extends Fragment {

    private Spinner dropdownMenu;
    private FrameLayout fragmentContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selector, container, false);

        dropdownMenu = view.findViewById(R.id.dropdown_menu);
        fragmentContainer = view.findViewById(R.id.fragment_container);

        // Set up the dropdown menu
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.dropdown_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownMenu.setAdapter(adapter);

        // Handle dropdown menu selection
        dropdownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switchFragment(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        return view;
    }

    /**
     * Function switching of fragments based on the dropdown menu
     * @param position of the dropdown menu --> which fragment to switch to
     */
    private void switchFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new CreateClassPage();
                break;
            case 1:
                fragment = new CreateTaskFragment();
                break;
            default:
                fragment = new CreateClassPage();
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
