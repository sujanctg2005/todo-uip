/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import com.uip.todoapp.action.TaskController;
import com.uip.todoapp.domain.Tag;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author sujan
 */

/*
 *  this class is to share common model
 *   for tag list and add new task tag list
 *  
 */
public class TagList {

    private static DefaultListModel model = new DefaultListModel();
    TaskController taskController;
    static TagList tagList;

    private TagList() {

        taskController = TaskController.getInstance();
        loadTag(taskController.getAllTag());
    }
    /*
     *   singleton pattern to instantiate new object
     */

    public static TagList getInstance() {
        if (tagList == null) {
            tagList = new TagList();
        }
        return tagList;
    }

    /*
     *  add tag to List model
     *  @param t new tag
     */
    public static void addTag(Tag t) {
        if (model.indexOf(t) == -1) {
            model.add(1, t);
        }
    }
    /*
     *  create new JList
     * @return instance of JList
     */

    public JList getTagList() {

        return new JList(model);
    }
    /*
     *  this method for get default list model
     * @return instance of DefaultListModel
     */

    public DefaultListModel getDefaultListModel() {
        return model;
    }

    public void loadTag(List<Tag> data) {
        for (Tag t : data) {
            model.addElement(t);
        }
    }
}
