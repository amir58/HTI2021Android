package com.amirmohammed.hti2021androidone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class TasksFragment extends Fragment {

    RecyclerView recyclerView;
    String status;

    public TasksFragment(String status) {
        this.status = status;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view;
        List<Task> taskList;

        switch (status) {
            case "active":
                taskList = TasksDatabase.getInstance(requireContext())
                        .taskDao().getActiveTasks();
                break;
            case "done":
                taskList = TasksDatabase.getInstance(requireContext())
                        .taskDao().getDoneTasks();
                break;
            case "archive":
                taskList = TasksDatabase.getInstance(requireContext())
                        .taskDao().getArchiveTasks();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + status);
        }

        TasksAdapter tasksAdapter = new TasksAdapter(taskList, requireContext());

        recyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                tasksAdapter.onSwiped(viewHolder.getAdapterPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

}