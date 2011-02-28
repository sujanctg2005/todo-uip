/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp.service.impl;

import com.uip.todoapp.domain.Tag;
import com.uip.todoapp.domain.Task;
import com.uip.todoapp.service.ToDoService;
import com.uip.todoapp.utility.Utility;
import com.uip.todoapp.utility.XMLParser;

import java.util.ArrayList;
import java.util.List;


import org.jdom.Document;
import org.jdom.Element;




/**
 *
 * @author sujan
 */
public class ToDoServiceImpl implements ToDoService {

    XMLParser parser;
    Document doc;
    String rootElement = "todo";
    String taskRootName = "tasklist";
    String tagRootName = "taglist";

    public ToDoServiceImpl(XMLParser parser) {

        this.parser = parser;
        doc = parser.getDoc();
    }
    /*
     *  initialize document object 
     */

    public void initDoc() {

        this.doc = parser.initDoc();
    }
    /*
     *  save changes data to xml
     */

    public void saveAllData() {
        parser.saveAllData();
    }
    /*
     *  add new task to xml
     *  @param t to add in xml
     */

    public void addTask(Task t) {

        initDoc();

        //Element category = new Element("Category");
        Element id = new Element("id");
        Element taskName = new Element("taskName");
        Element priority = new Element("priority");
        Element dueDate = new Element("dueDate");
        Element description = new Element("description");
        Element tag = new Element("tag");
        Element progress = new Element("progress");
        Element status = new Element("status");

        Element task = new Element("Task");

        task.addContent(id.addContent(t.getId().toString()));
        task.addContent(taskName.addContent(t.getTaskName()));
        task.addContent(priority.addContent(t.getPriority()));
        task.addContent(dueDate.addContent(Utility.formatDateShort(t.getDueDate())));
        task.addContent(description.addContent(t.getDescription()));
        task.addContent(tag.addContent(t.getTag()));
        task.addContent(progress.addContent(t.getProgress().toString()));
        task.addContent(status.addContent(t.getStatus().toString()));

        System.out.println(" add xml :" + t.toSting());
        System.out.println(task.toString());
        doc.getRootElement().getChild(taskRootName).addContent(task);



        saveAllData();
    }
    /*
     *   This method to add new tag to xml
     *  @param t is new Tag
     */

    public void addTag(Tag t) {
        initDoc();
        Element tagId = new Element("tagID");
        Element tagName = new Element("tagName");

        Element tag = new Element("Tag");

        tag.addContent(tagId.addContent(t.getTagID().toString()));
        tag.addContent(tagName.addContent(t.getTagName()));

        doc.getRootElement().getChild(tagRootName).addContent(tag);
        saveAllData();

    }

    public void addTasks(List<Task> tasks) {
        for (Task t : tasks) {
            addTask(t);
        }
    }

    public void addTags(List<Tag> tag) {
        for (Tag t : tag) {
            addTag(t);
        }
    }
    /*
     *  edit selected task in xml
     *  @param t to edit in xml
     *
     */

    public void editTask(Task t) {

        initDoc();

        Element el = doc.getRootElement().getChild(taskRootName);
        Element editEl = null;
        for (Object obj : el.getChildren()) {
            Element e = (Element) obj;
            if (Integer.parseInt(e.getChild("id").getText()) == t.getId()) {
                editEl = e;
            }

        }


        System.out.println("dd " + t.toSting());
        editEl.getChild("id").removeContent();
        editEl.getChild("id").addContent(t.getId().toString());

        editEl.getChild("taskName").removeContent();
        editEl.getChild("taskName").addContent(t.getTaskName());

        editEl.getChild("priority").removeContent();
        editEl.getChild("priority").addContent(t.getPriority());

        editEl.getChild("tag").removeContent();
        editEl.getChild("tag").addContent(t.getTag());

        editEl.getChild("dueDate").removeContent();
        editEl.getChild("dueDate").addContent(Utility.formatDateShort(t.getDueDate()));

        editEl.getChild("description").removeContent();
        editEl.getChild("description").addContent(t.getDescription());

        editEl.getChild("progress").removeContent();
        editEl.getChild("progress").addContent(t.getProgress().toString());

        editEl.getChild("status").removeContent();
        editEl.getChild("status").addContent(t.getStatus().toString());

        saveAllData();
    }
    /*
     *  This method to check whether this tag is used
     *  before delete tag
     */

    public boolean isTagUsed(Tag tag) {
        initDoc();
        Element el = doc.getRootElement().getChild(taskRootName);
        Element removeEl = null;
        for (Object obj : el.getChildren()) {
            Element e = (Element) obj;
            System.out.println("tt " + e.getChild("tag").getText());

            if (e.getChild("tag").getText().indexOf(tag.getTagName()) != -1) {
                return true;
            }

        }
        return false;
    }
    /*
     *   delete task from xml
     *   @param t to delete from xml
     */

    public void deleteTag(Tag t) {
        initDoc();
        Element el = doc.getRootElement().getChild(tagRootName);
        Element removeEl = null;
        for (Object obj : el.getChildren()) {
            Element e = (Element) obj;
            if (Integer.parseInt(e.getChild("tagID").getText()) == t.getTagID()) {
                removeEl = e;
            }

        }
        el.removeContent(removeEl);
        saveAllData();
    }

    public void deleteTask(Task t) {
        initDoc();
        Element el = doc.getRootElement().getChild(taskRootName);
        Element removeEl = null;
        System.out.println("list size :" + el.getChildren().size());
        for (Object obj : el.getChildren()) {
            Element e = (Element) obj;
            if (Integer.parseInt(e.getChild("id").getText()) == t.getId()) {
                removeEl = e;
            }

        }
        el.removeContent(removeEl);
        saveAllData();
    }

    public Task getTask(Task task) {
        initDoc();
        Element el = doc.getRootElement().getChild(taskRootName);

        for (Object obj : el.getChildren()) {
            Element e = (Element) obj;
            System.out.print(" id " + e.getChild("id").getText() + " , ");

            if (Integer.parseInt(e.getChild("id").getText()) == task.getId()) {

                task = getTask((Element) e);
                break;

            }

        }

        System.out.println(" getask () " + task.toSting());
        return task;
    }
    /*
     *  This method to get all task from xml
     *  @return List of Task
     */

    public List<Task> getAllTask() {

        List<Task> allTask = new ArrayList<Task>();
        Element el = doc.getRootElement().getChild(taskRootName);

        for (Object t : el.getChildren()) {
            Task task = getTask((Element) t);
            if (task.getStatus() == 0) {
                allTask.add(task);
            }
        }

        return allTask;
    }
    /*
     *  This method to get selected  task from xml
     *  @return instance of Task
     */

    private Task getTask(Element e) {
        Task t = new Task();

        t.setId(Integer.parseInt(e.getChild("id").getText()));
        t.setTaskName(e.getChild("taskName").getText());
        t.setPriority(e.getChild("priority").getText());
        t.setTag(e.getChild("tag").getText());
        t.setDueDate(Utility.parseDate(e.getChild("dueDate").getText()));
        t.setDescription(e.getChild("description").getText());
        t.setProgress(Integer.parseInt(e.getChild("progress").getText()));
        t.setStatus(Integer.parseInt(e.getChild("status").getText()));


        return t;
    }
    /*
     *  This method to get all tag from xml
     *  @return list for Tag object
     */

    public List<Tag> getAllTag() {
        List<Tag> allTag = new ArrayList<Tag>();
        Element el = doc.getRootElement().getChild(tagRootName);


        for (Object t : el.getChildren()) {
            Tag tag = getTag((Element) t);
            allTag.add(tag);
        }

        return allTag;
    }

    private Tag getTag(Element e) {
        Tag t = new Tag();

        t.setTagID(Integer.parseInt(e.getChild("tagID").getText()));
        t.setTagName(e.getChild("tagName").getText());
        return t;
    }

    public List<Task> viewTaskByTag(Tag tag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
