/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import com.uip.todoapp.action.TaskController;
import com.uip.todoapp.domain.Tag;
import com.uip.todoapp.utility.Utility;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author 
 */
/*
 *   this class for create frame for add new tag
 */
public class TagFrame extends javax.swing.JDialog {

    private javax.swing.JButton closeButton;
    ResourceMap resourceMap;
    javax.swing.ActionMap actionMap;
    JTextField txtTagName;
    TodoForm mainForm;
    TaskController taskController;

    public TagFrame(java.awt.Frame parent, TodoForm mainForm) {
        super(parent);
        resourceMap = org.jdesktop.application.Application.getInstance(TodoApplication.class).getContext().getResourceMap(TagFrame.class);
        taskController = TaskController.getInstance();
        actionMap = org.jdesktop.application.Application.getInstance(com.uip.todoapp.TodoApplication.class).getContext().getActionMap(TagFrame.class, this);
        this.mainForm = mainForm;

        initComponents();

        getRootPane().setDefaultButton(closeButton);
    }
    /*
     *  this action for close this frame
     */

    @Action
    public void closeAboutBox() {
        dispose();
    }
    /*
     *  add new tag to app
     */

    @Action
    public void addTag() {
        Tag t = new Tag();
        t.setTagID(Utility.getTagMaxId());
        t.setTagName(txtTagName.getText());
        txtTagName.setText("");
        taskController.addTag(t);
        TagList.addTag(t);

    }
    /*
     *  initialize all component
     */

    private void initComponents() {
        JPanel addTaskPanel = new JPanel();
        addTaskPanel.setLayout(new BoxLayout(addTaskPanel, BoxLayout.Y_AXIS));


        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.add(new JLabel("Tag"));
        txtTagName = new JTextField("", 25);
        p1.add(txtTagName);
        addTaskPanel.add(p1);

        JSeparator sp = new JSeparator(JSeparator.HORIZONTAL);
        addTaskPanel.add(sp);

        JPanel p7 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addTaskButton = new JButton(actionMap.get("addTag"));
        addTaskButton.setName("addTaskButton");// NOI18N
        p7.add(addTaskButton);
        closeButton = new javax.swing.JButton();
        closeButton.setAction(actionMap.get("closeAboutBox")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N
        p7.add(closeButton);

        addTaskPanel.add(p7);

        getRootPane().setLayout(new BorderLayout());
        getRootPane().add(addTaskPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(resourceMap.getString("frameTitle")); // NOI18N
        setModal(true);
        setName("Add Task"); // NOI18N
        setResizable(false);

        pack();
    }
}
