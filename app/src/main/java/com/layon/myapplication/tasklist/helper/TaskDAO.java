package com.layon.myapplication.tasklist.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.layon.myapplication.tasklist.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements ITaskDAO{

    private SQLiteDatabase writer;
    private SQLiteDatabase reader;

    public TaskDAO(Context context) {
        DbHelper db = new DbHelper(context);
        writer = db.getWritableDatabase();
        reader = db.getReadableDatabase();
    }

    @Override
    public boolean save(Task task) {

        ContentValues cv = new ContentValues();
        cv.put("name", task.getTaskName());

        try {
            writer.insert(DbHelper.TABLE_TASK, null, cv);
            Log.i("layon.f", "Tarefa salva com sucesso!");
        } catch (Exception e) {
            Log.e("layon.f", "Erro ao salvar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Task task) {
        ContentValues cv = new ContentValues();
        cv.put("name", task.getTaskName());

        try {
            String[] args = {task.getId().toString()};
            writer.update(DbHelper.TABLE_TASK, cv, "id=?", args);
            Log.i("layon.f", "Tarefa atualizada com sucesso!");
        } catch (Exception e) {
            Log.e("layon.f", "Erro ao atualizar tarefa " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Task task) {
        try {
            String[] args = {task.getId().toString()};
            writer.delete(DbHelper.TABLE_TASK, "id=?", args);
            Log.i("layon.f", "Tarefa removida com sucesso!");
        } catch (Exception e) {
            Log.e("layon.f", "Erro ao remover tarefa " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Task> list() {
        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABLE_TASK + " ;";
        Cursor c = reader.rawQuery(sql, null);

        while( c.moveToNext()) {
            Task task = new Task();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nameTask = c.getString(c.getColumnIndex("name"));

            task.setId(id);
            task.setTaskName(nameTask);

            tasks.add(task);
        }

        return tasks;
    }
}
