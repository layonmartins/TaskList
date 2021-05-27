package com.layon.myapplication.tasklist.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.layon.myapplication.tasklist.R;
import com.layon.myapplication.tasklist.adapter.TaskAdapter;
import com.layon.myapplication.tasklist.databinding.ActivityMainBinding;
import com.layon.myapplication.tasklist.helper.DbHelper;
import com.layon.myapplication.tasklist.helper.RecyclerItemClickListener;
import com.layon.myapplication.tasklist.helper.TaskDAO;
import com.layon.myapplication.tasklist.model.Task;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ActivityMainBinding binding;
    private List<Task> taskList = new ArrayList<>();
    private Task SelectedTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        recyclerView = findViewById(R.id.recyclerView);

        //add click event
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Log.i("clicked", "onItemClick");
                                //Recuperar task for edition
                                Task taskSelected = taskList.get(position);
                                //Send task to add task activity
                                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                                intent.putExtra("taskSelected", taskSelected);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Log.i("clicked", "onLongItemClick");
                                //Recuperar task for edition
                                Task taskSelected = taskList.get(position);
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                //configure title
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir a tarefa: " + taskSelected.getTaskName() + "?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        TaskDAO taskDao = new TaskDAO(getApplicationContext());
                                        if(taskDao.delete(taskSelected)){
                                            loadingTaskList();
                                            Toast.makeText(getApplicationContext(),
                                                    "Sucesso ao excluir tarefa!",
                                                    Toast.LENGTH_SHORT);
                                        } else {
                                            Toast.makeText(getApplicationContext(),
                                                    "Error ao excluir tarefa!",
                                                    Toast.LENGTH_SHORT);
                                        }

                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                //Show dialog
                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadingTaskList(){
        //list tasks
        TaskDAO taskDAO = new TaskDAO(getApplicationContext());
        taskList = taskDAO.list();
        for(Task task : taskList) {
            Log.i("layon.f", "task: " + task.getTaskName());
        }


        //setup adapter
        taskAdapter = new TaskAdapter(taskList);

        //setup recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(taskAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadingTaskList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}