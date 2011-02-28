/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp.action;

import com.uip.todoapp.domain.Tag;
import com.uip.todoapp.domain.Task;
import com.uip.todoapp.service.ServiceFactory;
import com.uip.todoapp.service.ToDoService;
import com.uip.todoapp.utility.XMLParser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;

/**
 *
 * @author sujan
 */
public class TaskController {

    private static TaskController controller;
    private ToDoService toDoService;

    private TaskController() {
        toDoService = ServiceFactory.getToDoService();
    }

    static public TaskController getInstance() {
        if (controller == null) {

            return new TaskController();
        }
        return controller;
    }
    /*
     *  add new task to xml
     *  @param t to add in xml
     */

    public void addTask(Task t) {
        toDoService.addTask(t);

    }
    /*
     *   delete task from xml
     *   @param t to delete from xml
     */

    public void deleteTask(Task t) {
        toDoService.deleteTask(t);
    }
    /*
     *  This method to get all tag from xml
     *  @return list for Tag object
     */

    public List<Tag> getAllTag() {
        return toDoService.getAllTag();
    }
    /*
     *  edit selected task in xml
     *  @param t to edit in xml
     *
     */

    public void editTask(Task t) {
        toDoService.editTask(t);
    }

    /*
     *  This method to get all task from xml
     *  @return List of Task
     */
    public List<Task> getAllTask() {
        return toDoService.getAllTask();
    }
    /*
     *  This method to get selected  task from xml
     *  @return instance of Task
     */

    public Task getTask(Task t) {
        return toDoService.getTask(t);

    }
    /*
     *   This method to add new tag to xml
     *  @param t is new Tag
     */

    public void addTag(Tag t) {
        toDoService.addTag(t);

    }
    /*
     *   This method to delete new tag from xml
     *   @param t , delete from xml
     */

    public void deleteTag(Tag t) {
        toDoService.deleteTag(t);
    }
    /*
     *  This method to check whether this tag is used
     *  before delete
     */

    public boolean isTagUsed(Tag tag) {
        return toDoService.isTagUsed(tag);
    }
}
