/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import com.uip.todoapp.action.TaskController;
import com.uip.todoapp.domain.Task;

import org.jdesktop.application.Action;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;


import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;



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
    final TableRowSorter<TableModel> sorter; // table data sorter
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

        taskListTable.getColumn(".").setMaxWidth(0);
        sorter = new TableRowSorter<TableModel>(taskListTablemodel);
        taskListTable.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        sortKeys.add(new RowSorter.SortKey(4, SortOrder.UNSORTED));
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.UNSORTED));
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.UNSORTED));
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.UNSORTED));
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.UNSORTED));
        sorter.setSortKeys(sortKeys);

        final JPopupMenu contextMenu = new JPopupMenu("Edit");
        contextMenu.add(makeMenuItem("edit"));
        contextMenu.add(makeMenuItem("delete"));
        contextMenu.add(makeMenuItem("complete"));


        taskListTable.setComponentPopupMenu(contextMenu);
        loadTask();
    }

    /*
     *  get table default sorter
     *  @return TableRowSorter instance
     */
    public TableRowSorter<TableModel> getSorter() {
        return sorter;
    }

    /*
     *  this method to get the table model
     */
    public TaskModel getTaskListTablemodel() {
        return taskListTablemodel;

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

            Integer id = new Integer(taskListTable.getValueAt(row[i], 5).toString());
            Task t = taskListTablemodel.getTaskByID(id);
            taskController.deleteTask(t);
            taskListTablemodel.removeRow(t.getId(), row[i]);


        }

    }
    /*
     *  to edit selected task
     */

    @Action
    public void edit() {
        int row = taskListTable.getSelectedRow();

        Integer id = new Integer(taskListTable.getValueAt(row, 5).toString());

        Task t = taskListTablemodel.getTaskByID(id);



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
