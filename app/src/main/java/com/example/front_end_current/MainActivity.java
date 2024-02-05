package com.example.front_end_current;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.CollegeClass;
import com.example.Models.Day;
import com.example.Models.Day.*;
import com.example.Models.MeetingTime;
import com.example.Models.ScheduleManager;
import com.example.front_end_current.R.layout;
import com.example.front_end_current.databinding.ActivityMainBinding;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Metadata(
    mv = {1, 9, 0},
    k = 1,
    d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"},
    d2 = {"Lcom/example/front_end_current/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}
)
public final class MainActivity extends AppCompatActivity implements CollegeClassAdapter.FragmentChangeListener{
    ActivityMainBinding binding;
    Button button;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database.start();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        button = (Button) findViewById(R.id.middle_menu_button);
        setContentView(binding.getRoot());
        binding.bottomMenu.setBackground(null);
        binding.bottomMenu.setOnItemSelectedListener(item ->{
                if (item.getItemId() == R.id.reminders) {
                    ReminderPage reminderPage = new ReminderPage();
                    changeTheFragment(reminderPage);
                } else if (item.getItemId() == R.id.schedule) {
                    SchedulePage schedulePage = new SchedulePage();
                    changeTheFragment(schedulePage);
                }
                return true;
        });
        binding.middleMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheFragment(
                    new CreateTaskFragment()
                );
            }
        });
        SchedulePage schedulePage = new SchedulePage();
        changeTheFragment(schedulePage);
    }

    private void changeTheFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void changeFragment(Fragment fragment) {
        // Get the FragmentManager and start a transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
//    private void buttonClicked() {
//        CreateClassPage createClassPage = new CreateClassPage();
//        changeTheFragment(
//                createClassPage
//        );
//    }
    private void buttonClicked() {
        CreateTaskFragment createTaskFragment = new CreateTaskFragment();
        changeFragment(createTaskFragment);
    }
}
