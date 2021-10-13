package com.amirmohammed.hti2021androidone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amirmohammed.hti2021androidone.databinding.FragmentInsertTaskSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class InsertTaskSheetFragment extends BottomSheetDialogFragment {

    FragmentInsertTaskSheetBinding binding;
    IInsertTask iInsertTask;

    public InsertTaskSheetFragment(IInsertTask iInsertTask) {
     this.iInsertTask = iInsertTask;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_insert_task_sheet, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.insertTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.title.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(requireContext(), "title required", Toast.LENGTH_SHORT).show();
                    return;
                }

                String date = binding.date.getText().toString();

                if (date.isEmpty()) {
                    Toast.makeText(requireContext(), "date required", Toast.LENGTH_SHORT).show();
                    return;
                }

                String time = binding.time.getText().toString();

                if (time.isEmpty()) {
                    Toast.makeText(requireContext(), "time required", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User(1, "Amir");

                Task task = new Task(title, date, time, user);

                TasksDatabase.getInstance(requireContext())
                        .taskDao().insertTask(task);

                dismiss();

                iInsertTask.onTaskInserted();
            }
        });


    }
}