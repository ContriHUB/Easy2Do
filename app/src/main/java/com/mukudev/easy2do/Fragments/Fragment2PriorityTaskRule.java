package com.mukudev.easy2do.Fragments;

import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.AlertDialog;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Random;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mukudev.easy2do.R;
import com.google.android.material.card.MaterialCardView;

public class Fragment2PriorityTaskRule extends Fragment {
    // List of motivational quotes
    private static final String[] quotes = {
            "Great job on completing your first task!",
            "Keep up the great work!",
            "You're doing amazing!",
            "You're making excellent progress!",
            "Well done on prioritizing your tasks!"
    };

    // Function to get a random quote
    private String getRandomQuote() {
        int index = new Random().nextInt(quotes.length);
        return quotes[index];
    }

    private void handleTaskCompletion(CheckBox checkbox, EditText input, String taskKey, String message, SharedPreferences.Editor editor, View cardView) {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Task Completion")
                .setMessage("Are you sure you want to mark this task as completed? This action cannot be undone.")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    input.setEnabled(false);
                    checkbox.setEnabled(false);
                    editor.putBoolean(taskKey, true);
                    editor.apply();
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    cardView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));

                    boolean bothTasksCompleted = getActivity().getSharedPreferences("PriorityTasks", Context.MODE_PRIVATE)
                            .getBoolean("task1_completed", false) && getActivity().getSharedPreferences("PriorityTasks", Context.MODE_PRIVATE)
                            .getBoolean("task2_completed", false);

                    TextView motivationalMessage = getView().findViewById(R.id.motivational_message);
                    if (bothTasksCompleted) {
                        motivationalMessage.setText("Awesome! You've completed both tasks!");
                    } else if (getRandomQuote().equals("Fantastic effort!")) {
                        String quote = getRandomQuote();
                        while (quote.equals("Fantastic effort!")) {
                            quote = getRandomQuote();
                        }
                        motivationalMessage.setText(quote);
                    } else {
                        motivationalMessage.setText(getRandomQuote());
                    }
                    motivationalMessage.setVisibility(View.VISIBLE);
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    checkbox.setChecked(false);
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2priority_task_rule, container, false);

        EditText task1Input = view.findViewById(R.id.task1_input);
        CheckBox task1Checkbox = view.findViewById(R.id.task1_checkbox);
        EditText task2Input = view.findViewById(R.id.task2_input);
        CheckBox task2Checkbox = view.findViewById(R.id.task2_checkbox);
        TextView motivationalMessage = view.findViewById(R.id.motivational_message);
        View task1Card = view.findViewById(R.id.task1_card);
        View task2Card = view.findViewById(R.id.task2_card);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PriorityTasks", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Calendar now = Calendar.getInstance();
        long currentTime = now.getTimeInMillis();
        long lastResetTime = sharedPreferences.getLong("last_reset_time", 0);

        boolean shouldReset = currentTime - lastResetTime > 24 * 60 * 60 * 1000; // 24 hours in milliseconds

        boolean task1Completed = sharedPreferences.getBoolean("task1_completed", false);
        boolean task2Completed = sharedPreferences.getBoolean("task2_completed", false);

        if (shouldReset) {
            // It's a new day, perform reset
            editor.clear();
            editor.putLong("last_reset_time", currentTime);
            editor.apply();

            task1Input.setText("");
            task2Input.setText("");
            task1Checkbox.setChecked(false);
            task2Checkbox.setChecked(false);
            task1Input.setEnabled(true);
            task2Input.setEnabled(true);
            task1Checkbox.setEnabled(true);
            task2Checkbox.setEnabled(true);  // Enable both checkboxes
            motivationalMessage.setVisibility(View.GONE);
            task1Completed = false;
            task2Completed = false;
        } else {
            // Restore saved state
            task1Input.setText(sharedPreferences.getString("task1", ""));
            task2Input.setText(sharedPreferences.getString("task2", ""));
            task1Checkbox.setChecked(task1Completed);
            task2Checkbox.setChecked(task2Completed);
            task1Input.setEnabled(!task1Completed);
            task2Input.setEnabled(!task2Completed);
            task1Checkbox.setEnabled(!task1Completed);
            task2Checkbox.setEnabled(!task2Completed);
        }

        task1Input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("task1", s.toString());
                editor.apply();
            }
        });

        task2Input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("task2", s.toString());
                editor.apply();
            }
        });

        task1Checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                handleTaskCompletion(task1Checkbox, task1Input, "task1_completed", "Task 1 completed!", editor, task1Card);
            }
        });

        task2Checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                handleTaskCompletion(task2Checkbox, task2Input, "task2_completed", "Task 2 completed!", editor, task2Card);
            }
        });

        return view;
    }

}
