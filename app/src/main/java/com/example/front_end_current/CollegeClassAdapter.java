package com.example.front_end_current;

// Android
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

// Models
import com.example.Models.CollegeClass;
import com.example.Models.Day;

// Java
import java.util.List;

/**
 * College class adapter class that holds individual class objects
 * to be rendered in a list by the recycle view.
 */
public class CollegeClassAdapter extends RecyclerView.Adapter<CollegeClassAdapter.ViewHolder> {
    private List<CollegeClass> collegeClasses;

    /**
     * Interface to allow item to be linked to a edit class fragment through
     * fragment change listener.
     */
    public interface FragmentChangeListener {
        void changeFragment(Fragment fragment);
    }
    private FragmentChangeListener fragmentChangeListener;

    /**
     * Setter for the fragment change listener
     * @param listener to set to
     */
    public void setFragmentChangeListener(FragmentChangeListener listener) {
        this.fragmentChangeListener = listener;
    }

    /**
     * Constructor to initialize based on context of collegeClasses
     * @param collegeClasses list of classes to build fragment with
     */
    public CollegeClassAdapter(List<CollegeClass> collegeClasses) {
        this.collegeClasses = collegeClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_college_class, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CollegeClass collegeClass = collegeClasses.get(position);
        holder.textViewClassName.setText(collegeClass.getClassTitle());
        holder.textViewDayOfWeek.setText(getDayOfWeekString(collegeClass.getMeetingTime().getMeetDay()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchEditClassFragment(collegeClass);
            }
        });
    }

    /**
     * Helper function to launch the edit class fragment when a adapter is clicked
     * @param collegeClass context to populate edit class fragment with
     */
    private void launchEditClassFragment(CollegeClass collegeClass) {
        EditClassFragment editFragment = EditClassFragment.newInstance(collegeClass);
        Log.d("Log", "Clicked on editable element");

        if (fragmentChangeListener != null) {
            fragmentChangeListener.changeFragment(editFragment);
            Log.d("Log", "Clicked on editable element");
        }
    }

    @Override
    public int getItemCount() {
        return collegeClasses.size();
    }

    /**
     * Helper function to convert the Day enum into a string
     * @param dayOfWeek to convert
     * @return String representation of dayOfWeek
     */
    private String getDayOfWeekString(Day dayOfWeek) {
        switch (dayOfWeek) {
            case Sunday:
                return "Sunday";
            case Monday:
                return "Monday";
            case Tuesday:
                return "Tuesday";
            case Wednesday:
                return "Wednesday";
            case Thursday:
                return "Thursday";
            case Friday:
                return "Friday";
            case Saturday:
                return "Saturday";
            default:
                return "";
        }
    }

    /**
     * ViewHolder class to represent class object.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewClassName;
        private TextView textViewDayOfWeek;

        /**
         * Constructor to build object based on view
         * @param itemView view context to build fragment ViewHolder
         */
        public ViewHolder(View itemView) {
            super(itemView);
            textViewClassName = itemView.findViewById(R.id.textViewClassName);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
        }
    }
}
