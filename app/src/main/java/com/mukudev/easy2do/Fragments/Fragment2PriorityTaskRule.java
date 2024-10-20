package com.mukudev.easy2do.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import java.util.Random;
import java.util.Calendar;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import com.mukudev.easy2do.R;

public class Fragment2PriorityTaskRule extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2priority_task_rule, container, false);

        EditText task1Input = view.findViewById(R.id.task1_input);
        EditText task2Input = view.findViewById(R.id.task2_input);
        CheckBox task1Checkbox = view.findViewById(R.id.task1_checkbox);
        CheckBox task2Checkbox = view.findViewById(R.id.task2_checkbox);
        TextView motivationalMessage = view.findViewById(R.id.motivational_message);
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String task1 = sharedPreferences.getString("task1", "");
        String task2 = sharedPreferences.getString("task2", "");
        boolean task1Completed = sharedPreferences.getBoolean("task1 Completed", false);
        boolean task2Completed = sharedPreferences.getBoolean("task2 Completed", false);

        task1Input.setText(task1);
        task2Input.setText(task2);
        task1Checkbox.setChecked(task1Completed);
        task2Checkbox.setChecked(task2Completed);
        if (task1Completed) {
            task1Input.setEnabled(false);
            task1Checkbox.setEnabled(false);
        }
        if (task2Completed) {
            task2Input.setEnabled(false);
            task2Checkbox.setEnabled(false);
        }

        task1Checkbox.setOnClickListener(v -> {
            if (task1Checkbox.isChecked()) {
                new AlertDialog.Builder(getContext())
                    .setTitle("Confirm")
                    .setMessage("Are you sure you want to mark this task as completed?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        task1Input.setEnabled(false);
                        task1Checkbox.setEnabled(false);
                        showMotivationalMessage();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("task1Completed", true);
                        editor.putString("task1", task1Input.getText().toString());
                        editor.apply();
                    })
                    .setNegativeButton("No", (dialog, which) -> task1Checkbox.setChecked(false))
                    .show();
            }
        });

        task2Checkbox.setOnClickListener(v -> {
            if (task2Checkbox.isChecked()) {
                new AlertDialog.Builder(getContext())
                    .setTitle("Confirm")
                    .setMessage("Are you sure you want to mark this task as completed?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        task2Input.setEnabled(false);
                        task2Checkbox.setEnabled(false);
                        showMotivationalMessage();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("task2Completed", true);
                        editor.putString("task2", task2Input.getText().toString());
                        editor.apply();
                    })
                    .setNegativeButton("No", (dialog, which) -> task2Checkbox.setChecked(false))
                    .show();
            }
        });



        task1Checkbox.setOnClickListener(v -> {
            if (task1Checkbox.isChecked()) {
                new AlertDialog.Builder(getContext())
                    .setTitle("Confirm")
                    .setMessage("Are you sure you want to mark this task as completed?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        task1Input.setEnabled(false);
                        task1Checkbox.setEnabled(false);
                        showMotivationalMessage();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("task 1 Completed", true);
                        editor.apply();
                    })
                    .setNegativeButton("No", (dialog, which) -> task1Checkbox.setChecked(false))
                    .show();
            }
        });

        task2Checkbox.setOnClickListener(v -> {
            if (task2Checkbox.isChecked()) {
                new AlertDialog.Builder(getContext())
                    .setTitle("Confirm")
                    .setMessage("Are you sure you want to mark this task as completed?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        task2Input.setEnabled(false);
                        task2Checkbox.setEnabled(false);
                        showMotivationalMessage();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("task 2 Completed", true);
                        editor.apply();
                    })
                    .setNegativeButton("No", (dialog, which) -> task2Checkbox.setChecked(false))
                    .show();
            }
        });

         return view;
     }

     private void showMotivationalMessage() {
         String[] messages = getResources().getStringArray(R.array.motivational_messages);
         Random random = new Random();
         String message = messages[random.nextInt(messages.length)];
         Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
     }
 }