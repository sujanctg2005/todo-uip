/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp.service;

import com.uip.todoapp.domain.Tag;
import com.uip.todoapp.domain.Task;
import java.util.List;

/**
 *
 * @author sujan
 */
public interface ToDoService {

    public void addTask(Task task);
    public Task getTask(Task task);
    public void deleteTask(Task task);
    public List<Task> viewTaskByTag(Tag tag);
    public List<Task> getAllTask();
    public List<Tag> getAllTag();
    
    public void addTag(Tag tag);
     public void editTask(Task t) ;
     public boolean isTagUsed(Tag tag);
     public void deleteTag(Tag t);
}
