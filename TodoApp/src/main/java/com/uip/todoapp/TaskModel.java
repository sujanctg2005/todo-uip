/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import com.uip.todoapp.domain.Task;
import com.uip.todoapp.utility.Utility;

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

    public TaskModel() {
        resourceMap = org.jdesktop.application.Application.getInstance(TodoApplication.class).getContext().getResourceMap(TaskListTable.class);
        columnNames = new String[]{resourceMap.getString("taskListTable.taskName"),
                    resourceMap.getString("taskListTable.date"),
                    resourceMap.getString("taskListTable.priority"),
                    resourceMap.getString("taskListTable.tag"),
                    resourceMap.getString("taskListTable.progress")};


    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return taskList.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int column) {
        Task t = taskList.elementAt(row);
        return t;
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @Override
    public Class getColumnClass(int c) {
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
        if (col < 2) {
            return false;
        } else {
            return true;
        }
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
        }else if (column == 1) {
            t.setDueDate(Utility.parseDate(aValue.toString()));
        }else if (column == 2) {
            t.setPriority(aValue.toString());
        }else if (column == 3) {
            t.setTag(aValue.toString());
        }else if (column == 4) {
            t.setProgress(Integer.parseInt(aValue.toString()));
        }else if (column == 5) {
            t.setDescription(aValue.toString());
        }

       
        fireTableCellUpdated(row, column);
    }

/*
 *  add update task object in data model
 */
    public void setValueAt(Task t , int row){
      


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
        taskList.add( row,data);
        justifyRows(row, row + 1);
        fireTableRowsInserted(row, row);
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

    public void removeRow(int row) {
        taskList.remove(row);
        fireTableRowsDeleted(row, row);
    }
}
