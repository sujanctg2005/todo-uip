/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import com.uip.todoapp.action.TaskController;
import com.uip.todoapp.domain.Task;
import com.uip.todoapp.utility.Utility;
import java.awt.Component;
import org.jdesktop.application.Action;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;
import javax.swing.JComponent;

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

    private JTable taskListTable;
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
        taskListTable = new JTable(taskListTablemodel);
        taskListTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        taskListTable.setFillsViewportHeight(true);
//        TableCellRenderer defaultRenderer;
//
//        defaultRenderer = taskListTable.getDefaultRenderer(JProgressBar.class);
        taskListTable.setDefaultRenderer(JProgressBar.class,
                new ProgRenderer());
        taskListTable.getColumn("Progress").setCellRenderer(new ProgRenderer());
        taskListTable.getColumn(".").setMaxWidth(0);
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
        taskListTablemodel.insertRow(0, new Object[]{t.getTaskName(),
                    Utility.formatDateShort(t.getDueDate()),
                    t.getPriority(), t.getTag(),
                    createBar(t.getProgress(), t.getProgress() + "%"), t.getId()});


    }
    /*
     *  update selected task
     *  @param t instance of task
     */

    public void updateTask(Task t) {

        int row = taskListTable.getSelectedRow();
        taskListTablemodel.setValueAt(t.getTaskName(), row, 0);
        taskListTablemodel.setValueAt(Utility.formatDateShort(t.getDueDate()), row, 1);
        taskListTablemodel.setValueAt(t.getPriority(), row, 2);
        taskListTablemodel.setValueAt(t.getTag(), row, 3);
        taskListTablemodel.setValueAt(createBar(t.getProgress(), t.getProgress() + "%"), row, 4);
        taskListTablemodel.setValueAt(t.getId(), row, 5);
    }
    /*
     *  this method to create progress bar
     *  @param percentDone , what Percentage of work finish
     *  @param text , title for progress bar
     */

    public JProgressBar createBar(int percentDone, String text) {
        JProgressBar progressBar = new JProgressBar(0, 100);

        progressBar.setStringPainted(true);
        progressBar.setValue(percentDone);
        progressBar.setString(text);

        return progressBar;
    }
    /*
     *  This cell renderer is to render progress bar inside
     *  JTable
     */

    class ProgRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return (JProgressBar) value;
        }

        public JProgressBar createBar(int percentDone, String text) {
            JProgressBar progressBar = new JProgressBar(0, 100);

            progressBar.setStringPainted(true);
            progressBar.setValue(percentDone);
            progressBar.setString(text);

            return progressBar;
        }
    }
    /*
     *   delete task from window
     */

    @Action
    public void delete() {

        int row[] = taskListTable.getSelectedRows();
        for (int i = 0; i < row.length; i++) {

            taskController.deleteTask(new Task((Integer) taskListTablemodel.getValueAt(row[i], 5)));
            taskListTablemodel.removeRow(row[i]);


        }

    }
    /*
     *  to edit selected task
     */

    @Action
    public void edit() {
        int row = taskListTable.getSelectedRow();
        Task t = new Task((Integer) taskListTablemodel.getValueAt(row, 5));
        t = taskController.getTask(t);
        System.out.println("Table  " + t.toSting());
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
