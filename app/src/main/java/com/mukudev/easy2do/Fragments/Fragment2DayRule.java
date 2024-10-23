
package com.mukudev.easy2do.Fragments;

import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.TextView;

import com.mukudev.easy2do.R;

public class Fragment2DayRule extends Fragment {
    private TextView stayOnTrackTextView;
    private Button markDoneButton;
    private Set<String> markedDays;
    private SharedPreferences sharedPreferences;
    private CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2day_rule, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        markDoneButton = view.findViewById(R.id.markDoneButton);
        
        // Create TextView programmatically if it's not in the layout
        stayOnTrackTextView = new TextView(getContext());
        stayOnTrackTextView.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        stayOnTrackTextView.setPadding(16, 16, 16, 16);
        ((ViewGroup) view).addView(stayOnTrackTextView);

        sharedPreferences = requireContext().getSharedPreferences("HabitTracker", Context.MODE_PRIVATE);
        markedDays = new HashSet<>(sharedPreferences.getStringSet("markedDays", new HashSet<>()));

        setupCalendarView();
        setupMarkDoneButton();
        updateStayOnTrackMessage();

        return view;
    }

    private void setupCalendarView() {
        Calendar today = Calendar.getInstance();
        calendarView.setMaxDate(today.getTimeInMillis());

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);
            Calendar currentDate = Calendar.getInstance();

            if (selectedDate.before(currentDate)) {
                showPastDatePopup();
            } else if (selectedDate.after(currentDate)) {
                Toast.makeText(getContext(), "You cannot select future dates", Toast.LENGTH_SHORT).show();
            } else {
                updateMarkDoneButtonState(selectedDate);
                if (!isCurrentDateMarked()) {
                    showMarkAsDoneConfirmation();
                } else {
                    Toast.makeText(getContext(), "This day is already marked as done!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupMarkDoneButton() {
        markDoneButton.setOnClickListener(v -> showMarkAsDoneConfirmation());
        updateMarkDoneButtonState(Calendar.getInstance());
    }

    private void showPastDatePopup() {
        new AlertDialog.Builder(requireContext())
            .setTitle("Past Date")
            .setMessage("You cannot select previous dates.")
            .setPositiveButton("OK", null)
            .show();
    }

    private void showMarkAsDoneConfirmation() {
        new AlertDialog.Builder(requireContext())
            .setTitle("Mark as Done")
            .setMessage("Are you sure you want to mark this day as done? This action cannot be undone.")
            .setPositiveButton("Yes", (dialog, which) -> markTodayAsDone())
            .setNegativeButton("No", null)
            .show();
    }

    private void markTodayAsDone() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = formatDate(calendar);

        if (!markedDays.contains(currentDate)) {
            markedDays.add(currentDate);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("markedDays", markedDays);
            editor.apply();

            updateStayOnTrackMessage();
            Toast.makeText(getContext(), "Marked as done!", Toast.LENGTH_SHORT).show();
        }
        updateMarkDoneButtonState(calendar);
    }

    private void updateMarkDoneButtonState(Calendar selectedDate) {
        boolean isToday = isToday(selectedDate);
        String currentDate = formatDate(selectedDate);
        markDoneButton.setEnabled(isToday && !markedDays.contains(currentDate));
    }

    private void updateStayOnTrackMessage() {
        int streak = calculateStreak();
        stayOnTrackTextView.setText("Stay on track! Current streak: " + streak + " days");
    }

    private int calculateStreak() {
        Calendar calendar = Calendar.getInstance();
        int streak = 0;
        while (true) {
            String date = formatDate(calendar);
            if (markedDays.contains(date)) {
                streak++;
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            } else {
                break;
            }
        }
        return streak;
    }

    private String formatDate(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" +
               (calendar.get(Calendar.MONTH) + 1) + "/" +
               calendar.get(Calendar.YEAR);
    }

    private boolean isToday(Calendar date) {
        Calendar today = Calendar.getInstance();
        return date.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
               date.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
               date.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);
    }

    // Removed duplicate methods

    private boolean isCurrentDateMarked() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = formatDate(calendar);
        return markedDays.contains(currentDate);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupCalendarView();
        updateMarkDoneButtonState(Calendar.getInstance());
        updateStayOnTrackMessage();
    }
}
