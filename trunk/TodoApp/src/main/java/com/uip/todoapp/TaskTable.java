/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import com.uip.todoapp.utility.Utility;
import java.awt.Color;
import java.awt.Component;

import java.util.Date;
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
            // Task t = (Task) value;
            JLabel lable = new JLabel();
            lable.setOpaque(true);
            if (isSelected) {
                lable.setBackground(table.getSelectionBackground());
            } else {
                lable.setBackground(table.getBackground());
            }

            if (column == 0) {
                Integer progress = (Integer) table.getValueAt(row, 4);
                String taskName = value.toString();
                if (progress.intValue() == 100) {
                    taskName = "<html><strike>" + taskName + "</strike></html>";
                }
                lable.setText(taskName );
                return lable;
            } else if (column == 1) {
                if (value != null) {
                    
                    lable.setText(Utility.formatDateShort((Date) value));
                }
                return lable;
            } else if (column == 2) {
                String pri = "";
                if (value != null) {
                    pri = value.toString();
                }

                JLabel priorityLabel = new JLabel(pri);
                priorityLabel.setOpaque(true);
                if (pri.equals(TaskFrame.priorityItems[0])) {
                    priorityLabel.setBackground(Color.YELLOW);
                    priorityLabel.setToolTipText(pri);
                } else if (pri.equals(TaskFrame.priorityItems[1])) {
                    priorityLabel.setBackground(Color.BLUE);
                    priorityLabel.setToolTipText(pri);
                } else if (pri.equals(TaskFrame.priorityItems[2])) {
                    priorityLabel.setBackground(Color.red);
                    priorityLabel.setToolTipText(pri);
                }

                return priorityLabel;
            } else if (column == 3) {
                String tag = "";
                if (value != null) {
                    tag = value.toString();
                }

                lable.setText(tag);
                return lable;


            } else if (column == 4) {
                Integer progress = 0;
                if (value != null) {
                    progress = (Integer) value;
                }

                return createBar(progress, progress.toString());
            } else {
                lable.setText("");
                return lable;
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
