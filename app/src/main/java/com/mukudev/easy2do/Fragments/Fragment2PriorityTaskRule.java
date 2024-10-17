package com.mukudev.easy2do.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
        view view = inflater.inflate(R.layout.fragment_2priority_task_rule, container, false);

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
        view.findViewById(R.id.save_button).setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("task1", task1Input.getText().toString());
            editor.putString("task2", task2Input.getText().toString());
            editor.apply();
        });
        
        task1Checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                task1Input.setEnabled(false);
                task1Checkbox.setEnabled(false);
                motivationalMessage.setText("Great, you have your first priority task");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("task1 Completed", true);
                editor.apply();
            }
        });
        
        task2Checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                task2Input.setEnabled(false);
                task2Checkbox.setEnabled(false);
                motivationalMessage.setText("Awesome! You've completed both priority tasks!");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("task2Completed", true);
                editor.apply();
            }
        });
        return view;
    }
}