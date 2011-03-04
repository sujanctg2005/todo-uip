/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import com.uip.todoapp.utility.Utility;
import com.uip.todoapp.action.TaskController;
import com.uip.todoapp.domain.Tag;
import com.uip.todoapp.domain.Task;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author sujan
 *
 *  This class for showing
 *  new window for new task and edit task
 */
public class TaskFrame extends javax.swing.JDialog {

    private javax.swing.JButton closeButton;
    ResourceMap resourceMap;
    javax.swing.ActionMap actionMap;
    JTextField txtTaskName;
    JTextField txtDate;
    static String[] priorityItems = {"Low", "Medium", "High"};
    JComboBox priorityCmb;
    JList tagList;
    JTextArea txtDec;
    
    private CustomSlider progressSlider;
    TaskController taskController;
    TodoForm mainForm;
    Task selTask = null;
    DefaultListModel model;
    TagList tagObj = TagList.getInstance();

    /*
     *  default constructor
     *  @param parent ,to  access parent frame
     *  @param  mainForm  to access main Todo frame
     */
    public TaskFrame(java.awt.Frame parent, TodoForm mainForm) {
        super(parent);
        resourceMap = org.jdesktop.application.Application.getInstance(TodoApplication.class).getContext().getResourceMap(TaskFrame.class);



        taskController = TaskController.getInstance();
        actionMap = org.jdesktop.application.Application.getInstance(com.uip.todoapp.TodoApplication.class).getContext().getActionMap(TaskFrame.class, this);
        this.mainForm = mainForm;
        System.out.println("sss");

        initComponents();

        getRootPane().setDefaultButton(closeButton);
    }
    /*
     *   to close this task window
     */

    @Action
    public void closeAboutBox() {
        dispose();
    }

    /*
     *  this action to add new task in list
     */
    @Action
    public void addTask() {

        Task t = new Task();

        t.setTaskName(txtTaskName.getText());
        t.setDueDate(Utility.parseDate(txtDate.getText()));

        t.setProgress(progressSlider.getValue());
        t.setDescription(txtDec.getText());
        t.setPriority(priorityCmb.getSelectedItem().toString());
        t.setTag(getAllTag(tagList.getSelectedValues()));


       
        t.setStatus(0);
        if (selTask == null) {
            t.setId(Utility.getTaskMaxId());
            taskController.addTask(t);
            mainForm.addTaskToTable(t);
            clearForm();
        } else {
            t.setId(selTask.getId());
            taskController.editTask(t);
            mainForm.updateTaskToTable(t);

        }




    }
    /*
     *  show selected task
     *  @param t is selected task
     * 
     */

    public void showTask(Task t) {
        try {

           
            selTask = t;
            txtTaskName.setText(t.getTaskName());
            txtDate.setText(Utility.formatDateShort(t.getDueDate()));
            System.out.println(t.getProgress());
            if (progressSlider != null && t != null) {
                try {
                    progressSlider.setValue(t.getProgress());
                } catch (Exception e) {
                    System.out.println("Error " + e.getMessage());
                }

            }

            txtDec.setText(t.getDescription());
            priorityCmb.setSelectedItem(t.getPriority().trim());

            //  System.out.println("dd"+t.toSting());
            if (t.getTag() != null) {
                String tag[] = t.getTag().split(",");
                int[] tagIndex = new int[tag.length];

                System.out.println(t.getTag() + " d " + tag.length);

                for (int i = 0; i < tag.length; i++) {
                    Tag ta = new Tag();
                    ta.setTagName(tag[i].trim());
                    System.out.println(ta.getTagName());
                    tagIndex[i] = model.indexOf(ta);

                }
                tagList.setSelectedIndices(tagIndex);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JFrame mainFrame = TodoApplication.getApplication().getMainFrame();
            JOptionPane.showMessageDialog(mainFrame, "Internal Error 1:\n Details :" + e.getMessage(),
                    "Error", JOptionPane.WARNING_MESSAGE);
        }


    }
    /*
     *  to clear the form
     *  for new task
     */

    public void clearForm() {
        selTask = null;
        txtDate.setText("");
        txtDec.setText("");
        txtTaskName.setText("");
        progressSlider.setValue(0);
        tagList.setSelectedIndex(0);
        priorityCmb.setSelectedIndex(0);

    }
    /*
     *     convert list of tag to string using ',' comma separator
     *     @param data , list of tag object
     *     @return tags string
     */

    public String getAllTag(Object [] data) {
        String allTag = "";
        int size = data.length;
        int i = 0;
        for (Object o : data) {
            
            allTag += o.toString();
            i++;
            if (i < size) {
                allTag += ", ";
            }

        }

        return allTag;
    }

    /*
     *  Add Tag list to Jlist model
     *  @param data , list of tag object
     */
    public void loadTag(List<Tag> data) {
        for (Tag t : data) {
            model.addElement(t);
        }
    }
    /*
     *  initiate all components for
     *   new task window
     */

    public void initComponents() {
        JPanel addTaskPanel = new JPanel();
        addTaskPanel.setLayout(new BoxLayout(addTaskPanel, BoxLayout.Y_AXIS));


        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.add(new JLabel("Task Name"));
        txtTaskName = new JTextField("", 25);
        p1.add(txtTaskName);


        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
        p2.add(new JLabel("Priority"));
        priorityCmb = new JComboBox(priorityItems);
        p2.add(priorityCmb);

        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));




        model = tagObj.getDefaultListModel();


        tagList = tagObj.getTagList();
        JScrollPane sp1 = new JScrollPane(tagList);
        sp1.setColumnHeaderView(new JLabel("Tag"));
        p3.add(sp1);

        JPanel p4 = new JPanel();
        p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
        txtDate = new JTextField("", 8);
        p4.add(new JLabel("Date(yyyy-MM-dd)"));
        p4.add(txtDate);

        JPanel p5 = new JPanel();
        p5.setLayout(new BoxLayout(p5, BoxLayout.Y_AXIS));
        p5.add(new JLabel("Note"));
        txtDec = new JTextArea(4, 5);
        JScrollPane sp2 = new JScrollPane(txtDec);
        p5.add(sp2);


        JPanel p6 = new JPanel();
        p6.setLayout(new BoxLayout(p6, BoxLayout.Y_AXIS));
        progressSlider = new CustomSlider(0, 100, 0);
        progressSlider.setMajorTickSpacing(10);
        progressSlider.setMinorTickSpacing(2);
        progressSlider.setPaintTicks(true);
        progressSlider.setPaintTrack(true);
        p6.add(new JLabel("Progress"));
        p6.add(progressSlider);

        JPanel p7 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addTaskButton = new JButton(actionMap.get("addTask"));
        addTaskButton.setName("addTaskButton");// NOI18N
        p7.add(addTaskButton);
        closeButton = new javax.swing.JButton();
        closeButton.setAction(actionMap.get("closeAboutBox")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N
        p7.add(closeButton);

        addTaskPanel.add(p1);
        addTaskPanel.add(p3);
        addTaskPanel.add(p4);
        addTaskPanel.add(p5);
        addTaskPanel.add(p6);
        addTaskPanel.add(p2);
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
