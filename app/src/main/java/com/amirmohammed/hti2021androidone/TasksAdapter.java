package com.amirmohammed.hti2021androidone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.amirmohammed.hti2021androidone.databinding.ItemTaskBinding;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> {

    List<Task> taskList;
    Context context;

    public TasksAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    public void onSwiped(int adapterPosition){
        TasksDatabase.getInstance(context).taskDao().deleteTask(taskList.get(adapterPosition));

        taskList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);

    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemTaskBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_task,
                parent,
                false
        );
        return new TaskHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {

        Task task = taskList.get(position);
        holder.binding.setTask(task);


        holder.binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setStatus("done");
                TasksDatabase.getInstance(v.getContext()).taskDao().updateTask(task);
                taskList.remove(position);
                notifyItemRemoved(position);
            }
        });

        holder.binding.archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setStatus("archive");
                TasksDatabase.getInstance(v.getContext()).taskDao().updateTask(task);
                taskList.remove(position);
                notifyItemRemoved(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    class TaskHolder extends RecyclerView.ViewHolder {
        ItemTaskBinding binding;

        public TaskHolder(@NonNull ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
