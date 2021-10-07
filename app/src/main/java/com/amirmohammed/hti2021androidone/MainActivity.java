package com.amirmohammed.hti2021androidone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.amirmohammed.hti2021androidone.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding  binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new TasksFragment())
                .commit();
    }


    public void openInsertSheet(View view) {
        InsertTaskSheetFragment sheetFragment = new InsertTaskSheetFragment();
        sheetFragment.show(getSupportFragmentManager(), "insertSheet");
    }
}