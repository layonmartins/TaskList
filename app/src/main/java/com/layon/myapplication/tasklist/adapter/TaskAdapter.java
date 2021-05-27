package com.layon.myapplication.tasklist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.layon.myapplication.tasklist.R;
import com.layon.myapplication.tasklist.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<Task> taskList;

    public TaskAdapter(List<Task> list) {
        this.taskList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_adapter, parent, false);

        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.MyViewHolder holder, int position) {

        Task task = taskList.get(position);
        holder.task.setText(task.getTaskName());
    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView task;

        public MyViewHolder(View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.textTask);
        }
    }

}
