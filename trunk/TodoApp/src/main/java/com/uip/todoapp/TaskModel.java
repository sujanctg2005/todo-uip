/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import com.uip.todoapp.domain.Task;
import com.uip.todoapp.utility.Utility;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author sujan
 */
public class TaskModel extends AbstractTableModel {

    private String[] columnNames;
    Vector<Task> taskList = new Vector<Task>();
    ResourceMap resourceMap;
    TaskListModel listModel;  // list view task model to synchronize task with table and list
    List<AbstractTaskListener> dataListener = new ArrayList<AbstractTaskListener>();

    public TaskModel() {
        resourceMap = org.jdesktop.application.Application.getInstance(TodoApplication.class).getContext().getResourceMap(TaskListTable.class);
        columnNames = new String[]{resourceMap.getString("taskListTable.taskName"),
                    resourceMap.getString("taskListTable.date"),
                    resourceMap.getString("taskListTable.priority"),
                    resourceMap.getString("taskListTable.tag"),
                    resourceMap.getString("taskListTable.progress"), "."};


    }

    public void addListener(AbstractTaskListener listener) {
        dataListener.add(listener);

    }
    /*   get total column
     *  @return number of column
     */ public int getColumnCount() {
        return columnNames.length;
    }
    /* get total row
     *  @return number of row in data model
     */

    public int getRowCount() {
        return taskList.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }
    /*
     *  get a single task from data model
     * using given row and column is here optional
     */

    public Object getValueAt(int row, int column) {
        if (row >= taskList.size()) {
            return "";
        }

        Task t = taskList.elementAt(row);
        if (column == 0) {
            return t.getTaskName();
        } else if (column == 1) {

            return t.getDueDate();
        } else if (column == 2) {

            return t.getPriority();
        } else if (column == 3) {
            return t.getTag();

        } else if (column == 4) {
            return t.getProgress();
        } else if (column == 5) {
            return t.getId();
        } else {
            return "";
        }

    }
    /*   get task from data model by index
     *  @return  Task instance
     */

    public Object getValueAt(int row) {

        Task t = taskList.elementAt(row);
        return t;
    }

    /*
     *
     */
    public Task getTaskByID(Integer taskId) {
        for (Task t : taskList) {
            if (t.getId().intValue() == taskId.intValue()) {
                return t;

            }
        }

        return null;
    }


    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @Override
    public Class getColumnClass(int c) {
        // if first row task date is null then it will
        //create exception to get class type

        if (getValueAt(0, c) == null) {
            return (new Date()).getClass();
        }

        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return false;
    }

    /*
     * update data in specific cell
     * @param value to be set in cell
     * @param row ,cell row number
     * @parma column ,cell column number
     */
    public void setValueAt(Object aValue, int row, int column) {



        Task t = taskList.elementAt(row);
        if (column == 0) {
            t.setTaskName(aValue.toString());
        } else if (column == 1) {
            t.setDueDate(Utility.parseDate(aValue.toString()));
        } else if (column == 2) {
            t.setPriority(aValue.toString());
        } else if (column == 3) {
            t.setTag(aValue.toString());
        } else if (column == 4) {
            t.setProgress(Integer.parseInt(aValue.toString()));
        } else if (column == 5) {
            t.setDescription(aValue.toString());
        }


        fireTableCellUpdated(row, column);

        for (AbstractTaskListener listener : dataListener) {

            listener.updateTaskEvent(row);
        }

    }




    /*
     *  add update task object in data model
     */
    public void setValueAt(Task t, int row) {

        row=geteDataIndex(t.getId());

//
        setValueAt(t.getTaskName(), row, 0);
        setValueAt(Utility.formatDateShort(t.getDueDate()), row, 1);
        setValueAt(t.getPriority(), row, 2);
        setValueAt(t.getTag(), row, 3);
        setValueAt(t.getProgress(), row, 4);
        setValueAt(t.getDescription(), row, 5);



    }
    /*
     *  insert new row in data model
     * and fire table event 
     */

    public void insertRow(int row, Task data) {
        taskList.add(row, data);
        justifyRows(row, row + 1);
        fireTableRowsInserted(row, row);
        for (AbstractTaskListener listener : dataListener) {

            listener.newTaskEvent(row);
        }
    }

    //
// Manipulating rows
//
    private void justifyRows(int from, int to) {
        // Sometimes the DefaultTableModel is subclassed
        // instead of the AbstractTableModel by mistake.
        // Set the number of rows for the case when getRowCount
        // is overridden.
        taskList.setSize(getRowCount());

        for (int i = from; i < to; i++) {
            if (taskList.elementAt(i) == null) {
                taskList.setElementAt(new Task(), i);
            }

        }
    }

    /*
       * after sorting the table table index is chnaged ,
       *  so table index and data index won't be same
       */

    public int geteDataIndex(Integer taskId){

        int i = 0;
            // after sorting the table table index is chnaged ,
            // so table index and data index won't be same
            for (Task task : taskList) {
                i++;
                if (task.getId().intValue() == taskId.intValue()) {
                    break;

                }
            }
            i--;
            return i;
    }


    /*
     *  remove task from  data model
     * @param taskId Id of task to delete
     */

    public void removeRow(Integer taskId, int row) {
        int i = geteDataIndex(taskId);
      
       
        taskList.remove(i);
        fireTableRowsDeleted(i, i);
        for (AbstractTaskListener listener : dataListener) {

            listener.removeTaskEvent(i);  // here data index will be same to data model , table index is changed after sort
        }
    }
}
