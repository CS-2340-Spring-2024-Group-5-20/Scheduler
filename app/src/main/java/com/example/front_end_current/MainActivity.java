package com.example.front_end_current;

// Android
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.front_end_current.databinding.ActivityMainBinding;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

/**
 * Main activity for project --> runs all related fragments.
 * We decided to keep most of the fragment logic within the fragments to keep the
 * main activity lightweight, and to isolate individual fragment logic to each respective fragment.
 * We also decided to go with fragment based UI instead of multiple
 * activities because all of the functionality of each component is closely linked, and data needs
 * to be seamlessly passed and new UI components need to be rendered often with the same data.
 */
@Metadata(
    mv = {1, 9, 0},
    k = 1,
    d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"},
    d2 = {"Lcom/example/front_end_current/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}
)
public final class MainActivity extends AppCompatActivity implements TaskAdapter.FragmentChangeListener, CollegeClassAdapter.FragmentChangeListener{
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
                    changeFragment(reminderPage);
                } else if (item.getItemId() == R.id.schedule) {
                    SchedulePage schedulePage = new SchedulePage();
                    changeFragment(schedulePage);
                }
                return true;
        });
        binding.middleMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {buttonClicked();}
        });
        changeFragment(new ReminderPage());
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

    /**
     * Function to bring up the selector fragment when middle button clicked.
     * Selector fragment holds frame to toggle between add task or add class.
     */
    private void buttonClicked() {
        changeFragment(new SelectorFragment());
    }
}
