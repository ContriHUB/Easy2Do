package com.mukudev.easy2do;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mukudev.easy2do.Fragments.Fragment2DayRule;
import com.mukudev.easy2do.Fragments.Fragment2PriorityTaskRule;
import com.mukudev.easy2do.Fragments.Fragment2MinTaskRule;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragment2DayRule())
                    .commit();

            bottomNavigationView.setSelectedItemId(R.id.nav_2day_rule);

        }


        // Set the BottomNavigationView listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_2min_task_rule) {
                    selectedFragment = new Fragment2MinTaskRule();

                }
                else if (item.getItemId() == R.id.nav_2day_rule) {
                    selectedFragment = new Fragment2DayRule();

                }
                else{
                    selectedFragment = new Fragment2PriorityTaskRule();

                }

                // Replace the fragment
                //added transition for fragment change
                getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();

                return true;
            }
        });
    }
}