/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import javax.swing.AbstractListModel;

/**
 *
 * @author sujan
 */

/*
 * this class is adapter model to view task list
 */
public class TaskListModel<E> extends AbstractListModel<E>
        implements AbstractTaskListener
        {

    TaskModel taskModel;  // share same model with jtable and list

    public TaskListModel(TaskModel taskModel) {
        this.taskModel = taskModel;
        taskModel.addListener(this);

    }
    /*
     *  fire event if data model content change
     */

    public void updateTaskList(int index) {
        fireContentsChanged(this, index, index);
    }
    /*
     *  get size of data model
     */

    public int getSize() {
        return taskModel.getRowCount();
    }
    /*
     *  get Elements of given index form data model
     */

    public E getElementAt(int index) {
        return (E) taskModel.getValueAt(index);
    }

    public void updateTaskEvent(int index) {
        fireContentsChanged(this, index, index);
    }

    public void removeTaskEvent(int index) {
         fireIntervalRemoved(this, index, index);
    }

    public void newTaskEvent(int index) {
       fireIntervalAdded(this, index, index);
    }
}
