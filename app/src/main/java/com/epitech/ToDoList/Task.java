package com.epitech.ToDoList;

/**
 * Created by Yoshax on 04/02/2018.
 */

public class Task {

    String taskTitle;
    String taskDescription;
    String taskDh;

    public Task(String taskTitle,String taskDescription, String taskDh)
    {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDh = taskDh;
    }
    public String getTaskTitle()
    {
        return taskTitle;
    }
    public String getTaskDescription()
    {
        return taskDescription;
    }

    public String getTaskDh()
    {
        return taskDh;
    }
}
