package com.epitech.ToDoList;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Yoshax on 04/02/2018.
 */

public class CustomAdaptater extends ArrayAdapter<Task> {

    ArrayList<Task> taskList = new ArrayList<>();

    public CustomAdaptater(Context context, int textViewResourceId, ArrayList<Task> objects) {
        super(context, textViewResourceId, objects);
        taskList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_todo, null);
        TextView textViewTitle = (TextView) v.findViewById(R.id.task_title);
        TextView textViewDescr = (TextView) v.findViewById(R.id.task_description);
        TextView textViewDh = (TextView) v.findViewById(R.id.task_dh);
        textViewTitle.setText(taskList.get(position).getTaskTitle());
        textViewDescr.setText(taskList.get(position).getTaskDescription());
        textViewDh.setText(taskList.get(position).getTaskDh());
        return v;

    }
}
