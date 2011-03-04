/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import com.uip.todoapp.domain.Task;
import com.uip.todoapp.utility.Utility;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DateFormat;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author sujan
 */
public class TaskListCellRenderer extends JPanel
        implements ListCellRenderer {

    /** Normal Background Color */
    static final Color BACKGROUND_NORM = new Color(255, 250, 230);
    /** Color for selected items */
    static final Color BACKGROUND_SELECTED = new Color(255, 225, 110);
// Components used
    JLabel lblTaskName = new JLabel();
    JLabel lblPriority = new JLabel();
    JLabel lblNote = new JLabel();
    JLabel lblTag = new JLabel();
    JLabel lbldueDate = new JLabel();
    JProgressBar progressBar = new JProgressBar(0, 100);
    Font prioFont = null;
    Font prioFontHigh = null;
    ResourceMap resourceMap;

    /**
     * Default constructor
     */
    public TaskListCellRenderer() {
        // Get ResourceBundle for current Locale

        resourceMap = org.jdesktop.application.Application.getInstance(TodoApplication.class).getContext().getResourceMap(TaskListTable.class);

        initComponents(); // create all components
        initLayout(); // place them in layout
    }

    /**
     * Creates components
     */
    private void initComponents() {



// big bold font for task name


// slightly larger font for high priority tasks
        prioFont = lblPriority.getFont();
        prioFontHigh = prioFont.deriveFont(Font.BOLD, prioFont.getSize() + 2);

    }

    /**
     * Layout components
     */
    private void initLayout() {
        // this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        GridLayout gridLayout = new GridLayout(2, 2);
        gridLayout.setHgap(10);
        this.setLayout(gridLayout);
        this.add(lblTaskName);
        //this.add(Box.createHorizontalStrut(10));

        //this.add(Box.createHorizontalStrut(10));
        this.add(lbldueDate);
        this.add(this.progressBar);



    } // END: initLayout()

    /**
     * Called by the JList to get a initialized Renderer
     * @param list The JList drawing the Task
     * @param value Task to be drawn
     * @param index index of Task
     * @param isSelected true if selected
     * @param cellHasFocus true if focused
     * @return a Renderer suitable for rendering the Task
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
// cast value to a TaskModel so we can work with it
        Task task = (Task) value;

        this.lblTaskName.setText(task.getTaskName());
        this.lbldueDate.setText(Utility.formatDateShort(task.getDueDate()));
        lbldueDate.setForeground(Color.PINK);
        this.progressBar.setValue(task.getProgress());
        progressBar.setStringPainted(true);
        progressBar.setString(task.getProgress().toString());



        // Alter appearance for selected status
        if (isSelected) {
// set lowered borders for selected task
            setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            setBackground(BACKGROUND_SELECTED);
        } else {
// and raised for others
            setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            setBackground(BACKGROUND_NORM);
        }

        if (task.getPriority().equals(TaskFrame.priorityItems[0])) {
            lblTaskName.setForeground(Color.YELLOW);
            lblTaskName.setToolTipText(task.getPriority());
        } else if (task.getPriority().equals(TaskFrame.priorityItems[1])) {
            lblTaskName.setForeground(Color.BLUE);
            lblTaskName.setToolTipText(task.getPriority());
        } else if (task.getPriority().equals(TaskFrame.priorityItems[2])) {
            lblTaskName.setForeground(Color.red);
            lblTaskName.setToolTipText(task.getPriority());
        }else  {
            lblTaskName.setForeground(Color.BLACK);
            lblTaskName.setToolTipText(task.getPriority());
        }



        return this;
    } // END: getListCellRendererComponent
} // END: Class TaskListCellRenderer

