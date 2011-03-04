/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import com.uip.todoapp.action.TaskController;

import com.uip.todoapp.domain.Tag;
import com.uip.todoapp.domain.Task;

import com.uip.todoapp.utility.Language;
import com.uip.todoapp.utility.Utility;
import java.awt.*;
import java.awt.event.*;

import java.util.*;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.application.Action;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.ResourceManager;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;

/**
 *
 * @author sujan
 */

/* This is the main frame of todo app
 *
 *
 */
public class TodoForm extends FrameView {

    private JMenuBar menubar;
    private JToolBar toolbar;
    private JComponent status;
    ResourceMap resourceMap;
    javax.swing.ActionMap actionMap;
    Border loweredBorder = new CompoundBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED),
            new EmptyBorder(5, 5, 5, 5));
    EmptyBorder border5 = new EmptyBorder(5, 5, 5, 5);
    EmptyBorder border10 = new EmptyBorder(10, 10, 10, 10);
    JList tagList;
    private TaskFrame taskFrame;
    private TaskListTable taskListTable;
    JTextField txtTaskName;
    JTextField txtDate;
    DefaultListModel model;
    TagList tagObj = TagList.getInstance();
    TagFrame tagFrame;
    TaskListModel taskListModel;
    JList taskJlist;   // view task as list
    /**
     * Suffix applied to the key used in resource file
     * lookups for an image.
     */
    public static final String imageSuffix = "Image";
    /**
     * Suffix applied to the key used in resource file
     * lookups for a label.
     */
    public static final String labelSuffix = "Label";
    /**
     * Suffix applied to the key used in resource file
     * lookups for an action.
     */
    public static final String actionSuffix = "Action";
    /**
     * Suffix applied to the key used in resource file
     * lookups for tooltip text.
     */
    public static final String tipSuffix = "Tooltip";
    public static final String openAction = "open";
    public static final String newAction = "new";
    public static final String saveAction = "save";
    public static final String exitAction = "exit";
    // Premade convenience dimensions, for use wherever you need 'em.
    public static Dimension HGAP2 = new Dimension(2, 1);
    public static Dimension VGAP2 = new Dimension(1, 2);
    public static Dimension HGAP5 = new Dimension(5, 1);
    public static Dimension VGAP5 = new Dimension(1, 5);
    public static Dimension HGAP10 = new Dimension(10, 1);
    public static Dimension VGAP10 = new Dimension(1, 10);
    public static Dimension HGAP15 = new Dimension(15, 1);
    public static Dimension VGAP15 = new Dimension(1, 15);
    public static Dimension HGAP20 = new Dimension(20, 1);
    public static Dimension VGAP20 = new Dimension(1, 20);
    public static Dimension HGAP25 = new Dimension(25, 1);
    public static Dimension VGAP25 = new Dimension(1, 25);
    public static Dimension HGAP30 = new Dimension(30, 1);
    public static Dimension VGAP30 = new Dimension(1, 30);
    TaskController taskController;
    /*
     *  constructor
     *  all application resource is initialized
     */

    public TodoForm(SingleFrameApplication app) {
        super(app);
        // resourceMap = getResourceMap();


        ResourceManager mgr = org.jdesktop.application.Application.getInstance(TodoApplication.class).getContext().getResourceManager();



        resourceMap = org.jdesktop.application.Application.getInstance(TodoApplication.class).getContext().getResourceMap(TodoForm.class);
        taskController = TaskController.getInstance();
        actionMap = org.jdesktop.application.Application.getInstance(com.uip.todoapp.TodoApplication.class).getContext().getActionMap(TodoForm.class, this);

        // Force SwingSet to come up in the Cross Platform L&F
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            // If you want the System L&F instead, comment out the above line and
            // uncomment the following:
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exc) {
            System.err.println("Error loading L&F: " + exc);
        }
        initComponents();




    }

    /**
     *  initialise all UI component
     */
    public void initComponents() {
        actionMap = org.jdesktop.application.Application.getInstance(com.uip.todoapp.TodoApplication.class).getContext().getActionMap(TodoForm.class, this);

        setComponent(todoMainPanel());
        setMenuBar(createMenubar());
        setStatusBar(new javax.swing.JPanel());




    }
    TitledBorder titledBorder1;
    TitledBorder titledBorder2;
    TitledBorder titledBorder3;
    TitledBorder titledBorder4;
    JLabel taskLbl;
    JTextField searchTextField;
    FancyButton todaysBtn;
    FancyButton weeklyBtn;
    FancyButton moreBtn;
    JPanel taskCardPanel = new JPanel(new CardLayout());
    JPanel tableCard = new JPanel();
    JPanel listCard = new JPanel();
    String tableCardKey = "tableCard";
    String listCardKey = "listCard";
    String currentTaskCard = "tableCard";

    /**
     *
     * it will add all component to main panel
     * @return the instance of main panel
     */
    public JPanel todoMainPanel() {

        JPanel mpanel = new JPanel();
        mpanel.setBorder(BorderFactory.createEtchedBorder());
        mpanel.setLayout(new BorderLayout());


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        titledBorder1 = new TitledBorder(null, resourceMap.getString("leftPanelBorderTile"),
                TitledBorder.LEFT, TitledBorder.TOP);
        leftPanel.setBorder(new CompoundBorder(titledBorder1, border5));
        leftPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        titledBorder2 = new TitledBorder(null, resourceMap.getString("rightPanelBorderTile"),
                TitledBorder.LEFT, TitledBorder.TOP);
        rightPanel.setBorder(new CompoundBorder(titledBorder2, border5));
        rightPanel.setAlignmentY(JPanel.TOP_ALIGNMENT);


        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchTextField =
                new JTextField(resourceMap.getString("searchTextBox.Title"), 15);
        searchPanel.add(searchTextField);
        JPanel searchButtonPanel = new JPanel();

        todaysBtn = new FancyButton("");
        todaysBtn.setAction(actionMap.get("todaysTask"));
        weeklyBtn = new FancyButton("");
        weeklyBtn.setAction(actionMap.get("weeklyTask"));
        moreBtn = new FancyButton("");
        moreBtn.setAction(actionMap.get("moreTask"));

        searchButtonPanel.add(todaysBtn);
        searchButtonPanel.add(weeklyBtn);
        searchButtonPanel.add(moreBtn);
        searchPanel.add(searchButtonPanel);

        leftPanel.add(searchPanel, BorderLayout.NORTH);




        model = tagObj.getDefaultListModel();
        tagList = tagObj.getTagList();

        final JPopupMenu contextMenu = new JPopupMenu("Edit");
        contextMenu.add(makeMenuItem("addTag"));
        contextMenu.add(makeMenuItem("deleteTag"));
        tagList.setComponentPopupMenu(contextMenu);


        tagList.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                try {
                    Object objectArray[] = tagList.getSelectedValues();
                    String selectedTags[] = new String[objectArray.length];
                    for (int i = 0; i < objectArray.length; i++) {
                        selectedTags[i] = objectArray[i].toString();
                        //System.out.print(selectedTags[i]);
                    }

                    if (selectedTags[0].equals("All")) {
                        taskListTable.getSorter().setRowFilter(null);
                    } else {
                        ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
                        for (int i = 0; i < selectedTags.length; i++) {
                            filters.add(RowFilter.regexFilter(selectedTags[i], 3));
                            //System.out.println(selectedTags[i]);
                        }
                        taskListTable.getSorter().setRowFilter(RowFilter.andFilter(filters));
                    }
                } catch (Exception es) {
                    System.out.println(es);

                }




            }
        });



        JScrollPane sp1 = new JScrollPane(tagList);
        sp1.setColumnHeaderView(new JLabel("Tag"));

        leftPanel.add(sp1, BorderLayout.CENTER);


        JPanel archivePanel = new JPanel();
        archivePanel.setLayout(new FlowLayout());
        archivePanel.add(Box.createRigidArea(HGAP5));
        archivePanel.add(new JButton("Archive"));
        archivePanel.add(Box.createRigidArea(HGAP5));
        archivePanel.add(new JButton("Trash"));
        leftPanel.add(archivePanel, BorderLayout.SOUTH);


        JPanel rightNorthPanel = new JPanel();
        rightNorthPanel.setLayout(new BoxLayout(rightNorthPanel, BoxLayout.Y_AXIS));


        JPanel settingPanel = new JPanel();
        settingPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        Object[] items = {"Select Language", "English", "Swedish"};
        JComboBox languaeCmb = new JComboBox(items);
        languaeCmb.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String language = (String) cb.getSelectedItem();
                if (language.equals("English")) {
                    changeLanguage(Locale.getDefault());

                } else if (language.equals("Swedish")) {
                    changeLanguage(new Locale("sv"));

                } else if (language.equals("French")) {
                    changeLanguage(new Locale("fn"));

                }
            }
        });


        JSeparator settingPanelSeparator = new JSeparator(JSeparator.HORIZONTAL);
        settingPanel.add(new JButton("Setting"));
        settingPanel.add(languaeCmb);
        settingPanel.add(settingPanelSeparator);


        JPanel addTaskPanel = new JPanel();
        addTaskPanel.setLayout(new BoxLayout(addTaskPanel, BoxLayout.X_AXIS));

        titledBorder4 = new TitledBorder("New Task");
        addTaskPanel.setBorder(titledBorder4);

        taskLbl = new JLabel("Task");
        addTaskPanel.add(taskLbl);
        addTaskPanel.add(Box.createHorizontalStrut(10));
        txtTaskName = new JTextField("", 35);
        addTaskPanel.add(txtTaskName);

        addTaskPanel.add(Box.createHorizontalStrut(10));
        addTaskPanel.add(new JLabel("Date(yyyy-MM-dd)"));
        txtDate = new JTextField("", 8);
        addTaskPanel.add(Box.createHorizontalStrut(10));
        addTaskPanel.add(txtDate);
        addTaskPanel.add(Box.createHorizontalStrut(10));

        JButton addTaskButton = new JButton("Add");
        addTaskButton.setAction(actionMap.get("addTask")); // NOI18N
        addTaskButton.setName("addTaskButton");// NOI18N
        addTaskPanel.add(addTaskButton);

        //
        rightNorthPanel.add(settingPanel);
        rightNorthPanel.add(addTaskPanel);

        rightPanel.add(rightNorthPanel, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel();
        titledBorder3 = new TitledBorder("Action");
        controlPanel.setBorder(titledBorder3);
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JButton("Complete"));
        controlPanel.add(new JButton("Archive"));
        controlPanel.add(new JButton("Delete"));

        rightPanel.add(controlPanel, BorderLayout.SOUTH);




        tableCard.setLayout(new BoxLayout(tableCard, BoxLayout.Y_AXIS));
        tableCard.add(taskListTable());

        // demo task list



        taskListModel = new TaskListModel(taskListTable.getTaskListTablemodel());

        taskJlist = new JList(taskListModel);
        taskJlist.setCellRenderer(new TaskListCellRenderer());
        JScrollPane scrlPane = new JScrollPane(taskJlist);
        scrlPane.setColumnHeaderView(new JLabel("Task"));


        //end

        listCard.setLayout(new BoxLayout(listCard, BoxLayout.Y_AXIS));
        listCard.add(scrlPane);

        taskCardPanel.add(tableCard, tableCardKey);
        taskCardPanel.add(listCard, listCardKey);
        rightPanel.add(taskCardPanel, BorderLayout.CENTER);




        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);
        splitPane.setAlignmentY(JPanel.TOP_ALIGNMENT);

        mpanel.add("North", createToolbar());
        mpanel.add("Center", splitPane);
        mpanel.add("South", createStatusbar());
        return mpanel;

    }
    /*
     *  change different language
     *  @param locale1 , Local instance for language
     */

    public void changeLanguage(Locale locale1) {



        ResourceBundle resources1 = Language.getResourceBundle(locale1);
        System.out.println(resources1.getString("TodoForm.leftPanelBorderTile") + "sss");
        titledBorder2.setTitle(resources1.getString("TodoForm.rightPanelBorderTile"));
        titledBorder1.setTitle(resources1.getString("TodoForm.leftPanelBorderTile"));
        titledBorder3.setTitle(resources1.getString("TodoForm.southPanelBorderTile"));
        titledBorder4.setTitle(resources1.getString("TodoForm.addTaskPanelBorderTile"));

        taskLbl.setText(resources1.getString("TodoForm.taskLbl"));
        searchTextField.setText(resources1.getString("TodoForm.txtSearch"));
        JFrame mainFrame = TodoApplication.getApplication().getMainFrame();





        mainFrame.validate();
        mainFrame.repaint();
    }

    /**
     * To shutdown when run as an application.  This is a
     * fairly lame implementation.   A more self-respecting
     * implementation would at least check to see if a save
     * was needed.
     */
    protected static final class AppCloser extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    private JMenuItem makeMenuItem(String label) {
        JMenuItem item = new JMenuItem(actionMap.get(label));

        return item;
    }

    /**
     *  this will crate a demo JTable for showing task list
     *  @return the instance of JScrollPane with task list
     */
    public JComponent taskListTable() {
        taskListTable = new TaskListTable(this);
        return taskListTable.getTasklistTable();
    }

    public void addTaskToTable(Task t) {
        taskListTable.addTask(t);
    }

    public void updateTaskToTable(Task t) {
        taskListTable.updateTask(t);
    }

    /**
     * To crate a panel with Horizontal orientation layout
     * @param threeD effect of border panel
     * @return the  instance of JPanel
     */
    public JPanel createHorizontalPanel(boolean threeD) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setAlignmentY(JPanel.TOP_ALIGNMENT);
        p.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        if (threeD) {
            p.setBorder(loweredBorder);
        }
        return p;
    }

    /**
     *To crate a panel with Vertical orientation layout
     *  @param threeD effect of border panel
     * @return the  instance of JPanel
     */
    public JPanel createVerticalPanel(boolean threeD) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setAlignmentY(JPanel.TOP_ALIGNMENT);
        p.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        if (threeD) {
            p.setBorder(loweredBorder);
        }
        return p;
    }

    /**
     * Create a status bar
     * @return status bar component
     */
    protected Component createStatusbar() {
        // need to do something reasonable here
        status = new StatusBar();
        return status;
    }

    /**
     * Create the toolbar.  By default this reads the
     * resource file for the definition of the toolbar.
     * @return status bar component
     */
    private Component createToolbar() {
        toolbar = new JToolBar();
        String[] toolKeys = tokenize(resourceMap.getString("toolbar"));
        for (int i = 0; i < toolKeys.length; i++) {
            if (toolKeys[i].equals("-")) {
                toolbar.add(Box.createHorizontalStrut(5));
            } else {
                toolbar.add(createTool(toolKeys[i]));
            }
        }
        toolbar.add(Box.createHorizontalGlue());
        return toolbar;
    }

    /**
     * Hook through which every toolbar item is created.
     */
    protected Component createTool(String key) {
        return createToolbarButton(key);
    }

    /**
     * Create a button to go inside of the toolbar.  By default this
     * will load an image resource.  The image filename is relative to
     * the classpath (including the '.' directory if its a part of the
     * classpath), and may either be in a JAR file or a separate file.
     *
     * @param key The key in the resource file to serve as the basis
     *  of lookups.
     */
    protected JButton createToolbarButton(String key) {

        JButton b = new JButton(actionMap.get(key)) {

            public float getAlignmentY() {
                return 0.5f;
            }
        };
        b.setRequestFocusEnabled(false);
        b.setMargin(new Insets(1, 1, 1, 1));



        String tip = resourceMap.getString(key + tipSuffix);
        if (tip != null) {
            b.setToolTipText(tip);
        }

        return b;
    }

    protected JMenuBar createMenubar() {
        JMenuItem mi;
        JMenuBar mb = new JMenuBar();



        String[] menuKeys = tokenize(resourceMap.getString("menubar"));
        for (int i = 0; i < menuKeys.length; i++) {

            System.out.println(menuKeys[i]);

            JMenu m = createMenu(menuKeys[i]);
            if (m != null) {
                mb.add(m);
            }
        }
        this.menubar = mb;
        return mb;
    }

    protected JMenu createMenu(String key) {
        String[] itemKeys = tokenize(resourceMap.getString(key));
        JMenu menu = new JMenu(resourceMap.getString(key + "Label"));
        for (int i = 0; i < itemKeys.length; i++) {
            if (itemKeys[i].equals("-")) {
                menu.addSeparator();
            } else {
                JMenuItem mi = createMenuItem(itemKeys[i]);
                menu.add(mi);
            }
        }
        return menu;
    }

    protected JMenuItem createMenuItem(String cmd) {

        JMenuItem mi = new JMenuItem();
        mi.setAction(actionMap.get(cmd));

        return mi;


    }

    protected String[] tokenize(String input) {
        Vector v = new Vector();
        StringTokenizer t = new StringTokenizer(input);
        String cmd[];

        while (t.hasMoreTokens()) {
            v.addElement(t.nextToken());
        }
        cmd = new String[v.size()];
        for (int i = 0; i < cmd.length; i++) {
            cmd[i] = (String) v.elementAt(i);
        }

        return cmd;
    }

    public class FancyButton extends JButton {

        public void setText(String text) {
            String htmlText = "<html><p><u><font color=\"red\" "
                    + ">" + text + "</font></u> </p>"
                    + "</html>";
            super.setText(htmlText);

        }

        public FancyButton(String text) {
            setSize(WIDTH, WIDTH);


            setText(text);
            setMargin(new Insets(0, 0, 0, 0));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            // super(icon);
            setFocusPainted(false);
            setRolloverEnabled(true);
            //setRolloverIcon(rollover);
            // setPressedIcon(pressed);
            setBorderPainted(false);
            setContentAreaFilled(false);
        }
    }

    /**
     * FIXME - I'm not very useful yet
     */
    class StatusBar extends JComponent {

        public StatusBar() {
            super();
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        }

        public void paint(Graphics g) {
            super.paint(g);
        }
    }

    /*
     *  all actions here
     *
     */
    @Action
    public void addTask() {
        try {
            Task t = new Task();

            t.setTaskName(txtTaskName.getText());
            t.setDueDate(Utility.parseDate(txtDate.getText()));
            t.setId(Utility.getTaskMaxId());
            t.setProgress(0);
            t.setDescription("");
            t.setPriority("");
            t.setTag("");
            t.setStatus(0);

            System.out.println("toform :" + t.toSting());
            taskController.addTask(t);
            txtTaskName.setText("");
            txtDate.setText("");
            addTaskToTable(t);

        } catch (Exception e) {

            JFrame mainFrame = TodoApplication.getApplication().getMainFrame();
            JOptionPane.showMessageDialog(mainFrame, "Internal Error :\n Details :" + e.getMessage(),
                    "Error", JOptionPane.WARNING_MESSAGE);
        }


    }

    @Action
    public void addTag() {
        if (tagFrame == null) {
            JFrame mainFrame = TodoApplication.getApplication().getMainFrame();
            tagFrame = new TagFrame(mainFrame, this);
            tagFrame.setLocationRelativeTo(tagFrame);
        }
        TodoApplication.getApplication().show(tagFrame);
    }

    @Action
    public void deleteTag() {

        int index = tagList.getSelectedIndex();
        Tag t = (Tag) model.getElementAt(index);

        if (t.getTagName().equals("All")) // all tag to show all type of task
        {
            return;
        }

        if (!taskController.isTagUsed(t)) {
            taskController.deleteTag(t);
            model.remove(index);
        } else {
            JFrame mainFrame = TodoApplication.getApplication().getMainFrame();
            JOptionPane.showMessageDialog(mainFrame, "Tag is used",
                    "Error", JOptionPane.WARNING_MESSAGE);

        }

    }

    @Action
    public void save() {
        //  taskController.saveAllTask();
    }

    @Action
    public void open() {
        System.out.println("Task added");
    }
    /*
     *   this action to show new task window
     */

    @Action
    public void newTask() {

        getTaskFrame();
        taskFrame.clearForm();
        TodoApplication.getApplication().show(taskFrame);
    }
    /*
     * This method  to get new task window
     * @return instance of TaskFrame
     */

    public TaskFrame getTaskFrame() {
        if (taskFrame == null) {
            JFrame mainFrame = TodoApplication.getApplication().getMainFrame();
            taskFrame = new TaskFrame(mainFrame, this);
            taskFrame.setLocationRelativeTo(mainFrame);
        }
        return taskFrame;
    }
    /*
     *  this method for show task frame
     */

    public void showTaskForm() {
        getTaskFrame();
        TodoApplication.getApplication().show(taskFrame);

    }

    @Action
    public void exit() {
        System.exit(0);
    }

    @Action
    public void archiveList() {
        System.out.println("Task added");
    }

    @Action
    public void archive() {
        System.out.println("Task added");
    }

    @Action
    public void trash() {
        System.out.println("Task added");
    }

    @Action
    public void delete() {
    }
    /*
     *  show all todays tasks
     */

    @Action
    public void todaysTask() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date today = cal.getTime();
        //System.out.println("Today : " + today);

        cal.add(Calendar.DATE, 7);
        Date sevenDays = cal.getTime();
        //System.out.println("7 Days from now : " + sevenDays);

        cal.add(Calendar.DATE, 23);
        Date thirtyDays = cal.getTime();
        System.out.println("30 Days from now : " + thirtyDays);


        taskListTable.getSorter().setRowFilter(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, today, 1));

    }

    /*
     *  show all monthly tasks
     */
    @Action
    public void moreTask() {
        taskListTable.getSorter().setRowFilter(null);
    }
    /*
     *  show all weekly tasks
     */

    @Action
    public void weeklyTask() {
        System.out.println("Task weekly");
    }

    @Action
    public void changeTaskView() {


        if (currentTaskCard.equals(tableCardKey)) {
            currentTaskCard = listCardKey;
        } else {
            currentTaskCard = tableCardKey;

        }
        CardLayout cl = (CardLayout) (taskCardPanel.getLayout());
        cl.show(taskCardPanel, currentTaskCard);

    }
}
