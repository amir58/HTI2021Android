package com.amirmohammed.hti2021androidone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.amirmohammed.hti2021androidone.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        changeFragment("active");

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);

                int itemId = item.getItemId();
                if (itemId == R.id.item_tasks) {
                    changeFragment("active");
                } else if (itemId == R.id.item_done) {
                    changeFragment("done");
                } else if (itemId == R.id.item_archive) {
                    changeFragment("archive");
                }
                return false;
            }
        });
    }

    public void changeFragment(String status) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new TasksFragment(status))
                .commit();
    }


    public void openInsertSheet(View view) {
        InsertTaskSheetFragment sheetFragment = new InsertTaskSheetFragment(iInsertTask);
        sheetFragment.show(getSupportFragmentManager(), "insertSheet");
    }


    IInsertTask iInsertTask = new IInsertTask() {
        @Override
        public void onTaskInserted() {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new TasksFragment("active"))
                    .commit();
        }
    };

}