package com.uip.todoapp;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Valjean Clark
 */


public class FilterTable {
    public static void main(String args[]) {
        Runnable runner = new Runnable() {
        public void run() {
            JFrame frame = new JFrame("TODOit");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new GridBagLayout());


            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new GridBagLayout());

            GridBagConstraints c = new GridBagConstraints();

            String dateLabels[] = {"All", "Today", "7 Days", "30 Days"};
            final JList dateList = new JList(dateLabels);
            dateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            dateList.setSelectedIndex(0);

            String priorityLabels[] = {"All", "High", "Medium", "Low"};
            final JList priorityList = new JList(priorityLabels);
            priorityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            priorityList.setSelectedIndex(0);

            final String tagLabels[] = {"All", "personal", "school", "work", "fantasy", "transportation"};
            final JList tagList = new JList(tagLabels);
            
            tagList.setSelectedIndex(0);

            JScrollPane dateSP = new JScrollPane(dateList);
            dateSP.setColumnHeaderView(new JLabel("DATE"));
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.weightx = 1.0;
            c.weighty = 0.2;
            c.insets = new Insets(5,5,5,5);
            leftPanel.add(dateSP, c);

            JScrollPane prioritySP = new JScrollPane(priorityList);
            prioritySP.setColumnHeaderView(new JLabel("PRIORITY"));
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 2;
            c.weightx = 1.0;
            c.weighty = 0.2;
            c.insets = new Insets(0,5,5,5);
            leftPanel.add(prioritySP, c);

            JScrollPane tagSP = new JScrollPane(tagList);
            tagSP.setColumnHeaderView(new JLabel("TAG"));
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 2;
            c.weightx = 1.0;
            c.weighty = 0.6;
            c.insets = new Insets(0,5,5,5);
            leftPanel.add(tagSP, c);

            JButton archiveButton = new JButton("Archive");
            JButton trashButton = new JButton("Trash");

            c.fill = GridBagConstraints.NONE;
            c.gridx = 0;
            c.gridy = 3;
            c.gridwidth = 1;
            c.weightx = 0.5;
            c.weighty = 0.1;
            c.insets = new Insets(0,5,5,5);
            leftPanel.add(archiveButton, c);

            c.fill = GridBagConstraints.NONE;
            c.gridx = 1;
            c.gridy = 3;
            c.gridwidth = 1;
            c.weightx = 0.5;
            c.weighty = 0.1;
            c.insets = new Insets(0,5,5,5);
            leftPanel.add(trashButton, c);

           Object rows[][] = {
             {"Wash car", "Medium", "personal", new Date(111, 1, 1), 0},
             {"Do laundry for the week", "High", "personal", new Date(111, 1, 25), 50},
             {"Buy a new bike", "Medium", "transportation", new Date(111, 2, 11), 0},
             {"Build a new engine", "Low", "transportation", new Date(111, 5, 30), 75},
             {"Setup meeting with Roger", "High", "work", new Date(111, 1, 15), 0},
             {"Find the pot of gold", "Low", "fantasy", new Date(111, 4, 2), 0},
           };
           Object columns[] = {"Task", "Priority", "Tag", "Date", "%"};
           TableModel model = new DefaultTableModel(rows, columns) {
               public Class getColumnClass(int column) {
                   Class returnValue;
                   if ((column >= 0) && (column < getColumnCount())) {
                     returnValue = getValueAt(0, column).getClass();
                   } else {
                     returnValue = Object.class;
                   }
                   return returnValue;
               }
           };
           JTable table = new JTable(model);

           final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
           table.setRowSorter(sorter);
           ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
           sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
           sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
           sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
           sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
           sorter.setSortKeys(sortKeys);
           JScrollPane taskPane = new JScrollPane(table);

            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.weightx = 1.0;
            c.weighty = 1.0;
            c.insets = new Insets(5,5,5,5);
            rightPanel.add(taskPane, c);


            dateList.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e)
                {
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
                    //System.out.println("30 Days from now : " + thirtyDays);

                    if(dateList.getSelectedValue()=="All") {
                        sorter.setRowFilter(null);
                    }
                    else if(dateList.getSelectedValue()=="Today") {
                        sorter.setRowFilter(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, today, 3));
                    }
                    else if(dateList.getSelectedValue()=="7 Days") {
                        sorter.setRowFilter(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, sevenDays, 3));
                    }
                    else if(dateList.getSelectedValue()=="30 Days") {
                        sorter.setRowFilter(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, thirtyDays, 3));
                    }

                    handleEvent(e);
                }
            });

            priorityList.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e)
                {
                    if(priorityList.getSelectedValue()=="All") {
                        sorter.setRowFilter(null);
                    }
                    else if(priorityList.getSelectedValue()=="High") {
                        sorter.setRowFilter(RowFilter.regexFilter("^High$", 1));
                    }
                    else if(priorityList.getSelectedValue()=="Medium") {
                        sorter.setRowFilter(RowFilter.regexFilter("^Medium$", 1));
                    }
                    else if(priorityList.getSelectedValue()=="Low") {
                        sorter.setRowFilter(RowFilter.regexFilter("^Low$", 1));
                    }

                    handleEvent(e);
                }
            });

            tagList.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e)
                {
                    Object objectArray[] = tagList.getSelectedValues();
                    String selectedTags[] = new String[objectArray.length];
                    for(int i = 0; i < objectArray.length; i++) {
                        selectedTags[i] = objectArray[i].toString();
                        //System.out.print(selectedTags[i]);
                    }

                    if( selectedTags[0].equals("All")) {
                        sorter.setRowFilter(null);
                    }
                    else {
                        ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);
                        for (int i = 0; i < selectedTags.length; i++) {
                            filters.add(RowFilter.regexFilter(selectedTags[i], 2));
                            //System.out.println(selectedTags[i]);
                        }
                        sorter.setRowFilter(RowFilter.andFilter(filters));
                    }

                    handleEvent(e);
                }
            });

           JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
            splitPane.setContinuousLayout(true);
            splitPane.setOneTouchExpandable(true);
            splitPane.setDividerLocation(200);
            splitPane.setAlignmentY(JPanel.TOP_ALIGNMENT);

           frame.add(splitPane);
           frame.setSize(800, 600);
           frame.setVisible(true);
         }
        };
        EventQueue.invokeLater(runner);
    }

    protected static void handleEvent(ListSelectionEvent e)
    {
        /*
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
        //System.out.println("30 Days from now : " + thirtyDays);

        ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();

        if(FilterTable.dateList.getSelectedValue()=="All"
         && priorityList.getSelectedValue()=="All"
         && tagList.getSelectedValue()=="All") {
            sorter.setRowFilter(null);
        }
        else {
            if(dateList.getSelectedValue()=="Today") {
                filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, today));
            }
            else if(dateList.getSelectedValue()=="7 Days") {
                filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, sevenDays));
            }
            else if(dateList.getSelectedValue()=="30 Days") {
                filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, thirtyDays));


            if(priorityList.getSelectedValue()=="High") {
                filters.add(RowFilter.regexFilter("^High$"));
            }
            else if(priorityList.getSelectedValue()=="Medium") {
                filters.add(RowFilter.regexFilter("^Medium$"));
            }
            else if(priorityList.getSelectedValue()=="Low") {
                filters.add(RowFilter.regexFilter("^Low$"));
            }

        }

        sorter.setRowFilter(RowFilter.andFilter(filters));
         *
         */
    }
}

