package com.example.front_end_current;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.Assignment;
import com.example.Models.CollegeClass;
import com.example.Models.Exam;
import com.example.Models.Task;
import com.example.Models.Day;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> tasks;

    // Define interface
    public interface FragmentChangeListener {
        void changeFragment(Fragment fragment);
    }
    private FragmentChangeListener fragmentChangeListener;

    // Method to set the fragment change listener
    public void setFragmentChangeListener(FragmentChangeListener listener) {
        this.fragmentChangeListener = listener;
    }


    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textViewTaskDescription.setText(task.getDescription());
        holder.textViewTaskDayOfMonth.setText(new Integer(task.getDayOfMonth()).toString());
        holder.textViewTaskMonth.setText(new Integer(task.getMonth()).toString());
        String dueDate = "";
        if (task instanceof Exam || task instanceof Assignment) {
            if (task instanceof Exam) {
                dueDate = ((Exam)task).getEndTime();
            } else if (task instanceof Assignment) {
                dueDate = ((Assignment)task).getDueDate();
            }
        }
        holder.textViewTaskDueDate.setText(dueDate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch EditClassFragment with the selected CollegeClass object
                launchTaskEditFragment(task);
            }
        });
    }

    private void launchTaskEditFragment(Task task) {
        // Create a new instance of EditTaskFragment
        EditTaskFragment editTaskFragment = EditTaskFragment.newInstance(task);
        Log.d("Log", "Clicked on editable element");

        // Pass the fragment to the activity
        if (fragmentChangeListener != null) {
            fragmentChangeListener.changeFragment(editTaskFragment);
            Log.d("Log", "Clicked on editable element 2, task " + task.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTaskDescription;
        private TextView textViewTaskDayOfMonth;
        private TextView textViewTaskMonth;
        private TextView textViewTaskDueDate;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewTaskDescription = itemView.findViewById(R.id.textViewTaskDescription);
            textViewTaskDayOfMonth = itemView.findViewById(R.id.textViewTaskDayOfMonth);
            textViewTaskMonth = itemView.findViewById(R.id.textViewTaskMonth);
            textViewTaskDueDate = itemView.findViewById(R.id.textViewTaskDueDate);
        }
    }
}
