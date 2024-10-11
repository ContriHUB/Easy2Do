package com.mukudev.easy2do;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.Menu;

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
        Toolbar toolbar = findViewById(R.id.toolBar);//object of ToolBar
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragment2DayRule())
                    .commit();

            toolbar.setTitle("Building Habits Can be Tough");//changing according to fragment

            bottomNavigationView.setSelectedItemId(R.id.nav_2day_rule);

        }


        // Set the BottomNavigationView listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_2min_task_rule) {
                    selectedFragment = new Fragment2MinTaskRule();
                    toolbar.setTitle("Get Things Done Faster!");//changing according to fragment

                }
                else if (item.getItemId() == R.id.nav_2day_rule) {
                    selectedFragment = new Fragment2DayRule();
                    toolbar.setTitle("Building Habits Can be Tough");//changing according to fragment

                }
                else{
                    selectedFragment = new Fragment2PriorityTaskRule();
                    toolbar.setTitle("Priority Tasks for Today");//changing according to fragment

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
}