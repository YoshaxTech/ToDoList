package com.epitech.ToDoList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.epitech.ToDoList.DataBase.DataBaseH;
import com.epitech.ToDoList.DataBase.TaskContract;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView mTaskListView;
    private ListView mDescriptionListView;
    private ListView mDHListView;
    private DataBaseH mHelper;
    private ArrayAdapter<String> mAdapter;
    private ArrayAdapter<String> mAdap;
    private ArrayAdapter<String> mAdapH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new DataBaseH(this);
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        mDescriptionListView = (ListView) findViewById(R.id.list_todo);
        mDHListView = (ListView) findViewById(R.id.list_todo);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                /*
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                */

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.alert_dialog, null);

                final EditText etTask = alertLayout.findViewById(R.id.et_task);
                final EditText etDescription = alertLayout.findViewById(R.id.et_description);
                final EditText etDh = alertLayout.findViewById(R.id.et_dh);
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Tasks");
                alert.setView(alertLayout);
                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = etTask.getText().toString();
                        String description = etDescription.getText().toString();
                        String dh = etDh.getText().toString();
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                        values.put(TaskContract.TaskEntry.COL_TASK_DESCRIPTION, description);
                        values.put(TaskContract.TaskEntry.COL_TASK_DH, dh);
                        db.insertWithOnConflict(TaskContract.TaskEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);

                        /*
                        ContentValues value = new ContentValues();
                        value.put(DatabaseTask.TaskEntry.COL_TASK_DESCRIPTION,description);
                        db.insertWithOnConflict(DatabaseTask.TaskEntry.TABLE, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                        */

                        db.close();
                        updateUI();
                    }
                });
                alert.setNegativeButton("Cancel", null);
                AlertDialog dialog = alert.create();
                dialog.show();



                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_dh);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_DH + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

    private void updateUI() {
        ArrayList<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();

        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE, TaskContract.TaskEntry.COL_TASK_DESCRIPTION, TaskContract.TaskEntry.COL_TASK_DH},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            int idy = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DESCRIPTION);
            int idz = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DH);
            taskList.add(new Task(cursor.getString(idx), cursor.getString(idy), cursor.getString(idz)));
        }

        CustomAdaptater customAdaptater = new CustomAdaptater(this, R.layout.item_todo, taskList);
        mTaskListView.setAdapter(customAdaptater);
        cursor.close();
        db.close();
    }
}