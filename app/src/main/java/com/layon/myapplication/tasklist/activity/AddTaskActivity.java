package com.layon.myapplication.tasklist.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.layon.myapplication.tasklist.R;
import com.layon.myapplication.tasklist.helper.TaskDAO;
import com.layon.myapplication.tasklist.model.Task;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText editText;
    private Task atualTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editText = findViewById(R.id.textTask);

        //Recover the task passed by intent
        atualTask = (Task) getIntent().getSerializableExtra("taskSelected");

        //config the textbox
        if(atualTask != null){
            editText.setText(atualTask.getTaskName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSave:

                TaskDAO taskDAO = new TaskDAO(getApplicationContext());
                if(atualTask != null) { //edit
                    String nameTask = editText.getText().toString();
                    if(!nameTask.isEmpty()) {
                        Task task = new Task();
                        task.setTaskName(nameTask);
                        task.setId(atualTask.getId());

                        //update of data base
                        if (taskDAO.update(task)){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao atualizar tarefa!",
                                    Toast.LENGTH_SHORT);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Error ao atualizar tarefa!",
                                    Toast.LENGTH_SHORT);
                        }
                    }

                } else {
                    //execute action to save
                    String nameTask = editText.getText().toString();
                    if(!nameTask.isEmpty()) {
                        Task task = new Task();
                        task.setTaskName(nameTask);
                        if(taskDAO.save(task)) {
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao salvar tarefa!",
                                    Toast.LENGTH_SHORT);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao salvar tarefa!",
                                    Toast.LENGTH_SHORT);
                        }

                    }
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}