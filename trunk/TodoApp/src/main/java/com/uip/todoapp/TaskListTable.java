/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import com.uip.todoapp.action.TaskController;
import com.uip.todoapp.domain.Task;
import com.uip.todoapp.utility.Utility;
import java.awt.Color;
import java.awt.Component;
import org.jdesktop.application.Action;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JLabel;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author sujan
 */
public class TaskListTable {

    private TaskTable taskListTable;
    private TaskModel taskListTablemodel;
    ResourceMap resourceMap;
    javax.swing.ActionMap actionMap;
    TaskController taskController;
    TodoForm mainForm;

    /*
     *   default constructor
     *   @param mainForm to access main frame
     */
    public TaskListTable(TodoForm mainForm) {
        this.mainForm = mainForm;
        resourceMap = org.jdesktop.application.Application.getInstance(TodoApplication.class).getContext().getResourceMap(TaskListTable.class);

        actionMap = org.jdesktop.application.Application.getInstance(com.uip.todoapp.TodoApplication.class).getContext().getActionMap(TaskListTable.class, this);
        taskController = TaskController.getInstance();
        taskListTablemodel = new TaskModel();
        taskListTable = new TaskTable(taskListTablemodel);
        taskListTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        taskListTable.setFillsViewportHeight(true);


        final JPopupMenu contextMenu = new JPopupMenu("Edit");
        contextMenu.add(makeMenuItem("edit"));
        contextMenu.add(makeMenuItem("delete"));
        contextMenu.add(makeMenuItem("complete"));


        taskListTable.setComponentPopupMenu(contextMenu);
        loadTask();

    }

    /*
     *  create menu item
     */
    private JMenuItem makeMenuItem(String label) {
        JMenuItem item = new JMenuItem(actionMap.get(label));

        return item;
    }

    public JComponent getTasklistTable() {

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(taskListTable);

        //Add the scroll pane to this panel.
        return scrollPane;


    }
    /*
     *  add task to table from xml
     *
     */

    public void loadTask() {
        List<Task> data = taskController.getAllTask();

        for (Task t : data) {
            addTask(t);
        }

    }
    /*
     *  add new task to model
     *  @param t instance of task
     */

    public void addTask(Task t) {
        System.out.println("tag" + t.getTag());

        taskListTablemodel.insertRow(0, t);


    }
    /*
     *  update selected task
     *  @param t instance of task
     */

    public void updateTask(Task t) {

        int row = taskListTable.getSelectedRow();
       taskListTablemodel.setValueAt(t, row);

    }
   
 
    /*
     *   delete task from window
     */

    @Action
    public void delete() {

        int row[] = taskListTable.getSelectedRows();
        for (int i = 0; i < row.length; i++) {

            taskController.deleteTask( (Task)taskListTablemodel.getValueAt(row[i], 0));
            taskListTablemodel.removeRow(row[i]);


        }

    }
    /*
     *  to edit selected task
     */

    @Action
    public void edit() {
        int row = taskListTable.getSelectedRow();
        Task t = (Task) taskListTablemodel.getValueAt(row, 0);
        
        mainForm.getTaskFrame().showTask(t);

        mainForm.showTaskForm(); // show task window

    }
    /*
     *  to complete selected task
     */

    @Action
    public void complete() {
        System.out.println("cc");
    }
}
