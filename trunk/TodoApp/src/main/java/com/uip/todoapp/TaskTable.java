/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import com.uip.todoapp.domain.Task;
import com.uip.todoapp.utility.Utility;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author sujan
 */
public class TaskTable extends JTable {

    /*
     *  return default cell renderer for all column
     */
    public TaskTable(AbstractTableModel model) {

        super.setModel(model);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {

        return new ProgRenderer();
    }

    /*
     *  This cell renderer is to render progress bar inside
     *  JTable
     */
    class ProgRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Task t = (Task) value;
            JLabel lable = new JLabel();
            lable.setOpaque(true);
            if (isSelected) {
                lable.setBackground(table.getSelectionBackground());
            } else {
                lable.setBackground(table.getBackground());
            }

            if (column == 0) {
                lable.setText(t.getTaskName());
                return lable;
            } else if (column == 1) {
                lable.setText(Utility.formatDateShort(t.getDueDate()));
                return lable;
            } else if (column == 2) {
                JLabel priorityLabel = new JLabel(t.getPriority());
                priorityLabel.setOpaque(true);
                if (t.getPriority().equals(TaskFrame.priorityItems[0])) {
                    priorityLabel.setBackground(Color.YELLOW);
                    priorityLabel.setToolTipText(t.getPriority());
                } else if (t.getPriority().equals(TaskFrame.priorityItems[1])) {
                    priorityLabel.setBackground(Color.BLUE);
                    priorityLabel.setToolTipText(t.getPriority());
                } else if (t.getPriority().equals(TaskFrame.priorityItems[2])) {
                    priorityLabel.setBackground(Color.red);
                    priorityLabel.setToolTipText(t.getPriority());
                }

                return priorityLabel;
            } else if (column == 3) {
                lable.setText(t.getTag());
                return lable;


            } else if (column == 4) {
                return createBar(t.getProgress(), t.getProgress().toString());
            } else {
                return null;
            }

        }
/*
 *  create progressbar to show task progress
 */
        public JProgressBar createBar(int percentDone, String text) {
            JProgressBar progressBar = new JProgressBar(0, 100);

            progressBar.setStringPainted(true);
            progressBar.setValue(percentDone);
            progressBar.setString(text);

            return progressBar;
        }
    }
}
