package com.suonk.oc_project5.ui.tasks.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.suonk.oc_project5.databinding.ItemTaskBinding;
import com.suonk.oc_project5.events.OnTaskEventListener;

public class TasksListAdapter extends ListAdapter<TasksViewState, TasksListAdapter.TaskViewHolder> {

    private final OnTaskEventListener callBack;

    public TasksListAdapter(OnTaskEventListener callBack) {
        super(new TasksListAdapter.TaskItemCallBack());
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.onBind(getItem(position), callBack);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final ItemTaskBinding binding;

        public TaskViewHolder(@NonNull ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(TasksViewState task, OnTaskEventListener callBack) {
            binding.taskName.setText(task.getTaskName());
            binding.imgProject.setImageResource(task.getColor());

            binding.imgDelete.setOnClickListener(view -> callBack.onTaskDelete(task.getId()));
        }
    }

    static class TaskItemCallBack extends DiffUtil.ItemCallback<TasksViewState> {
        @Override
        public boolean areItemsTheSame(@NonNull TasksViewState oldUser, @NonNull TasksViewState newUser) {
            return oldUser.equals(newUser);
        }

        @Override
        public boolean areContentsTheSame(@NonNull TasksViewState oldUser, @NonNull TasksViewState newUser) {
            return oldUser.getId() == newUser.getId() &&
                    oldUser.getTaskName().equals(newUser.getTaskName()) &&
                    oldUser.getColor() == newUser.getColor();
        }
    }
}
